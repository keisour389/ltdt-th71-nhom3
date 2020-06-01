package com.example.shippers.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shippers.API.OrdersFoodAPI;
import com.example.shippers.API.Req.OrderDetails;
import com.example.shippers.API.Req.OrderDetailsData;
import com.example.shippers.API.Req.OrdersData;
import com.example.shippers.API.Req.Orders;
import com.example.shippers.API.Req.UpdateStatus;
import com.example.shippers.R;
import com.example.shippers.SQLite.DatabaseContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentGetOrder extends Fragment {
    //Database
    DatabaseContext databaseContext;
    //Các biến lấy từ activity khác
    int orderIdPickUp = 0;
    int orderIdMoreInfo = 0;
    //Các biến sử dụng cho API
    Orders orderSearchResult;
    //Các biến dùng chung
    TextView orderStatus;
    TextView orderTime;
    TextView shipFee; //*
    TextView totalShipFee; //*
    TextView pickUpAddress; //*
    TextView shipAddress; //*
    TextView moneyPaid; //*
    TextView note;
    TextView phonePickUp;
    TextView phoneShip;
    //Intent
    private String account;
    //
    Button buttonGet;
    View view;
    ScrollView orderScrollView;
    //Các biến truyền vào adapter
    private ArrayList<String> foodName = new ArrayList<>();
    private ArrayList<Integer> foodAmount = new ArrayList<>();
    private ArrayList<Integer> foodPrice = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_get_order, container, false); //Lấy view pager đã thiết kế
        //Tìm id các đối tượng
        orderStatus = (TextView) view.findViewById(R.id.orderStatus);
        orderTime = (TextView) view.findViewById(R.id.orderTime);
        shipFee = (TextView) view.findViewById(R.id.shipFee);
        totalShipFee = (TextView) view.findViewById(R.id.totalShipFee);
        pickUpAddress = (TextView) view.findViewById(R.id.pickUpAddress);
        shipAddress = (TextView) view.findViewById(R.id.shipAddress);
        note = (TextView) view.findViewById(R.id.note);
        phonePickUp = (TextView) view.findViewById(R.id.phonePickUp);
        phoneShip = (TextView) view.findViewById(R.id.phoneShip);
        buttonGet = (Button) view.findViewById(R.id.buttonGet);
        //
        //set Scroll View
        orderScrollView = (ScrollView) view.findViewById(R.id.orderScrollView);
        //

        //Get OrderId
        Intent intent = getActivity().getIntent(); //Không thể tạo đối tượng new Intent mà phải getActivity rồi get Intent
        orderIdPickUp = intent.getIntExtra(FragmentPickUp.ORDER_ID, 0);
        orderIdMoreInfo = intent.getIntExtra(FragmentOrders.MORE_INFO_ORDER_ID, 0);
        Boolean isPickUp = intent.getBooleanExtra(FragmentPickUp.IS_PICKUP, false);
        Boolean moreInfo = intent.getBooleanExtra(FragmentOrders.MORE_INFO_ORDER, false);
        Log.d("Order PickUp", String.valueOf(isPickUp));
        Log.d("Order PickUp", String.valueOf(orderIdPickUp));
        Log.d("Order MoreInfo", String.valueOf(moreInfo));
        Log.d("Order MoreInfo", String.valueOf(orderIdMoreInfo));
        if(isPickUp == true)
        {
            orderSearchResult = getOrderById(orderIdPickUp);
            orderIdPickUp = 0;
        }
        else if(moreInfo == true)
        {
            orderSearchResult = getOrderById(orderIdMoreInfo);
            orderIdMoreInfo = 0;
        }
        //Get account
        databaseContext = new DatabaseContext(getActivity(), "Local", null, 1);
        account = getAccountFromDatabase();

        //Toast.makeText(getContext(), orderSearchResult.getStatus(), Toast.LENGTH_SHORT).show();
        //


        return view;
    }
    Gson gson = new GsonBuilder().serializeNulls().create();
    //Khởi tạo retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
            .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
            .build();
    OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller
    //Lấy trạng thái button hiện tại
    private void getOrderStatus(String text)
    {
        switch (text)
        {
            case "Đã nhận":
                //Change Button
                changeStatusButtonAndLabel("Đã nhận", "#0731db", "Đã nhận", "#0731db");
                togglePhoneNumber(20, View.VISIBLE);
                break;
            case "Đang giao":
                //Change Button
                changeStatusButtonAndLabel("Hoàn thành", "#05f709",  "Đang giao", "#05f709");
                togglePhoneNumber(20, View.VISIBLE);
                break;
            case "Hoàn thành":
                //Change Button
                buttonGet.setVisibility(View.GONE); //Khi button mất đi thì đơn hàng sẽ được full screen
                togglePhoneNumber(0, View.INVISIBLE);
                break;
        }
    }
    //Thay đổi trạng thái trong DL lẫn button
    private void changeOrderStatus(String account, String status, Orders order)
    {
        switch (status)
        {
            //Phải toggle hết SĐT, nếu không load từ giai đoạn 2 -> 3 không hiện
            case "Nhận hàng":
                //Update Status Ordered
                order.setShipperId(account);
                order.setStatus("Đã nhận");
                updateOrder(order);
                //Change Button
                changeStatusButtonAndLabel("Đã nhận", "#0731db", "Đã nhận", "#0731db");
                togglePhoneNumber(20, View.VISIBLE);
                break;
            case "Đã nhận":
                //Update Status Complete
                order.setStatus("Đang giao");
                updateOrder(order);
                //Change Button
                togglePhoneNumber(20, View.VISIBLE);
                changeStatusButtonAndLabel("Hoàn thành", "#05f709",  "Đang giao", "#05f709");
                break;
            case "Đang giao":
                //Update Status Completed
                order.setStatus("Hoàn thành");
                updateOrder(order);
                //Thay đổi label và button sau khi hoàn thành
                changeStatusButtonAndLabel("Hoàn thành", "#f000", "Hoàn thành", "#cfcbca"); //Chủ yếu là thay đổi label
                buttonGet.setVisibility(View.GONE); //Khi button mất đi thì đơn hàng sẽ được full screen
                togglePhoneNumber(0, View.INVISIBLE);
                break;
        }


    }
    //Thay đổi chữ và màu sắc button
    private void changeStatusButtonAndLabel(String buttonText, String buttonColor, String lableText, String lableColor)
    {
        //Label bên trên
        orderStatus.setBackgroundColor(Color.parseColor(lableColor));
        orderStatus.setText(lableText);
        //Button
        buttonGet.setBackgroundColor(Color.parseColor(buttonColor));
        buttonGet.setText(buttonText);
    }
    //Điều chỉnh hiển thị số điện thoại
    private void togglePhoneNumber(int textSize, int toggle)
    {
        //Số điện thoại nhận
        phonePickUp.setVisibility(toggle); //Hiển thị số điện thoại nhận
        phonePickUp.setTextSize(textSize); //Set text size
        //Số điện thoại giao
        phoneShip.setVisibility(toggle); //Hiển thị số điện thoại giao
        phoneShip.setTextSize(textSize); //Set text size
    }
    //Lấy đơn hàng theo mã
    public Orders getOrderById(int orderId)
    {
        final Orders[] result = new Orders[1]; //Tạo kết quả trả về

        //Orders
        Call<OrdersData> call = ordersFoodAPI.getOrderById(orderId); //id này là request
        call.enqueue(new Callback<OrdersData>() {
            Orders result; //Kết quả trả về
            Orders[] orderSearchByID;
            @Override
            public void onResponse(Call<OrdersData> call, Response<OrdersData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                orderSearchByID = response.body().getOrdersList(); //Tìm thấy đơn hàng theo Id
                //Gán
                //Luôn luôn lấy index 0 vì chỉ có 1 id tìm được duy nhất
                orderStatus.setText(orderSearchByID[0].getStatus());
                orderTime.setText(orderSearchByID[0].getCreatedDate());
                note.setText(orderSearchByID[0].getNote());
                phonePickUp.setText(orderSearchByID[0].getPhoneNumber());
                //Số điện thoại của người đặt hàng có thể khác thông tin cá nhân
                phoneShip.setText(orderSearchByID[0].getPhoneNumber());
                //Địa chỉ
                pickUpAddress.setText(orderSearchByID[0].getPickUpAddress());
                shipAddress.setText(orderSearchByID[0].getShipAddress());
                //Tiền ship
                totalShipFee.setText(String.valueOf(orderSearchByID[0].getShippingCost()) +
                        "VNĐ");
                //Lấy trạng thái hiện tại của đơn hàng
                getOrderStatus(orderSearchByID[0].getStatus());
                //Còn 1 số thuộc tính khác chưa gán

                //Phần xử lí result khi lấy được
                result = response.body().getOrdersList()[0];
                //Thực hiện lệnh bấm thì sẽ thay đổi trạng thái đơn
                buttonGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Check and change status
                        changeOrderStatus(account, result.getStatus(), result); //Thay đổi DL gồm shipper, trạng thái và Button
                        Log.d("Result", result.getStatus()); //Kiểm tra
                    }
                });

            }
            @Override
            public void onFailure(Call<OrdersData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
        //Kết quả trả về
        return result[0];
    }
    //Update Order
    public void updateOrder(Orders orderToUpdate)
    {
        //Update Status
        Call<UpdateStatus> call = ordersFoodAPI.updateOrdersStatus(orderToUpdate); //id này là request
        call.enqueue(new Callback<UpdateStatus>() {
            UpdateStatus updateStatus;
            @Override
            public void onResponse(Call<UpdateStatus> call, Response<UpdateStatus> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Update", "Failed");
                    return;
                }
                //Tìm thấy
                updateStatus = response.body();
                Log.d("Update", "Success");
            }
            @Override
            public void onFailure(Call<UpdateStatus> call, Throwable t) {
                Log.d("Update", "Error");
            }
        });
    }
    //Get account from local database
    private String getAccountFromDatabase()
    {
        //Database
        String result = "";
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM AccountTable");
        Log.d("GetCount", String.valueOf(getData.isFirst()));
        while(getData.moveToNext()) //The first row
        {
            result = getData.getString(0);//Account nằm cột 1
            Log.d("AccountDatabase", result);
        }
        return result;
    }
}

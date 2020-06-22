package com.example.lalafood.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.Orders;
import com.example.lalafood.API.Req.OrdersData;
import com.example.lalafood.API.Req.Users;
import com.example.lalafood.API.Req.UsersData;
import com.example.lalafood.Customers.Adapter.BillCustomerAdapter;
import com.example.lalafood.Helper.LocaleHelper;
import com.example.lalafood.R;
import com.example.lalafood.SQLite.DatabaseContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerOrderCompleted extends AppCompatActivity {
    //Các biến xử lí
//    private String orderStatus = getString(R.string.take_order); //mặc định

    //Test
    private ArrayList<String> foodName = new ArrayList<>();
    private ArrayList<Integer> foodAmount = new ArrayList<>();
    private ArrayList<Integer> foodPrice = new ArrayList<>();
    //Các biến dùng chung
    int totalPrice = 0;
    String orderStatus;
    ListView foodListCustomerOrder;
    TextView customerOrderChecking;
    TextView customerOrderPrepare;
    TextView customerOrderShipping;
    TextView customerOrderCompleted;
    LinearLayout shipperInfoInCusOrder;
    TextView shipperNameInCusOrder;
    TextView shipperPhoneNumberInCusOrder;
    TextView customerOrderIdLabel;
    TextView totalPriceInCusOrder;
    TextView addressInCustomerOrder;
    TextView shippingCostInCusOrder;
    TextView noteInCustomerOrder;
    TextView searchShipperLabel;
    //Các biến intent
    private int orderId;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_order_complete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refreshStatusCusOrder)
        {
            getOrderById(orderId);
            //TODO: Under
            getSetStatusCusOrder(orderStatus);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_completed);
        //Ánh xạ
        orderStatus = getString(R.string.take_order);
        foodListCustomerOrder = (ListView) findViewById(R.id.foodListCustomerOrder);
        customerOrderChecking = (TextView) findViewById(R.id.customerOrderChecking);
        customerOrderPrepare = (TextView) findViewById(R.id.customerOrderPrepare);
        customerOrderShipping = (TextView) findViewById(R.id.customerOrderShipping);
        customerOrderCompleted = (TextView) findViewById(R.id.customerOrderCompleted);
        shipperInfoInCusOrder = (LinearLayout) findViewById(R.id.shipperInfoInCusOrder);
        shipperNameInCusOrder = (TextView) findViewById(R.id.shipperNameInCusOrder);
        shipperPhoneNumberInCusOrder = (TextView) findViewById(R.id.shipperPhoneNumberInCusOrder);
        customerOrderIdLabel = (TextView) findViewById(R.id.customerOrderIdLabel);
        totalPriceInCusOrder = (TextView) findViewById(R.id.totalPriceInCusOrder);
        addressInCustomerOrder = (TextView) findViewById(R.id.addressInCustomerOrder);
        shippingCostInCusOrder = (TextView) findViewById(R.id.shippingCostInCusOrder);
        noteInCustomerOrder = (TextView) findViewById(R.id.noteInCustomerOrder);
        searchShipperLabel = (TextView) findViewById(R.id.searchShipperLabel);
        //Điều chỉnh list view có thể scroll khi nằm trong scroll view cha
        foodListCustomerOrder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        //Lấy mã đơn hàng
        Intent intent = getIntent();
        orderId = intent.getIntExtra(RestaurantsDishes.ORDER_ID, 0);
        getOrderById(orderId);
        //Gán mã đơn hàng vào label
        customerOrderIdLabel.setText(getText(R.string.order_id_num) + String.valueOf(orderId));
        //Xử lí tình trạng đơn hàng
        Log.d("orderId", String.valueOf(orderId));
        //Get order details
        getOrderDetailsFromSQLite(String.valueOf(orderId));
        //Triển khai adapter sau khi đã lấy order details từ sqlite
        BillCustomerAdapter billCustomerAdapter = new BillCustomerAdapter(this,
                foodName, foodAmount, foodPrice);
        foodListCustomerOrder.setAdapter(billCustomerAdapter);
        //Tính tổng tiền chưa tính ship
        for(int price: foodPrice)
        {
            totalPrice += price;
        }
//        totalPrice += shippingCost;
//        shippingCostInCusOrder.setText(String.valueOf(totalPrice));
        //



    }
    //Get and set status customer order
    private void getSetStatusCusOrder(String status)
    {
//        switch (status)
//        {
//            case "Nhận hàng":
//                customerOrderChecking.setTextColor(Color.parseColor("#000000"));
//                customerOrderPrepare.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderShipping.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderCompleted.setTextColor(Color.parseColor("#a7aba6"));
//                break;
//            case "Đã nhận":
//                customerOrderChecking.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderPrepare.setTextColor(Color.parseColor("#000000"));
//                customerOrderShipping.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderCompleted.setTextColor(Color.parseColor("#a7aba6"));
//                break;
//            case "Đang giao":
//                customerOrderChecking.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderPrepare.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderShipping.setTextColor(Color.parseColor("#000000"));
//                customerOrderCompleted.setTextColor(Color.parseColor("#a7aba6"));
//                break;
//            case "Hoàn thành":
//                customerOrderChecking.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderPrepare.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderShipping.setTextColor(Color.parseColor("#a7aba6"));
//                customerOrderCompleted.setTextColor(Color.parseColor("#000000"));
//                break;
//        }
        //Dùng if else để có thể dùng string resources
        if(status.equals(getString(R.string.take_order)))
        {
            customerOrderChecking.setTextColor(Color.parseColor("#000000"));
            customerOrderPrepare.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderShipping.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderCompleted.setTextColor(Color.parseColor("#a7aba6"));
        }
        else if (status.equals(getString(R.string.order_taken)))
        {
            customerOrderChecking.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderPrepare.setTextColor(Color.parseColor("#000000"));
            customerOrderShipping.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderCompleted.setTextColor(Color.parseColor("#a7aba6"));
        }
        else if (status.equals(getString(R.string.order_on_the_way)))
        {
            customerOrderChecking.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderPrepare.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderShipping.setTextColor(Color.parseColor("#000000"));
            customerOrderCompleted.setTextColor(Color.parseColor("#a7aba6"));
        }
        else if (status.equals(getString(R.string.order_completed)))
        {
            customerOrderChecking.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderPrepare.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderShipping.setTextColor(Color.parseColor("#a7aba6"));
            customerOrderCompleted.setTextColor(Color.parseColor("#000000"));
        }
    }
    //Check pick up or not
    private void orderIsPickUp()
    {
        if(orderStatus.equals(getString(R.string.order_taken))) //Trường hợp chưa response
        {
            shipperInfoInCusOrder.setVisibility(View.GONE);
            searchShipperLabel.setVisibility(View.GONE);
        }
        else
        {
            shipperInfoInCusOrder.setVisibility(View.VISIBLE);
            searchShipperLabel.setVisibility(View.VISIBLE);
        }
    }
    Gson gson = new GsonBuilder().serializeNulls().create();
    //Khởi tạo retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
            .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
            .build();
    OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller
    //Lấy đơn hàng theo mã
    public void getOrderById(int orderId)
    {
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
                //Phần xử lí result khi lấy được
                orderStatus = orderSearchByID[0].getStatus(); //Trạng thái
                addressInCustomerOrder.setText(orderSearchByID[0].getShipAddress()); //Địa chỉ nhận hàng
                shippingCostInCusOrder.setText(String.format("%s%s", getString(R.string.shipping_fee), String.valueOf(orderSearchByID[0].getShippingCost()))); //Phí ship
                //Cộng thêm phí ship vào phần tổng
                // Set total
                totalPriceInCusOrder.setText(String.format("%s%s", getString(R.string.total), String.valueOf(totalPrice + orderSearchByID[0].getShippingCost())));
                noteInCustomerOrder.setText(orderSearchByID[0].getNote()); //Ghi chú
                //orderIsPickUp(); //Kiểm tra trạng thái tài xế nhận hay chưa
                getSetStatusCusOrder(orderStatus);
                //Lấy thông tin tài xế
                getShipperByAccount(orderSearchByID[0].getShipperId());
            }
            @Override
            public void onFailure(Call<OrdersData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
    //Lấy shipper theo mã
    private void getShipperByAccount(String account)
    {
        //Shipper
        Call<UsersData> call = ordersFoodAPI.getUserByAccount(account); //id này là request
        call.enqueue(new Callback<UsersData>() {
            Orders result; //Kết quả trả về
            Users[] userSearchByID;
            @Override
            public void onResponse(Call<UsersData> call, Response<UsersData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                userSearchByID = response.body().getUsersList(); //Tìm thấy đơn hàng theo Id
                //Gán
                //Luôn luôn lấy index 0 vì chỉ có 1 id tìm được duy nhất
                //Phần xử lí result khi lấy được
                //Gán tên và sdt shipper
                shipperNameInCusOrder.setText(userSearchByID[0].getLastName() + " " +
                        userSearchByID[0].getFirstName());
                shipperPhoneNumberInCusOrder.setText(userSearchByID[0].getUserName());
                orderIsPickUp(); //Kiểm tra trạng thái nhận hay chưa
                getSetStatusCusOrder(orderStatus);
            }
            @Override
            public void onFailure(Call<UsersData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
    //Lấy chi tiết đơn hàng
    private void getOrderDetailsFromSQLite(String orderId)
    {
        DatabaseContext database = new DatabaseContext(this, "Local", null, 1);
        //Get Data
        Cursor data = database.GetData("SELECT *\n" +
                "FROM Bill\n" +
                "WHERE Bill.OrderId = " + orderId);
        while(data.moveToNext())//Lấy hết các data trong bảng
        {
            //Tạo arraylist gán vào adapter
            foodAmount.add(data.getInt(3));
            foodName.add(data.getString(4));
            foodPrice.add(data.getInt(5));
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}

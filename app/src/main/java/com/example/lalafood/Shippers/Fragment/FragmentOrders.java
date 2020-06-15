package com.example.lalafood.Shippers.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.Orders;
import com.example.lalafood.API.Req.OrdersData;
import com.example.lalafood.Shippers.Activity.OrdersAdapter;
import com.example.lalafood.R;
import com.example.lalafood.SQLite.DatabaseContext;
import com.example.lalafood.Shippers.Activity.ShippersMainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentOrders extends Fragment {
    public static final String MORE_INFO_ORDER = "MORE_INFO_ORDER";
    public static final String MORE_INFO_ORDER_ID = "MORE_INFO_ORDER_ID";
    //
    private String account;
    //Intent
    public String orderStatus = String.valueOf(R.string.order_taken); //Mặc định
    public View view;
    //Các biến dùng chung
    OrdersAdapter ordersAdapter;
    ListView ordersStatusList;
    SwipeRefreshLayout orderSwipeLayout;
    Spinner fillerOrders;
    TextView ordersTypeLabel;
    //Các biến gửi đi
//    private String textTitle = String.valueOf(R.string.order_taken); //Mặc định
    private String textTitle;
    private String filter;
    private String textTitleColor = "#0731db"; //Mặc định
    //Spinner
    private final String[] paths = {String.valueOf(R.string.order_taken), String.valueOf(R.string.order_on_the_way),
            String.valueOf(R.string.order_canceled), String.valueOf(R.string.order_completed)};

    public FragmentOrders()
    {}
    public FragmentOrders(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        account = getAccountFromDatabase();
        getOrdersByStatus(account, orderStatus);
        //Ánh xạ
        orderSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.orderSwipeLayout);
        fillerOrders = (Spinner) view.findViewById(R.id.fillerOrders);
        ordersTypeLabel = (TextView) view.findViewById(R.id.ordersTypeLabel);
        filter = (String) getText(R.string.filter);
        orderSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Khi refresh sẽ chạy vào các hàm này
                        getOrdersByStatus(account, orderStatus);
                        //Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                        orderSwipeLayout.setRefreshing(false);
                    }
                }, 2000); //Delay 2s
            }
        });
        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, paths);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        fillerOrders.setAdapter(adapter);
        //Click
        fillerOrders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Set text cứng
//                ((TextView)view).setText(filter);
                try
                {
                    ((TextView)view).setText(filter);
                }
                catch(Exception ex)
                {
                }
                switch (position) {
                    case 0: //Đã nhận
                        //Các biến dùng để truyền
                        orderStatus = (String) getText(R.string.order_taken);
                        textTitle = (String) getText(R.string.order_taken);
                        textTitleColor = "#0731db";
                        //Các biến dùng để gán
                        ordersTypeLabel.setText(getText(R.string.order_type) + orderStatus);
                        getOrdersByStatus(account, orderStatus);
                        break;
                    case 1:
                        //Các biến dùng để truyền
                        orderStatus = (String) getText(R.string.order_on_the_way);
                        textTitle = (String) getText(R.string.order_on_the_way);
                        textTitleColor = "#05f709";
                        //Các biến dùng để gán
                        ordersTypeLabel.setText(getText(R.string.order_type) + orderStatus);
                        getOrdersByStatus(account, orderStatus);
                        break;
                    case 2:
                        //Các biến dùng để truyền
                        orderStatus = (String) getText(R.string.order_canceled);
                        textTitle = (String) getText(R.string.order_canceled);
                        textTitleColor = "#fa3605";
                        //Các biến dùng để gán
                        ordersTypeLabel.setText(getText(R.string.order_type) + orderStatus);
                        getOrdersByStatus(account, orderStatus);
                        break;
                    case 3:
                        //Các biến dùng để truyền
                        orderStatus = (String) getText(R.string.order_completed);;
                        textTitle = (String) getText(R.string.order_completed);
                        textTitleColor = "#cfcbca";
                        //Các biến dùng để gán
                        ordersTypeLabel.setText(getText(R.string.order_type) + orderStatus);
                        getOrdersByStatus(account, orderStatus);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: Nothing
            }
        });
        return view;
    }
    public void getOrdersByStatus(final String account, final String status)
    {
        //Các biến của API
        final ArrayList<Integer> orderId = new ArrayList<>();
        final ArrayList<String> note = new ArrayList<>();
        final ArrayList<String> pickUpAddress = new ArrayList<>();
        final ArrayList<String> shipAddress = new ArrayList<>();
        final ArrayList<Integer> shipFee = new ArrayList<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        //Khởi tạo retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
                .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
                .build();
        OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller

        //Orders
        Call<OrdersData> call = ordersFoodAPI.getAllOrders("orderId", "desc");
        call.enqueue(new Callback<OrdersData>() {
            @Override
            public void onResponse(Call<OrdersData> call, Response<OrdersData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                final Orders[] ordersList = response.body().getOrdersList(); //Lấy các giá trị tìm được gán vào List
                int index = 1;
                for (Orders order : ordersList) {
                    if(order.getStatus().equals(status) && order.getShipperId().equals(account))
                    {
                        //Gán giá trị vào
                        orderId.add(order.getOrderId());
                        pickUpAddress.add("Quận " + index);
                        shipAddress.add("Quận " + index);
                        note.add(order.getNote());
                        shipFee.add(order.getShippingCost());
                        index++;
                    }
                }
                //Triển khai adapter
                ordersAdapter = new OrdersAdapter(getActivity(), orderId, pickUpAddress, shipAddress, note, shipFee , textTitle, textTitleColor);
                ordersStatusList = (ListView) view.findViewById(R.id.listOrdersStatus);
                ordersStatusList.setAdapter(ordersAdapter);
                //Pick up order
                ordersStatusList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ShippersMainActivity mainActivity = new ShippersMainActivity();

                        Intent getOrderIntent = new Intent(getActivity(), mainActivity.getClass());
                        //Put đã chọn đơn này
                        getOrderIntent.putExtra(MORE_INFO_ORDER, true);
                        //Put theo mã đơn hàng
                        getOrderIntent.putExtra(MORE_INFO_ORDER_ID, orderId.get(position)); //Lấy mã đơn hàng tìm được, không lấy theo list
                        startActivity(getOrderIntent);
                    }
                });
            }

            @Override
            public void onFailure(Call<OrdersData> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.notice))
                        .setMessage(getString(R.string.no_internet))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
    }
    //Get account from local database
    private String getAccountFromDatabase()
    {
        //Database
        String result = "";
        DatabaseContext databaseContext = new DatabaseContext(getActivity(), "Local", null, 1);
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM ShipperAccount");
        Log.d("GetCount", String.valueOf(getData.isFirst()));
        while(getData.moveToNext()) //The first row
        {
            result = getData.getString(0);//Account nằm cột 1
            Log.d("AccountDatabase", result);
        }
        return result;
    }
}
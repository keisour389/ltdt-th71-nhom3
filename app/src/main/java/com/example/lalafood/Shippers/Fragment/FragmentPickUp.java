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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.OrdersData;
import com.example.lalafood.API.Req.Orders;
import com.example.lalafood.SQLite.DatabaseContext;
import com.example.lalafood.Shippers.Activity.ShippersMainActivity;
import com.example.lalafood.Shippers.Activity.OrdersAdapter;
import com.example.lalafood.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentPickUp extends Fragment {
    public static final String IS_PICKUP = "IS_MAIN";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ACCOUNT = "ACCOUNT";
    View view;
    //
    OrdersAdapter ordersAdapter;
    ListView ordersListView;
    SwipeRefreshLayout newOrdersSwipeLayout;
    Switch workingSwitch;
    //Intent
    public String account;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pick_up, container, false);
        //Lấy account
        account = getAccountFromDatabase();
        //
        newOrdersSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.newOrdersSwipeLayout);
        newOrdersSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Khi refresh sẽ chạy vào các hàm này
                        ordersListView.removeAllViewsInLayout();
                        allOrders(getString(R.string.take_order));
                        Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                        newOrdersSwipeLayout.setRefreshing(false);
                    }
                }, 2000); //Delay 2s
            }
        });
        //Ánh xạ
        workingSwitch = (Switch) view.findViewById(R.id.workingSwitch);
        ordersListView = (ListView) view.findViewById(R.id.ordersListId);
        //Làm mới layout
        ordersListView.removeAllViewsInLayout();
        allOrders(getString(R.string.take_order));
        //Lấy tài khoản từ Shipper main
        Log.d("account", account);
        return view;
    }
    //Get all order by status
    public void allOrders(final String status)
    {
        //
        final ArrayList<String> orderName = new ArrayList<>();
        final ArrayList<Integer> orderId = new ArrayList<>();
        final ArrayList<String> orderNote = new ArrayList<>();
        final ArrayList<String> pickUpAddress = new ArrayList<>();
        final ArrayList<String> shipAddress = new ArrayList<>();
        final ArrayList<Integer> orderFee = new ArrayList<>();
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
                    if(order.getStatus().equals(status))
                    {
                        //Gán giá trị vào
                        orderName.add(order.getName());
                        orderId.add(order.getOrderId());
                        //Tạm thời chưa split, chờ CSDL chính thức
//                        pickUpAddress.add("Quận " + splitAddress(order.getPickUpAddress()));
//                        shipAddress.add("Quận " + splitAddress(order.getShipAddress()));
                        pickUpAddress.add(order.getPickUpAddress());
                        shipAddress.add(order.getShipAddress());
                        orderNote.add(order.getNote());
                        orderFee.add(order.getShippingCost());
                        index++;
                    }
                }
                Log.d("index", String.valueOf(index));
                //Triển khai adapter
                ordersAdapter = new OrdersAdapter(getActivity(), orderId, pickUpAddress, shipAddress, orderNote,
                        orderFee, getActivity().getText(R.string.lalafood_order).toString(), "#fa9702");
                ordersListView.setAdapter(ordersAdapter);
                //Xử lí switch
                //Khi con switch thay đổi
                workingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        changeSwitchStatus(isChecked);
                    }
                });
                //Pick up order
                ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ShippersMainActivity mainActivity = new ShippersMainActivity();

                        Intent getOrderIntent = new Intent(getActivity(), mainActivity.getClass());
                        //Put đã chọn đơn này
                        getOrderIntent.putExtra(IS_PICKUP, true);
                        //Put theo mã đơn hàng
                        getOrderIntent.putExtra(ORDER_ID, ordersList[position].getOrderId());
                        //Put tài khoản
                        getOrderIntent.putExtra(ACCOUNT, account);
                        Log.d("OrderId", String.valueOf(ordersList[position].getOrderId()));
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
                Log.d("Response", "Error");
                //Khi không có mạng cái đơn hàng sẽ biến mất
                changeSwitchStatus(false);
                //Chế độ nghĩ
                workingSwitch.setChecked(false);

            }
        });
    }
    //Thay đổi trạng thái của switch
    private void changeSwitchStatus(boolean check)
    {
        if (check) {
            ordersListView.setVisibility(View.VISIBLE);
            allOrders(getString(R.string.take_order));
        } else {
            ordersListView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Ứng dụng đang nghĩ", Toast.LENGTH_SHORT).show();
        }
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
    //Split District From Address
    private String splitAddress(String address)
    {
        String[] list = address.split(","); //Cách nhau bằng dấu phẩy
        return list[1]; //Quận nằm giữa
    }
}

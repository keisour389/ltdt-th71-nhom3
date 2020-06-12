package com.example.lalafood.Shippers.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.OrdersData;
import com.example.lalafood.API.Req.Orders;
import com.example.lalafood.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetOrderActivity extends AppCompatActivity {

    Context context = this;
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
    //Các biến lưu tạm giá trị
    String phoneShipString = "";
    Button buttonGet;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_get_order);
        //Nút quay lại
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Tìm id các đối tượng
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        orderTime = (TextView) findViewById(R.id.orderTime);
        shipFee = (TextView) findViewById(R.id.shipFee);
        totalShipFee = (TextView) findViewById(R.id.totalShipFee);
        pickUpAddress = (TextView) findViewById(R.id.pickUpAddress);
        shipAddress = (TextView) findViewById(R.id.shipAddress);
        note = (TextView) findViewById(R.id.note);
        phonePickUp = (TextView) findViewById(R.id.phonePickUp);
        phoneShip = (TextView) findViewById(R.id.phoneShip);
        //
        //Create Fake Id request
        getOrderById(1);
        //
        buttonGet = (Button) findViewById(R.id.buttonGet);
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check and change status
                checkButtonStatus(buttonGet.getText().toString());
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //Hàm kiểm tra trạng thái của button
    private void checkButtonStatus(String text)
    {
//        switch (text)
//        {
//            case "Nhận hàng":
//                changeButtonStatus("Đã nhận", "#0731db");
//                togglePhoneNumber(20, View.VISIBLE);
//                break;
//            case "Đã nhận":
//                changeButtonStatus("Hoàn thành", "#05f709");
//                break;
//            case "Hoàn thành":
//                buttonGet.setVisibility(View.INVISIBLE); //Ẩn button đi
//                break;
//        }
        //Dùng if else để có thể dùng string resources
        if (text.equals(getString(R.string.take_order)))
        {
            changeButtonStatus(getString(R.string.order_taken), "#0731db");
            togglePhoneNumber(20, View.VISIBLE);
        }
        else if (text.equals(getString(R.string.order_taken)))
        {
            changeButtonStatus(getString(R.string.order_completed), "#0731db");
            togglePhoneNumber(20, View.VISIBLE);
        }
        else if (text.equals(getString(R.string.order_completed)))
        {
            buttonGet.setVisibility(View.INVISIBLE); //Ẩn button đi
        }
    }
    //Thay đổi trạng thái button
    private void changeButtonStatus(String text, String color)
    {
        buttonGet.setBackgroundColor(Color.parseColor(color));
        buttonGet.setText(text);
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
    public void getOrderById(int orderId)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        //Khởi tạo retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
                .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
                .build();
        OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller

        //Orders
        Call<OrdersData> call = ordersFoodAPI.getOrderById(orderId); //id này là request
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
                Orders[] orderSearchByID = response.body().getOrdersList(); //Tìm thấy đơn hàng theo Id
                //Gán
                //Luôn luôn lấy index 0 vì chỉ có 1 id tìm được duy nhất
                orderStatus.setText(orderSearchByID[0].getStatus());
                orderTime.setText(orderSearchByID[0].getCreatedDate());
                note.setText(orderSearchByID[0].getNote());
                phonePickUp.setText("0902725706");
                //Số điện thoại của người đặt hàng có thể khác thông tin cá nhân
                phoneShip.setText(orderSearchByID[0].getPhoneNumber());
                //Còn 1 số thuộc tính khác chưa gán
            }
            @Override
            public void onFailure(Call<OrdersData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
}

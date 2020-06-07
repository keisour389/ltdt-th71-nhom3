package com.example.lalafood.Shippers.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.OrdersData;
import com.example.lalafood.API.Req.IdReq;
import com.example.lalafood.API.Req.Orders;
import com.example.lalafood.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestAPI extends AppCompatActivity {
    private TextView textViewResult;
    //private RequestQueue mQueue;
    private OrdersFoodAPI ordersFoodAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_a_p_i);

        textViewResult = findViewById(R.id.text_view_result);
        Gson gson = new GsonBuilder().serializeNulls().create();
        //Khởi tạo retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
                .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
                .build();
        ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller
        //
        //Call<List<Orders>> call = ordersFoodAPI.getAllOrders();
        IdReq orderId = new IdReq();
        orderId.setId(3);
        Call<OrdersData> call = ordersFoodAPI.getOrderById(1);
        call.enqueue(new Callback<OrdersData>() {
            @Override
            public void onResponse(Call<OrdersData> call, Response<OrdersData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                Orders[] ordersList = response.body().getOrdersList(); //Lấy các giá trị tìm được gán vào List
                textViewResult.setText(ordersList[0].getCustomerId());
                if (ordersList == null) {
                    textViewResult.setText("NULL");
                }
            }

            @Override
            public void onFailure(Call<OrdersData> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                Log.d("Response", "Error");
            }
        });
//        call.enqueue(new Callback<List<Orders>>() {
//            //Nếu có thể vào tìm dữ liệu
//            @Override
//            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
//                //Không tìm thấy
//                if (!response.isSuccessful()) {
//                    Log.d("Response", "Failed");
//                    textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//                //Tìm thấy
//                List<Orders> result = response.body(); //Lấy các giá trị tìm được gán vào List
//                //Lấy từng giá trị
//                for (Orders order : result) {
//                    String content = "";
//                    content += "orderId: " + order.getOrderId() + "\n";
//                    content += "name: " + order.getName() + "\n";
//                    content += "createdDate: " + order.getCreatedDate() + "\n";
//                    content += "phoneNumber: " + order.getPhoneNumber() + "\n";
//                    content += "status: " + order.getStatus() + "\n\n";
//                    content += "note: " + order.getNote() + "\n\n";
//                    content += "promotionId: " + order.getPromotionId() + "\n\n";
//                    content += "customerId: " + order.getCustomerId() + "\n\n";
//                    content += "shipperId: " + order.getShipperId() + "\n\n";
//                    content += "adminId: " + order.getAdminId() + "\n\n";
//                    textViewResult.append(content);
//                }
//                Log.d("Response", "Success");
//            }
//
//            //Không thể vào tìm dữ liệu
//            @Override
//            public void onFailure(Call<List<Orders>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//                Log.d("Response", "Error");
//            }
//        });

    }
}

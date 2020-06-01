package com.example.shippers.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.shippers.API.OrdersFoodAPI;
import com.example.shippers.API.Req.Restaurants;
import com.example.shippers.API.Req.RestaurantsData;
import com.example.shippers.Customer.Adapter.RestaurantAdapter;
import com.example.shippers.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CustomerSearchedRestaurant extends AppCompatActivity {
    private Activity contextActivity = this;
    //Các biến dùng chung
    private ListView restaurantList;
//    private String[] restaurantImage = {"https://www.bbcgoodfood.com/sites/default/files/recipe-collections/collection-image/2013/05/chorizo-mozarella-gnocchi-bake-cropped.jpg",
//    "https://www.bbcgoodfood.com/sites/default/files/recipe-collections/collection-image/2013/05/chorizo-mozarella-gnocchi-bake-cropped.jpg",
//    "https://www.bbcgoodfood.com/sites/default/files/recipe-collections/collection-image/2013/05/chorizo-mozarella-gnocchi-bake-cropped.jpg"};
//    private String[] restaurantName = {"Hanuri", "Busan", "14 Tôn Thất Hiệp"};
//    private String[] restaurantStatus = {"Đang mở cửa", "Đang mở cửa", "Đang mở cửa"};
//    private String[] restaurantOpen = {"8:00 am", "9:00 am", "10:00 am"};
//    private String[] restaurantClose = {"8:00 pm", "9:00 pm", "10:00 pm"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_searched_restaurant);
        //Ánh xạ
        restaurantList = (ListView) findViewById(R.id.restaurantList);
        //Lấy tất cả nhà hàng
        getAllRestaurant();

    }
    //Tạm thời get all nhà hàng để test
    private void getAllRestaurant()
    {

        Gson gson = new GsonBuilder().serializeNulls().create();
        //Khởi tạo retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
                .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
                .build();
        OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller

        //Orders
        Call<RestaurantsData> call = ordersFoodAPI.getAllRestaurant(); //id này là request
        call.enqueue(new Callback<RestaurantsData>() {
            ArrayList<String> restaurantImage = new ArrayList<>();
            ArrayList<String> restaurantName = new ArrayList<>();
            ArrayList<String> restaurantStatus = new ArrayList<>();
            ArrayList<String> restaurantOpen = new ArrayList<>();
            ArrayList<String> restaurantClose = new ArrayList<>();
            Restaurants[] allRestaurant;
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                allRestaurant = response.body().getRestaurantsList(); //Tìm thấy đơn hàng theo Id
                //Gán
                Log.d("allRestaurant.length", String.valueOf(allRestaurant.length));
                if(allRestaurant.length > 0) //Xử lí rỗng
                {
                    for(Restaurants res: allRestaurant)
                    {
                        restaurantImage.add(res.getImg());//URL trong ghi chú
                        restaurantName.add(res.getName());
                        restaurantStatus.add(res.getStatus());
                        restaurantOpen.add(res.getOpenTime());
                        restaurantClose.add(res.getCloseTime());
                    }
                }
                //Phần xử lí result khi lấy được
                //Triển khai adapter
                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(contextActivity, restaurantImage,
                        restaurantName, restaurantStatus, restaurantOpen,restaurantClose);
                restaurantList.setAdapter(restaurantAdapter);
                //Thực hiện lệnh bấm thì sẽ thay đổi trạng thái đơn
            }
            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });

    }
}

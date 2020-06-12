package com.example.lalafood.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.Restaurants;
import com.example.lalafood.API.Req.RestaurantsData;
import com.example.lalafood.Customers.Adapter.RestaurantAdapter;
import com.example.lalafood.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CustomerSearchedRestaurant extends AppCompatActivity {
    public static final String RESTAURANT_ID = "RESTAURANT_ID";
    public static final String RESTAURANT_NAME = "RESTAURANT_NAME";
    public static final String RESTAURANT_IMG = "RESTAURANT_IMG";
    public static final String RESTAURANT_ADDRESS = "RESTAURANT_ADDRESS";
    private Activity contextActivity = this;
    //Các biến dùng chung
    private String search = "";
    private ListView restaurantList;
    private ActionBar actionBar;
    private TextView searchRestaurant;
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
        actionBar = getSupportActionBar();
        restaurantList = (ListView) findViewById(R.id.restaurantList);
        searchRestaurant = (TextView) findViewById(R.id.searchRestaurant);
        //Chỉnh title action bar
        actionBar.setTitle(R.string.search);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Lấy kết quả tìm kiếm
        Intent intent = getIntent();
        search = intent.getStringExtra(CustomerMainSearch.SEARCH);
        //Set kết quả tìm kiếm
        searchRestaurant.setText(search);
        //Tìm kiếm kiếm theo tên
        getRestaurantByName(search);
        //Lấy tất cả nhà hàng
        //getAllRestaurant();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Nút quay lại
        if(id == android.R.id.home)
        {
            //Kết thúc hiện tại
            this.finish();
        }
        return super.onOptionsItemSelected(item);
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
                        restaurantImage.add(res.getImg());
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
    private void getRestaurantByName(String name)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        //Khởi tạo retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
                .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
                .build();
        OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller

        //Orders
        Call<RestaurantsData> call = ordersFoodAPI.getByTypeRestaurantName(name); //id này là request
        call.enqueue(new Callback<RestaurantsData>() {
            ArrayList<String> restaurantImage = new ArrayList<>();
            ArrayList<String> restaurantName = new ArrayList<>();
            ArrayList<String> restaurantStatus = new ArrayList<>();
            ArrayList<String> restaurantOpen = new ArrayList<>();
            ArrayList<String> restaurantClose = new ArrayList<>();
            Restaurants[] restaurantsByName;
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                restaurantsByName = response.body().getRestaurantsList(); //Tìm thấy đơn hàng theo Id
                //Gán
                Log.d("allRestaurant.length", String.valueOf(restaurantsByName.length));
                if(restaurantsByName.length > 0) //Xử lí rỗng
                {
                    for(Restaurants res: restaurantsByName)
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
                //Chọn nhà hàng
                restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(contextActivity, RestaurantsDishes.class);
                        intent.putExtra(RESTAURANT_ID, restaurantsByName[position].getRestaurantId()); //Gửi mã nhà hàng
                        intent.putExtra(RESTAURANT_NAME, restaurantsByName[position].getName()); //Gửi tên nhà hàng
                        intent.putExtra(RESTAURANT_IMG, restaurantsByName[position].getNote()); //Gửi ảnh nền nhà hàng
                        intent.putExtra(RESTAURANT_ADDRESS, restaurantsByName[position].getAddress()); //Gửi địa chỉ
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
}

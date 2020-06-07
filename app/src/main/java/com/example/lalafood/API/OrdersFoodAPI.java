package com.example.lalafood.API;

import com.example.lalafood.API.Req.CreateOrderData;
import com.example.lalafood.API.Req.CreateUserData;
import com.example.lalafood.API.Req.DishsData;
import com.example.lalafood.API.Req.OrderDetails;
import com.example.lalafood.API.Req.OrderDetailsCreateData;
import com.example.lalafood.API.Req.OrderDetailsData;
import com.example.lalafood.API.Req.RestaurantsData;
import com.example.lalafood.API.Req.Orders;
import com.example.lalafood.API.Req.OrdersData;
import com.example.lalafood.API.Req.UpdateStatus;
import com.example.lalafood.API.Req.Users;
import com.example.lalafood.API.Req.UsersData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrdersFoodAPI {
    //Summary
    //Các phương thức HTTP Request nằm hết ở đây
    //Xem như là 1 controller
    //End Summary

    //Orders
    @FormUrlEncoded
    @POST("api/Order/get-all") //Lấy hết đơn hàng về
    Call<OrdersData> getAllOrders(@Field("_sort") String sort,
                                  @Field("_order") String order); //Sắp xếp lại các id đơn hàng

//    @POST("api/Order/get-by-order-id")
//    Call<Orders> getOrderById(@Body IdReq id); //Post id theo request

    @FormUrlEncoded
    @POST("api/Order/get-by-order-id")
    Call<OrdersData> getOrderById(@Field("orderId") int orderId); //Post id theo request

    @POST("api/Order/update")
    Call<UpdateStatus> updateOrdersStatus(@Body Orders orders); //Update theo Body Orders

    @POST("api/Order/create")
    Call<CreateOrderData> createOrders(@Body Orders orders); //Update theo Body Orders
    //Users
    @FormUrlEncoded
    @POST("api/Users/get-by-user-name")
    Call<UsersData> getUserByAccount(@Field("userName") String account); //Post id theo request

    @POST("api/Users/create")
    Call<CreateUserData> createUser(@Body Users users); //Post id theo request

    //Restaurant
    @POST("api/Restaurant/get-all") //Lấy hết nhà hàng về
    Call<RestaurantsData> getAllRestaurant();

    @FormUrlEncoded
    @POST("api/Restaurant/get-by-type-restaurant-name") //Lấy nhà hàng theo tên
    Call<RestaurantsData> getByTypeRestaurantName(@Field("name") String name);

    //Dishes
    @POST("api/Dish/get-all") //Lấy hết món ăn về
    Call<DishsData> getAllDishes();

    @FormUrlEncoded
    @POST("api/Dish/get-by-restaurant-id") //Lấy nhà hàng theo tên
    Call<DishsData> getDishesByRestaurantId(@Field("restaurantId") String restaurantId);

    @FormUrlEncoded
    @POST("api/Dish/get-by-dish-id") //Lấy món theo Id
    Call<DishsData> getDishById(@Field("dishId") String dishId);

    //Order Details
    @POST("api/OrderDetail/create")
    Call<OrderDetailsCreateData> createOrderDetails(@Body OrderDetails orderDetails); //Post id theo request

    @FormUrlEncoded
    @POST("api/OrderDetail/get-by-order-id")
    Call<OrderDetailsData> getOrderDetailsById(@Field("orderId") String orderId);
}

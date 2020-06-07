package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class RestaurantsData {
    @SerializedName("data") //Đổi tên biến giống với JSON
    private Restaurants[] data; //Sử dụng mảng thường, không dùng Array List và List được
    @SerializedName("error")
    private String error;

    public Restaurants[] getRestaurantsList() {
        return data;
    }

    public String getError() {
        return error;
    }
}

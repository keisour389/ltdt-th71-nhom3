package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class DishsData {
    @SerializedName("data") //Đổi tên biến giống với JSON
    private Dishes[] data; //Sử dụng mảng thường, không dùng Array List và List được
    @SerializedName("error")
    private String error;

    public Dishes[] getDishesList() {
        return data;
    }

    public String getError() {
        return error;
    }
}

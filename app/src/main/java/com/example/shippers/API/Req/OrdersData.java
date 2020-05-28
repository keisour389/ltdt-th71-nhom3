package com.example.shippers.API.Req;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrdersData {
    @SerializedName("data") //Đổi tên biến giống với JSON
    private Orders[] data; //Sử dụng mảng thường, không dùng Array List và List được
    @SerializedName("error")
    private String error;

    public Orders[] getOrdersList() {
        return data;
    }

    public String getError() {
        return error;
    }
}

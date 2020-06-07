package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsCreateData {
    @SerializedName("data") //Đổi tên biến giống với JSON
    private OrderDetails data; //Sử dụng mảng thường, không dùng Array List và List được
    @SerializedName("error")
    private String error;

    public OrderDetails getOrderDetails() {
        return data;
    }

    public String getError() {
        return error;
    }
}

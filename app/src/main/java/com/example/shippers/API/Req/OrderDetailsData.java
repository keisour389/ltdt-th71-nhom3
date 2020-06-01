package com.example.shippers.API.Req;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsData
{
    @SerializedName("data") //Đổi tên biến giống với JSON
    private OrderDetails[] data; //Sử dụng mảng thường, không dùng Array List và List được
    @SerializedName("error")
    private String error;

    public OrderDetails[] getOrderDetailsList() {
        return data;
    }

    public String getError() {
        return error;
    }
}

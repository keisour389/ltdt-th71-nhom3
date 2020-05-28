package com.example.shippers.API.Req;

import com.google.gson.annotations.SerializedName;

public class UsersData
{
    @SerializedName("data") //Đổi tên biến giống với JSON
    private Users[] data; //Sử dụng mảng thường, không dùng Array List và List được
    @SerializedName("error")
    private String error;

    public Users[] getUsersList() {
        return data;
    }

    public String getError() {
        return error;
    }
}

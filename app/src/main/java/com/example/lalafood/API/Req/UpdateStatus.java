package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class UpdateStatus {
    @SerializedName("data")
    private int data; //Trả về 0 và 1
    @SerializedName("error")
    private String error;

    public int getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}

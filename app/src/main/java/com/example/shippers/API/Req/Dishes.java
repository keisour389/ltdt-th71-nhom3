package com.example.shippers.API.Req;

import com.google.gson.annotations.SerializedName;

public class Dishes {
    @SerializedName("dishId")
    private String dishId;
    @SerializedName("name")
    private String name;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("unit")
    private String unit;
    @SerializedName("note")
    private String note;
    @SerializedName("status")
    private String status;
    @SerializedName("price")
    private Integer price;
    @SerializedName("categoryId")
    private String categoryId;
    @SerializedName("restaurantId")
    private String restaurantId;
    @SerializedName("img")
    private String img;
    //get
    public String getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getUnit() {
        return unit;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}

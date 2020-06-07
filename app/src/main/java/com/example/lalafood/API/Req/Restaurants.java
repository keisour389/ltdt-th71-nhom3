package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class Restaurants {
    @SerializedName("restaurantId")
    private String restaurantId;
    @SerializedName("name")
    private String name;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("status")
    private String status;
    @SerializedName("openTime")
    private String openTime;
    @SerializedName("closeTime")
    private String closeTime;
    @SerializedName("managerId")
    private String managerId;
    @SerializedName("typeRestaurantId")
    private String typeRestaurantId;
    @SerializedName("note")
    private String note;
    @SerializedName("address")
    private String address;
    @SerializedName("img")
    private String img;

    //get

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getStatus() {
        return status;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getManagerId() {
        return managerId;
    }

    public String getTypeRestaurantId() {
        return typeRestaurantId;
    }

    public String getNote() {
        return note;
    }

    public String getAddress() {
        return address;
    }

    public String getImg() {
        return img;
    }
}

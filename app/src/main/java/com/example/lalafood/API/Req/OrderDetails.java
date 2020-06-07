package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class OrderDetails
{
    @SerializedName("orderId")
    private Integer orderId;
    @SerializedName("dishId")
    private String dishId;
    @SerializedName("amount")
    private Integer amount;
    @SerializedName("createdDate")
    private String createdDate;
    //Get

    public Integer getOrderId() {
        return orderId;
    }

    public String getDishId() {
        return dishId;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getCreatedDate() {
        return createdDate;
    }
    //Set

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

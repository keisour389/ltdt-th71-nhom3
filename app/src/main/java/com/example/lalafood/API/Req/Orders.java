package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("orderId")
    private int orderId;
    @SerializedName("name")
    private String name;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("status")
    private String status;
    @SerializedName("note")
    private String note;
    @SerializedName("promotionId")
    private String promotionId;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("shipperId")
    private String shipperId;
    @SerializedName("adminId")
    private String adminId;
    @SerializedName("pickUpAddress")
    private String pickUpAddress;
    @SerializedName("shipAddress")
    private String shipAddress;
    @SerializedName("shippingCost")
    private Integer shippingCost;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String  updated_at;
    //Phương thức khởi tạo
//    public Orders(int orderId, String name, String createdDate, String phoneNumber, String status, String note,
//                  String promotionId, String customerId, String shipperId, String adminId,
//                  String created_at, String updated_at)
//    {
//        this.orderId = orderId;
//        this.name = name;
//        this.createdDate = createdDate;
//        this.phoneNumber = phoneNumber;
//        this.status = status;
//        this.note = note;
//        this.promotionId = promotionId;
//        this.customerId = customerId;
//        this.shipperId = shipperId;
//        this.adminId = adminId;
//        this.created_at = created_at;
//        this.updated_at = updated_at;
//    }
    //get
    public int getOrderId() {return this.orderId;}
    public String getName() {return this.name;}
    public String getCreatedDate() {return this.createdDate;}
    public String getPhoneNumber() {return this.phoneNumber;}
    public String getStatus() {return this.status;}
    public String getNote() {return this.note;}
    public String getPromotionId() {return this.promotionId;}
    public String getCustomerId() {return this.customerId;}
    public String getShipperId() {return this.shipperId;}
    public String getAdminId() {return this.adminId;}
    public String getCreated_at() {return this.created_at;}
    public String getUpdated_at() {return this.updated_at;}
    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public Integer getShippingCost() {
        return shippingCost;
    }
    //set
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setShipperId(String shipperId) {
        this.shipperId = shipperId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public void setShippingCost(Integer shippingCost) {
        this.shippingCost = shippingCost;
    }
}

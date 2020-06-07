package com.example.lalafood.API.Req;

import com.google.gson.annotations.SerializedName;

public class Users
{
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("birthDate")
    private String birthDate;
    @SerializedName("gender")
    private String gender;
    @SerializedName("address")
    private String address;
    @SerializedName("email")
    private String email;
    @SerializedName("typeUserId")
    private String typeUserId;
    @SerializedName("status")
    private String status;
    @SerializedName("createdDate")
    private String createdDate;

    //set

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTypeUserId(String typeUserId) {
        this.typeUserId = typeUserId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getTypeUserId() {
        return typeUserId;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}

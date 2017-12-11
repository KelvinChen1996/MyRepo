package com.example.asus.pikachise.model;

import com.example.asus.pikachise.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kelvin on 07/12/2017.
 */

public class Notification {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
    @SerializedName("statusRead")
    @Expose
    private String statusRead;
    @SerializedName("notification_created_at")
    @Expose
    private String notificationCreatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("establishSince")
    @Expose
    private String establishSince;
    @SerializedName("investment")
    @Expose
    private String investment;
    @SerializedName("franchiseFee")
    @Expose
    private String franchiseFee;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("averageRating")
    @Expose
    private Object averageRating;
    @SerializedName("detail")
    @Expose
    private String detail;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getStatusRead() {
        return statusRead;
    }

    public void setStatusRead(String statusRead) {
        this.statusRead = statusRead;
    }

    public String getNotificationCreatedAt() {
        return notificationCreatedAt;
    }

    public void setNotificationCreatedAt(String notificationCreatedAt) {
        this.notificationCreatedAt = notificationCreatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return apiUtils.getUrlImage()+banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLogo() {
        return apiUtils.getUrlImage()+logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEstablishSince() {
        return establishSince;
    }

    public void setEstablishSince(String establishSince) {
        this.establishSince = establishSince;
    }

    public String getInvestment() {
        return investment;
    }

    public void setInvestment(String investment) {
        this.investment = investment;
    }

    public String getFranchiseFee() {
        return franchiseFee;
    }

    public void setFranchiseFee(String franchiseFee) {
        this.franchiseFee = franchiseFee;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Object averageRating) {
        this.averageRating = averageRating;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


//    private int imagefranchise;
//    private String namafranchise;
//    private String time;
//    private String status;
////    private boolean status;
////    private Time time;
//    public Notification(int image, String nama, String status, String time){
//        this.imagefranchise = image;
//        this.namafranchise = nama;
//        this.status = status;
//        this.time = time;
//    }
//
//    public int getImagefranchise() {
//        return imagefranchise;
//    }
//
//    public void setImagefranchise(int imagefranchise) {
//        this.imagefranchise = imagefranchise;
//    }
//
//    public String getNamafranchise() {
//        return namafranchise;
//    }
//
//    public void setNamafranchise(String namafranchise) {
//        this.namafranchise = namafranchise;
//    }
//
////    public boolean isStatus() {
////        return status;
////    }
////
////    public void setStatus(boolean status) {
////        this.status = status;
////    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
////    public Time getTime() {
////        return time;
////    }
////
////    public void setTime(Time time) {
////        this.time = time;
////    }
}

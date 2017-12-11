package com.example.asus.pikachise.model;

import com.example.asus.pikachise.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 04/12/2017.
 */

public class HotListFranchise {
    @SerializedName("nums_favorite")
    @Expose
    private Integer numsFavorite;
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
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
    private String averageRating;
    @SerializedName("detail")
    @Expose
    private String detail;

    public Integer getNumsFavorite() {
        return numsFavorite;
    }

    public void setNumsFavorite(Integer numsFavorite) {
        this.numsFavorite = numsFavorite;
    }

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return apiUtils.getUrlImage() + banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLogo() {
        return apiUtils.getUrlImage() + logo;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
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
}

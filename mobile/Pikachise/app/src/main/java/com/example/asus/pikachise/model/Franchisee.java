package com.example.asus.pikachise.model;

import com.example.asus.pikachise.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 10/12/2017.
 */

public class Franchisee {
    @SerializedName("franchisee_id")
    @Expose
    private String franchiseeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
    @SerializedName("agreement_franchisor_franchisee")
    @Expose
    private String agreementFranchisorFranchisee;
    @SerializedName("outlet_id")
    @Expose
    private String outletId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("telp")
    @Expose
    private String telp;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_join")
    @Expose
    private String dateJoin;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("email")
    @Expose
    private String email;

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

    public String getAgreementFranchisorFranchisee() {
        return apiUtils.getUrlImage() +  agreementFranchisorFranchisee;
    }

    public void setAgreementFranchisorFranchisee(String agreementFranchisorFranchisee) {
        this.agreementFranchisorFranchisee = agreementFranchisorFranchisee;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
}

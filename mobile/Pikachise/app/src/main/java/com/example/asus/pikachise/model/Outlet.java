package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 04/12/2017.
 */

public class Outlet {
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
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

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
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
}

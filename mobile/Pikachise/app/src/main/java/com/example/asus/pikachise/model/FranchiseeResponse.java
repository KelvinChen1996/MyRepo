package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 10/12/2017.
 */

public class FranchiseeResponse {
    @SerializedName("franchisee")
    @Expose
    private List<Franchisee> franchisee = null;

    public List<Franchisee> getFranchisee() {
        return franchisee;
    }

    public void setFranchisee(List<Franchisee> franchisee) {
        this.franchisee = franchisee;
    }
}

package com.example.asus.pikachise.model;

import com.example.asus.pikachise.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 03/12/2017.
 */

public class BrochureList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("franchise_id")
    @Expose
    private String franchiseId;
    @SerializedName("brochure")
    @Expose
    private String brochure;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getBrochure() {
        return apiUtils.getUrlImage() + brochure;
    }

    public void setBrochure(String brochure) {
        this.brochure = brochure;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

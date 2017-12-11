package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 04/12/2017.
 */

public class HotListFranchiseResponse {
    @SerializedName("franchise_list")
    @Expose
    private List<HotListFranchise> hotListFranchises;

    public List<HotListFranchise> getHotListFranchises() {
        return hotListFranchises;
    }

    public void setHotListFranchises(List<HotListFranchise> hotListFranchises) {
        this.hotListFranchises = hotListFranchises;
    }
}

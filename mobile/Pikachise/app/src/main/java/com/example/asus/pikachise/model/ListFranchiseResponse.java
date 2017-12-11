package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 03/12/2017.
 */

public class ListFranchiseResponse {
    @SerializedName("franchise_list")
    @Expose
    private List<ListFranchise> listFranchises;

    public List<ListFranchise> getListFranchises() {
        return listFranchises;
    }

    public void setListFranchises(List<ListFranchise> listFranchises) {
        this.listFranchises = listFranchises;
    }
}

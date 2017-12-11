package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 01/12/2017.
 */

public class MyFranchiseResponse {
    @SerializedName("franchise_list")
    @Expose
    private List<MyFranchise> myFranchiseList;

    public List<MyFranchise> getMyFranchiseList() {
        return myFranchiseList;
    }

    public void setMyFranchiseList(List<MyFranchise> myFranchiseList) {
        this.myFranchiseList = myFranchiseList;
    }
}

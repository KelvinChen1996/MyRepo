
package com.example.asus.pikachise.model;

import com.example.asus.pikachise.model.FranchiseList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyFavorite {

    @SerializedName("franchise_list")
    @Expose
    private List<FranchiseList> franchiseList = null;

    public List<FranchiseList> getFranchiseList() {
        return franchiseList;
    }

    public void setFranchiseList(List<FranchiseList> franchiseList) {
        this.franchiseList = franchiseList;
    }

}

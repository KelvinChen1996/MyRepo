package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 03/12/2017.
 */

public class BrochureListResponse {
    @SerializedName("brochure_list")
    @Expose
    private List<BrochureList> brochureList = null;

    public List<BrochureList> getBrochureList() {
        return brochureList;
    }

    public void setBrochureList(List<BrochureList> brochureList) {
        this.brochureList = brochureList;
    }
}

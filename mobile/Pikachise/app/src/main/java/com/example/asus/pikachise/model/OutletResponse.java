package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 04/12/2017.
 */

public class OutletResponse {
    @SerializedName("outlets")
    @Expose
    private List<Outlet> outlets = null;

    public List<Outlet> getOutlets() {
        return outlets;
    }

    public void setOutlets(List<Outlet> outlets) {
        this.outlets = outlets;
    }
}

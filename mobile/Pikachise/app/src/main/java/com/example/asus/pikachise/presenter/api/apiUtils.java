package com.example.asus.pikachise.presenter.api;

/**
 * Created by WilliamSumitro on 22/11/2017.
 */

public class apiUtils {

    public static final String IP ="http://192.168.1.100/";

    public static final String BASE = IP+"franchise_marketplace_api/public/";
    public static final String BASE_URL = BASE+"api/";
    public static final String BASE_IMAGE = IP+"franchise_marketplace_api/storage/app/public/";

    public static apiService getAPIService(){
        return apiClient.getClient(BASE_URL).create(apiService.class);
    }
    public static String getUrlImage(){
        return BASE_IMAGE;
    }
}

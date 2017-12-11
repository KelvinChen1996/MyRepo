package com.example.asus.pikachise.view.interestedlist.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.MyFranchise;
import com.example.asus.pikachise.model.MyFranchiseResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.interestedlist.adapter.Interested_Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER on 30/11/2017.
 */

public class Interested_List extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<MyFranchise> myFranchiseList;

    private Interested_Adapter myInterested_Adapter;

    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token, email, image, name ,id;
    private ProgressDialog progressDialog;
    private boolean isFavorited = false;
//    private ImageView favorite;


    //    @BindView(R.id.interestedlist_favorited) ImageView favorite;
    @BindView(R.id.interestedlist_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.interestedlist_recyclerview) RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interested_list, container, false);
        ButterKnife.bind(this,view);

//        favorite = (ImageView) view.findViewById(R.id.interestedlist_favorited);
        context = getContext();

        initObject();
        initDataSession();
        initRefresh();
//        initOnclick();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
                Toast.makeText(context, "Franchise Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    private void initDataSession() {
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManagement.USER_EMAIL);
        name = user.get(SessionManagement.USER_NAME);
        image = user.get(SessionManagement.USER_IMAGE);
        token = user.get(SessionManagement.USER_TOKEN);
        id = user.get(SessionManagement.USER_ID);
    }

    private void initRefresh() {
        progressDialog.setMessage("Fetching Your Franchise");
        progressDialog.show();

        myFranchiseList = new ArrayList<>();
        myInterested_Adapter = new Interested_Adapter(myFranchiseList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myInterested_Adapter);
        myInterested_Adapter.notifyDataSetChanged();
        API_MYFRANCHISE();
    }

    private void API_MYFRANCHISE() {
        service.myFavoriteRequest(token)
                .enqueue(new Callback<MyFranchiseResponse>() {
                    @Override
                    public void onResponse(Call<MyFranchiseResponse> call, Response<MyFranchiseResponse> response) {
                        if(response.code() == 401){
                            progressDialog.dismiss();
                            startActivity(new Intent(context, Error401.class));
                        }
                        else{
                            List<MyFranchise> myFranchises = response.body().getMyFranchiseList();
                            recyclerView.setAdapter(new Interested_Adapter(myFranchises, context));
                            recyclerView.smoothScrollToPosition(0);
                            if(swipeRefreshLayout.isRefreshing()){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyFranchiseResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private void initObject(){

        ButterKnife.bind(this.getActivity());
        context = getContext();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        progressDialog = new ProgressDialog(context);
        session = new SessionManagement(getContext());
        service = apiUtils.getAPIService();
    }

    @Override
    public void onRefresh() {
        API_MYFRANCHISE();
    }

}

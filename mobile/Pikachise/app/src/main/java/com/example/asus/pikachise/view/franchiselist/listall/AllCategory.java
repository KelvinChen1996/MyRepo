package com.example.asus.pikachise.view.franchiselist.listall;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.ListFranchiseResponse;
import com.example.asus.pikachise.model.ListFranchise;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategory extends AppCompatActivity {
    @BindView(R.id.allcategory_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.allcategory_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.allcategory_toolbar) Toolbar toolbar;
    @BindView(R.id.allcategory_textview) TextView textView;
    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;
    private AllRVAdapter adapter;
    private List<ListFranchise> listFranchise;
    private final static String INTENT_CATEGORY = "INTENT_CATEGORY";
    private String extraintentcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        initObject();
        initDataSession();
        setupToolbar();
        initGetIntent();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
                Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = ((LinearLayoutManager)recyclerView.getLayoutManager());
                boolean enabled =manager.findFirstCompletelyVisibleItemPosition() == 0;
                swipeRefreshLayout.setEnabled(enabled);
            }
        });

    }
    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        session = new SessionManagement(getApplicationContext());
        service = apiUtils.getAPIService();
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void initRefresh(){
        listFranchise = new ArrayList<>();
        adapter = new AllRVAdapter(listFranchise, context);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.i("debug", "error di refresh" + listFranchise.size());
        getFranchiseAPI();
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
    private void initDataSession(){
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }
    private void getFranchiseAPI(){
        if (extraintentcategory.equals("ALL")){
            allFranchiseAPI();
        }else{
            categoryFranchiseAPI();
        }

    }
    private void allFranchiseAPI(){
        service.franchiselistRquest(token)
                .enqueue(new Callback<ListFranchiseResponse>() {
                    @Override
                    public void onResponse(Call<ListFranchiseResponse> call, Response<ListFranchiseResponse> response) {
                        if(response.code() == 401){
                            responseAPI401();
                        }else if(response.body().getListFranchises().toString().equals("[]")){
                            responseAPI200null();
                        }
                        else{
                            responseAPI200(response);
                        }
                    }
                    @Override
                    public void onFailure(Call<ListFranchiseResponse> call, Throwable t) {
                        responseAPIfailure(t);
                    }
                });
    }
    private void categoryFranchiseAPI(){
        service.franchiselistCategoryRequest(token, extraintentcategory)
                .enqueue(new Callback<ListFranchiseResponse>() {
                    @Override
                    public void onResponse(Call<ListFranchiseResponse> call, Response<ListFranchiseResponse> response) {
                        if(response.code() == 401){
                            responseAPI401();
                        }else if(response.body().getListFranchises().toString().equals("[]")){
                            responseAPI200null();
                        }
                        else{
                            responseAPI200(response);
                        }
                    }
                    @Override
                    public void onFailure(Call<ListFranchiseResponse> call, Throwable t) {
                        responseAPIfailure(t);
                    }
                });
    }
    private void responseAPI401(){
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    private void responseAPI200(Response<ListFranchiseResponse> response){
        List<ListFranchise> listFranchises = response.body().getListFranchises();
        recyclerView.setAdapter(new AllRVAdapter(listFranchises, context));
        recyclerView.smoothScrollToPosition(0);
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    private void responseAPI200null(){
        textView.setVisibility(View.VISIBLE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
    }
    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(INTENT_CATEGORY)){
            extraintentcategory = getintent.getExtras().getString(INTENT_CATEGORY);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
}

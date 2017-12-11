package com.example.asus.pikachise.view.myfranchise.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.MyFranchiseResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.authentication.LoginUser;
import com.example.asus.pikachise.view.myfranchise.adapter.MyFranchiseRVAdapter;
import com.example.asus.pikachise.model.MyFranchise;
import com.example.asus.pikachise.view.home.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFranchiseActivity extends AppCompatActivity {

    public static Activity myfranchiseActivity;

    private List<MyFranchise> myFranchiseList;
    @BindView(R.id.myfranchise_RecyclerView) RecyclerView recyclerView;
    @BindView(R.id.myfranchise_toolbar) Toolbar toolbar;
    @BindView(R.id.myfranchise_add) FloatingActionButton add;
    @BindView(R.id.myfranchise_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.myfranchise_linearlayout) LinearLayout linearLayout;
    @BindView(R.id.myfranchise_textview) TextView textView;
    private MyFranchiseRVAdapter myFranchiseRVAdapter;

    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_franchise);
        initObject();
        initDataSession();
        setupToolbar();
        initRefresh();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = ((LinearLayoutManager)recyclerView.getLayoutManager());
                boolean enabled =manager.findFirstCompletelyVisibleItemPosition() == 0;
                swipeRefreshLayout.setEnabled(enabled);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
                Toast.makeText(context, "Franchise Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RegisterFranchise.class));
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
        myfranchiseActivity =this;
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
        myFranchiseList = new ArrayList<>();
        myFranchiseRVAdapter = new MyFranchiseRVAdapter(myFranchiseList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myFranchiseRVAdapter);
        myFranchiseRVAdapter.notifyDataSetChanged();
        API_MYFRANCHISE();
    }
    private void initDataSession(){
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }
    private void API_MYFRANCHISE(){
    service.myfranchiseRequest(token)
            .enqueue(new Callback<MyFranchiseResponse>() {
                @Override
                public void onResponse(Call<MyFranchiseResponse> call, Response<MyFranchiseResponse> response) {
                    if(response.code() == 401){
                        startActivity(new Intent(context, Error401.class));
                        finish();
                    }else if(response.code() == 200){
                        if(response.body().getMyFranchiseList().toString().equals("[]")){
                            textView.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else{
                            List<MyFranchise> myFranchises = response.body().getMyFranchiseList();
                            recyclerView.setAdapter(new MyFranchiseRVAdapter(myFranchises, context));
                            recyclerView.smoothScrollToPosition(0);
                            if(swipeRefreshLayout.isRefreshing()){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                    else{
                        Toast.makeText(context, "WTF IS HAPPENING AT MYFRANCHISE", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MyFranchiseResponse> call, Throwable t) {
                    Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                }
            });
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyFranchiseActivity.this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbarupdate_home) {
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
            overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

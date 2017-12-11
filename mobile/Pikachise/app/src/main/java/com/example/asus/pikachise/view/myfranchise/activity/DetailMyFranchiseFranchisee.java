package com.example.asus.pikachise.view.myfranchise.activity;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.BrochureList;
import com.example.asus.pikachise.model.BrochureListResponse;
import com.example.asus.pikachise.model.Franchisee;
import com.example.asus.pikachise.model.FranchiseeResponse;
import com.example.asus.pikachise.model.OutletResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.franchiselist.listall.AllRVAdapter;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.example.asus.pikachise.view.myfranchise.adapter.DetailMyFranchiseBrochureAdapter;
import com.example.asus.pikachise.view.myfranchise.adapter.FranchiseeRVAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMyFranchiseFranchisee extends AppCompatActivity {

    private final static String NAME = "NAME";
    private final static String LOGO = "LOGO";
    private final static String BANNER = "BANNER";
    private final static String EMAIL = "EMAIL";
    private final static String USER_ID = "USER_ID";
    private final static String FRANCHISE_ID = "FRANCHISE_ID";
    private final static String CATEGORY = "CATEGORY";
    private final static String TYPE = "TYPE";
    private final static String ESTABLISHSINCE = "ESTABLISHSINCE";
    private final static String INVESTMENT = "INVESTMENT";
    private final static String FRANCHISEFEE = "FRANCHISEFEE";
    private final static String WEBSITE = "WEBSITE";
    private final static String ADDRESS = "ADDRESS";
    private final static String LOCATION = "LOCATION";
    private final static String PHONENUMBER = "PHONENUMBER";
    private final static String DETAIL = "DETAIL";

    @BindView(R.id.franchiseedetailmyfranchise_add) FloatingActionButton add;
    @BindView(R.id.franchiseedetailmyfranchise_rv) RecyclerView recyclerView;
    @BindView(R.id.franchiseedetailmyfranchise_toolbar) Toolbar toolbar;
    @BindView(R.id.franchiseedetailmyfranchise_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.franchiseedetailmyfranchise_textview) TextView textView;
    private FranchiseeRVAdapter adapter;
    private Context context;
    private apiService service;
    private SessionManagement session;
    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid,
            extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee,
            extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail;
    private String token;
    private List<Franchisee> franchiseeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisee_detail_my_franchise);
        initObject();
        initDataSession();
        setupToolbar();
        getactivityIntent();
        initRefresh();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddFranchisee.class);
                initSendData(intent);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
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
        session = new SessionManagement(getApplicationContext());
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        service = apiUtils.getAPIService();
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void initDataSession(){
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
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
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void getactivityIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(FRANCHISEFEE)){
            extraname = getintent.getExtras().getString(NAME);
            extralogo = getintent.getExtras().getString(LOGO);
            extrabanner = getintent.getExtras().getString(BANNER);
            extraemail = getintent.getExtras().getString(EMAIL);
            extrauserid = getintent.getExtras().getString(USER_ID);
            extrafranchiseid = getintent.getExtras().getString(FRANCHISE_ID);
            extracategory = getintent.getExtras().getString(CATEGORY);
            extratype = getintent.getExtras().getString(TYPE);
            extraestablishsince = getintent.getExtras().getString(ESTABLISHSINCE);
            extrainvesment = getintent.getExtras().getString(INVESTMENT);
            extrafranchisefee = getintent.getExtras().getString(FRANCHISEFEE);
            extrawebsite = getintent.getExtras().getString(WEBSITE);
            extraaddress = getintent.getExtras().getString(ADDRESS);
            extralocation = getintent.getExtras().getString(LOCATION);
            extraphonenumber = getintent.getExtras().getString(PHONENUMBER);
            extradetail = getintent.getExtras().getString(DETAIL);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void initSendData(Intent intent){
        intent.putExtra(USER_ID, extrauserid);
        intent.putExtra(FRANCHISE_ID, extrafranchiseid);
        intent.putExtra(NAME, extraname);
        intent.putExtra(BANNER, extrabanner);
        intent.putExtra(LOGO, extralogo);
        intent.putExtra(CATEGORY, extracategory);
        intent.putExtra(TYPE, extratype);
        intent.putExtra(ESTABLISHSINCE, extraestablishsince);
        intent.putExtra(INVESTMENT, extrainvesment);
        intent.putExtra(FRANCHISEFEE, extrafranchisefee);
        intent.putExtra(WEBSITE, extrawebsite);
        intent.putExtra(ADDRESS, extraaddress);
        intent.putExtra(LOCATION, extralocation);
        intent.putExtra(PHONENUMBER, extraphonenumber);
        intent.putExtra(EMAIL, extraemail);
        intent.putExtra(DETAIL, extradetail);
    }
    private void initRefresh(){
        franchiseeList = new ArrayList<>();
        adapter = new FranchiseeRVAdapter(franchiseeList, context, extralogo, extraname);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        apimyoutlet();
    }
    private void apimyoutlet(){
        service.getmyfranchisee(token, extrafranchiseid)
                .enqueue(new Callback<FranchiseeResponse>() {
                    @Override
                    public void onResponse(Call<FranchiseeResponse> call, Response<FranchiseeResponse> response) {
                        if(response.code() == 401){
                            responseAPI401();
                        }else if(response.body().getFranchisee().toString().equals("[]")){
                            responseAPI200null();
                        }
                        else{
                            responseAPI200(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<FranchiseeResponse> call, Throwable t) {
                        responseAPIfailure(t);
                    }
                });
    }
    private void responseAPI401(){
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    private void responseAPI200null(){
        textView.setVisibility(View.VISIBLE);
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    private void responseAPI200(Response<FranchiseeResponse> response){
        List<Franchisee> franchiseeList = response.body().getFranchisee();
        recyclerView.setAdapter(new FranchiseeRVAdapter(franchiseeList, context, extralogo, extraname));
        recyclerView.smoothScrollToPosition(0);
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        textView.setVisibility(View.GONE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
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
                DetailMyFranchise.detailmyfranchiseActivity.finish();
            MyFranchiseActivity.myfranchiseActivity.finish();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

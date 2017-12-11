package com.example.asus.pikachise.view.franchisedetail.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
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
import com.example.asus.pikachise.model.BrochureList;
import com.example.asus.pikachise.model.BrochureListResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.franchisedetail.adapter.BrochuresAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseDetailBrochures extends Fragment {
    @BindView(R.id.brochure_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.brochure_nestedscrollview) NestedScrollView nestedScrollView;
    @BindView(R.id.brochure_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private apiService service;
    private SessionManagement session;

    private final static String NAME = "NAME";
    private final static String LOGO = "LOGO";
    private final static String BANNER = "BANNER";
    private final static String EMAIL = "EMAIL";
    private final static String AVERAGE_RATING = "AVERAGE_RATING";
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

    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid,
            extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee,
            extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail;
    private String token;

    private List<BrochureList> brochureList;
    private BrochuresAdapter adapter;

    public FranchiseDetailBrochures() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_franchise_detail_brochures, container, false);
        initObject(view);
        initDataSession();
        getBundleData();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
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
        return view;
    }
    private void initObject(View view){
        context = getActivity();
        ButterKnife.bind(this, view);
        session = new SessionManagement(context.getApplicationContext());
        service = apiUtils.getAPIService();
    }
    private void initDataSession(){
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }
    private void initRefresh(){
        brochureList = new ArrayList<>();
        adapter = new BrochuresAdapter(brochureList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        apiMyBrochure();
    }
    private void apiMyBrochure(){
        service.brochurelistRequest(token, extrafranchiseid)
                .enqueue(new Callback<BrochureListResponse>() {
                    @Override
                    public void onResponse(Call<BrochureListResponse> call, Response<BrochureListResponse> response) {
                        if(response.code() ==401 || response.code() ==500){
                            responseAPI200null();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if(response.body().getBrochureList().toString().equals("[]")){
                            responseAPI200null();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else{
                            responseAPI200(response);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                    @Override
                    public void onFailure(Call<BrochureListResponse> call, Throwable t) {
                        responseAPIfailure(t);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }
    private void responseAPI200(Response<BrochureListResponse> response){
        List<BrochureList> brochureList = response.body().getBrochureList();
        recyclerView.setAdapter(new BrochuresAdapter(brochureList, context));
        recyclerView.smoothScrollToPosition(0);
    }
    private void responseAPI200null(){
        nestedScrollView.setVisibility(View.VISIBLE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
    }
    private void getBundleData(){
        Bundle args = getArguments();
        if(args.getString(FRANCHISE_ID) != null){
            extraname = args.getString(NAME);
            extralogo = args.getString(LOGO);
            extrabanner = args.getString(BANNER);
            extraemail = args.getString(EMAIL);
            extrafranchiseid = args.getString(FRANCHISE_ID);
            extracategory = args.getString(CATEGORY);
            extratype = args.getString(TYPE);
            extraestablishsince = args.getString(ESTABLISHSINCE);
            extrainvesment = args.getString(INVESTMENT);
            extrafranchisefee = args.getString(FRANCHISEFEE);
            extrawebsite = args.getString(WEBSITE);
            extraaddress = args.getString(ADDRESS);
            extralocation = args.getString(LOCATION);
            extraphonenumber = args.getString(PHONENUMBER);
            extradetail = args.getString(DETAIL);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
}

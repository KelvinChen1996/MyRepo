package com.example.asus.pikachise.view.home.homehot.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.HotListFranchise;
import com.example.asus.pikachise.model.HotListFranchiseResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.home.homehot.adapter.HotRVAdapter;

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
public class HotFragment extends Fragment{
    @BindView(R.id.top_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.top_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.top_textview) TextView textView;
    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;
    private HotRVAdapter adapter;
    private List<HotListFranchise> hotListFranchises;

    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        initObject(view);
        initDataSession();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        return view;
    }
    private void initObject(View view){
        ButterKnife.bind(this,view);
        context = getActivity();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        session = new SessionManagement(context.getApplicationContext());
        service = apiUtils.getAPIService();
    }
    private void initDataSession(){
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        hotListFranchises = new ArrayList<>();
        adapter = new HotRVAdapter(hotListFranchises, context, token);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL ));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        apiHotFranchise();
    }
    private void apiHotFranchise(){
        service.hotFranchiseRequest(token, "9")
                .enqueue(new Callback<HotListFranchiseResponse>() {
                    @Override
                    public void onResponse(Call<HotListFranchiseResponse> call, Response<HotListFranchiseResponse> response) {
                        if (response.code() == 401 || response.code() == 500) {
                            responseAPI200null();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (response.body().getHotListFranchises().toString().equals("[]")) {
                            if (response.code() == 401 || token == null) {
                                Intent myIntent = new Intent(getActivity(), Error401.class);
                                getActivity().startActivity(myIntent);
                                getActivity().finish();
                            } else if (response.body().getHotListFranchises().toString().equals("[]")) {
                                responseAPI200null();
                                swipeRefreshLayout.setRefreshing(false);
                            } else {
                                responseAPI200(response);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HotListFranchiseResponse> call, Throwable t) {
                            responseAPIfailure(t);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });

    }


    private void responseAPI200(Response<HotListFranchiseResponse> response){
        List<HotListFranchise> hotListFranchises = response.body().getHotListFranchises();
        recyclerView.setAdapter(new HotRVAdapter(hotListFranchises, context, token));
        recyclerView.smoothScrollToPosition(0);
    }
    private void responseAPI200null(){
        textView.setVisibility(View.VISIBLE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "There is a problem with upir connection", Toast.LENGTH_LONG).show();

    }

}

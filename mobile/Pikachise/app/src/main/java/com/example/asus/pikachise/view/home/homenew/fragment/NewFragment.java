package com.example.asus.pikachise.view.home.homenew.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.BrochureList;
import com.example.asus.pikachise.model.BrochureListResponse;
import com.example.asus.pikachise.model.ListFranchise;
import com.example.asus.pikachise.model.ListFranchiseResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.franchisedetail.adapter.BrochuresAdapter;
import com.example.asus.pikachise.view.home.homenew.adapter.NewRVAdapter;
import com.example.asus.pikachise.model.ItemNewFranchise;

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
public class NewFragment extends Fragment{
    @BindView(R.id.new_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.new_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.new_textview) TextView textView;
    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;
    private NewRVAdapter adapter;
    private List<ListFranchise> franchiseList;
    public NewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        initObject(view);
        initDataSession();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
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
        franchiseList = new ArrayList<>();
        adapter = new NewRVAdapter(franchiseList, context, token);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL ));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        apiNewFranchise();
    }
    private void apiNewFranchise(){
        service.newFranchiseRequest(token, "7")
                .enqueue(new Callback<ListFranchiseResponse>() {
                    @Override
                    public void onResponse(Call<ListFranchiseResponse> call, Response<ListFranchiseResponse> response) {
                        if (response.code()==401 || token == null){
                            Intent myIntent = new Intent(getActivity(), Error401.class);
                            getActivity().startActivity(myIntent);
                            getActivity().finish();
                        }
                        else if(response.body().getListFranchises().toString().equals("[]")){
                            responseAPI200null();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else{
                            responseAPI200(response);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListFranchiseResponse> call, Throwable t) {
                        responseAPIfailure(t);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }
    private void responseAPI200(Response<ListFranchiseResponse> response){
        List<ListFranchise> listFranchises = response.body().getListFranchises();
        recyclerView.setAdapter(new NewRVAdapter(listFranchises, context, token));
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

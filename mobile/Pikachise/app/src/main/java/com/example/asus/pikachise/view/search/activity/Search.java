package com.example.asus.pikachise.view.search.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.ListFranchise;
import com.example.asus.pikachise.model.ListFranchiseResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.example.asus.pikachise.view.search.adapter.SearchRVAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener{
    @BindView(R.id.search_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.search_toolbar) Toolbar toolbar;
    @BindView(R.id.search_textview) TextView textView;
    private SearchRVAdapter adapter;

    private List<ListFranchise> listFranchise;
    private List<ListFranchise> newList;

    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;



    private final static String INTENT_CATEGORY = "INTENT_CATEGORY";
    private String extraintentcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        context = this;
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();

        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initObject();



//        setupToolbar();

    }



    private void initObject(){
        listFranchise = new ArrayList<>();
        adapter = new SearchRVAdapter(listFranchise, context);

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        Log.i("debug", "error di object" + listFranchise.size());
        allFranchiseAPI();
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
                Intent intent = new Intent(Search.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }


    private void allFranchiseAPI(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                listFranchise.clear();
                service.franchiselistRquest(token)
                        .enqueue(new Callback<ListFranchiseResponse>() {
                            @Override
                            public void onResponse(Call<ListFranchiseResponse> call, Response<ListFranchiseResponse> response) {
                                if(response.code() == 401){
                                    responseAPI401();
                                }else if(response.body().getListFranchises() == null){
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

                return null;
            }
        }.execute();
    }
    private void responseAPI401(){
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    private void responseAPI200(Response<ListFranchiseResponse> response){
        List<ListFranchise> listFranchises = response.body().getListFranchises();
        listFranchise = listFranchises;
        recyclerView.setAdapter(new SearchRVAdapter(listFranchises, context));
        recyclerView.smoothScrollToPosition(0);
    }
    private void responseAPI200null(){
        textView.setVisibility(View.VISIBLE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        newList = new ArrayList<>();
        for (ListFranchise daftarFranchise : listFranchise)
        {
            String name = daftarFranchise.getName().toLowerCase();
            if(name.contains(newText)){
                newList.add(daftarFranchise);
            }
        }
        adapter.setFilter(newList);
        recyclerView.setAdapter(adapter);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.search);
        final MenuItem search = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Cari Nama Franchise");
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}


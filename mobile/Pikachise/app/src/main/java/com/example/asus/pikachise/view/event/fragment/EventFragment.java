package com.example.asus.pikachise.view.event.fragment;


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
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Event;
import com.example.asus.pikachise.model.EventResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.event.adapter.EventRVAdapter;

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
public class EventFragment extends Fragment{
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_JUDUL = "EXTRA_JUDUL";
    private static final String EXTRA_ISI = "EXTRA_ISI";
    private static final String EXTRA_JADWAL = "EXTRA_JADWAL";
    private static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";
    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";

    @BindView(R.id.rec_list_Event) RecyclerView recView;
    @BindView(R.id.event_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.event_textview) TextView textView;
    private EventRVAdapter adapter;
    private List<Event> listevents;
    private ArrayList listEventClass;
    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
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
        listevents = new ArrayList<>();
        adapter = new EventRVAdapter(listevents, context, token);
        recView.setLayoutManager(new LinearLayoutManager(context));
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getEvent();
    }
    private void getEvent(){
        service.getEvent(token)
                .enqueue(new Callback<EventResponse>() {
                    @Override
                    public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                        if (response.code()==401 || token == null){
                            Intent myIntent = new Intent(getActivity(), Error401.class);
                            getActivity().startActivity(myIntent);
                            swipeRefreshLayout.setRefreshing(false);
                            getActivity().finish();
                        }
                        else if(response.body().getEvents().toString().equals("[]")){
                            responseAPI200null();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else{
                            responseAPI200(response);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<EventResponse> call, Throwable t) {
                        responseAPIfailure(t);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }
    private void responseAPI200(Response<EventResponse> response){
        List<Event> events = response.body().getEvents();
        listevents= events;
        recView.setAdapter(new EventRVAdapter(events, context, token));
        recView.smoothScrollToPosition(0);
    }
    private void responseAPI200null(){
        textView.setVisibility(View.VISIBLE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "There is a problem with your connection", Toast.LENGTH_LONG).show();

    }
}
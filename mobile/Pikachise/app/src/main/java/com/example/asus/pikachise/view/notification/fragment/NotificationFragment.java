package com.example.asus.pikachise.view.notification.fragment;


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
import com.example.asus.pikachise.model.Notification;
import com.example.asus.pikachise.model.NotificationResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.notification.adapter.NotificationRVAdapter;

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
public class NotificationFragment extends Fragment implements /*NotificationRVItemTouch.NotificationRVItemTouchListener */ SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.notification_recyclervidw) RecyclerView recyclerView;
    @BindView(R.id.notification_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    private List<Notification> notificationList = new ArrayList<>();
    private NotificationRVAdapter notificationRVAdapter;
    Context context;

    String token;
    apiService service;
    SessionManagement session;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        context = container.getContext();

        session = new SessionManagement(context);
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();

        ButterKnife.bind(this,view);
        notificationRVAdapter = new NotificationRVAdapter(notificationList,context,token);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notificationRVAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                GET_notificationAPI();
            }
        });

//        ItemTouchHelper.SimpleCallback simpleCallback = new NotificationRVItemTouch(0, ItemTouchHelper.LEFT, this);
//        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);


        return view;
    }

    private void GET_notificationAPI() {
        service.myNotificationRequest(token)
                .enqueue(new Callback<NotificationResponse>() {
                    @Override
                    public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                        if(response.code() == 401){
                            startActivity(new Intent(context, Error401.class));
                        }
                        else{
                            List<Notification> myNotification = response.body().getNotifications();
                            recyclerView.setAdapter(new NotificationRVAdapter(myNotification, context,token));
                            recyclerView.smoothScrollToPosition(0);
                            if(swipeRefreshLayout.isRefreshing()){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    public void onRefresh() {
        notificationRVAdapter.notifyItemRangeRemoved(0,notificationList.size());
        notificationList.clear();
        GET_notificationAPI();
//        notificationData();
    }

//    private void notificationData(){
//        Notification notification = new Notification(R.drawable.burgerkinglogo, "Burger King", "Upload new status", "24 secs ago");
//        Notification notification1 = new Notification(R.drawable.mypubfoodlogo, "My Pub Food", "Upload new status", "30 mins ago");
//        Notification notification2 = new Notification(R.drawable.mypizzalogo, "My Pizza", "Upload new status", "50 mins ago");
//        Notification notification3 = new Notification(R.drawable.starbuckslogo, "Starbucks", "Upload new status", "1 hour ago");
//        notificationList.add(notification);
//        notificationList.add(notification1);
//        notificationList.add(notification2);
//        notificationList.add(notification3);
//        swipeRefreshLayout.setRefreshing(false);
//    }

//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        if (viewHolder instanceof NotificationRVAdapter.ViewHolder){
//            String name = notificationList.get(viewHolder.getAdapterPosition()).getName();
//
//            final Notification deletedNotification = notificationList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();
//
//            notificationRVAdapter.removeItem(viewHolder.getAdapterPosition());
//
//            Snackbar snackbar = Snackbar
//                    .make(swipeRefreshLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // undo is selected, restore the deleted item
//                    notificationRVAdapter.restoreItem(deletedNotification, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
//        }
//    }

//    @Override
//    public void onRefresh() {
////        notificationRVAdapter.notifyItemRangeRemoved(0,notificationList.size());
//        notificationList.clear();
//        GET_notificationAPI();
////        notificationData();
//    }
}
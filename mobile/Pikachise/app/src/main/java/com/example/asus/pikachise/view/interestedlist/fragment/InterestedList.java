//package com.example.asus.pikachise.view.interestedlist.fragment;
//
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.asus.pikachise.R;
//import com.example.asus.pikachise.presenter.helper.DatabaseHelper;
//import com.example.asus.pikachise.view.interestedlist.adapter.InterestedAdapter;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class InterestedList extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
//    @BindView(R.id.interestedlist_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.interestedlist_recyclerview) RecyclerView recyclerView;
//    private ArrayList listhomedata;
//    private InterestedAdapter interestedAdapter;
//    private DatabaseHelper databaseHelper;
//    private Context context;
//    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
//    private static final String EXTRA_ID = "EXTRA_ID";
//    private static final String EXTRA_NAMA = "EXTRA_NAMA";
//    private static final String EXTRA_KETERANGAN = "EXTRA_KETERANGAN";
//    private static final String EXTRA_JENIS = "EXTRA_JENIS";
//    private static final String EXTRA_KATEGORI = "EXTRA_KATEGORI";
//    private static final String EXTRA_BERDIRI = "EXTRA_BERDIRI";
//    private static final String EXTRA_INVESTASI = "EXTRA_INVESTASI";
//    private static final String EXTRA_WEBSITE= "EXTRA_WEBSITE";
//    private static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";
//    private static final String EXTRA_LOKASI = "EXTRA_LOKASI";
//    private static final String EXTRA_TELEPON = "EXTRA_TELEPON";
//    private static final String EXTRA_EMAIL = "EXTRA_EMAIL";
//    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";
//    public InterestedList() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_interested_list, container, false);
//        ButterKnife.bind(this,view);
//        initObject();
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                getDataFromSQLite();
//            }
//        });
//        return view;
//    }
//    private void initObject(){
//        listhomedata = new ArrayList<>();
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setHasFixedSize(true);
//        interestedAdapter = new InterestedAdapter(listhomedata, getActivity());
//        recyclerView.setAdapter(interestedAdapter);
//
//        databaseHelper = new DatabaseHelper(this.getActivity());
//
//        getDataFromSQLite();
//    }
//    private void getDataFromSQLite() {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                listhomedata.clear();
//                listhomedata.addAll(databaseHelper.getHomeFranchise());
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                interestedAdapter.notifyDataSetChanged();
//            }
//        }.execute();
//        swipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onRefresh() {
//        getDataFromSQLite();
//    }
//}

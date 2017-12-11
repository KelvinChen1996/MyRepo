package com.example.asus.pikachise.view.franchisedetail.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.ReviewRating;
import com.example.asus.pikachise.model.ReviewRatingResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.franchisedetail.adapter.ChatArrayRVAdapter;
import com.example.asus.pikachise.view.home.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseDetailReviewandRating extends Fragment {
    private static final String TAG = "ChatActivity";
    private ChatArrayRVAdapter adapter;
    @BindView(R.id.franchisedetailreviewandrating_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.franchisedetailreviewandrating_editmessage) EditText chatText;
    @BindView(R.id.franchisedetailreviewandrating_send) ImageView buttonSend;
    @BindView(R.id.franchisedetailreviewandrating_container) LinearLayout container;
    @BindView(R.id.franchisedetailreviewandrating_allowornot) LinearLayout allowornot;
    @BindView(R.id.franchisedetailreviewandrating_ratingbar) RatingBar ratingBar;
    @BindView(R.id.franchisedetailreviewandrating_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.franchisedetailreviewandrating_nestedscrollview) NestedScrollView nestedScrollView;

    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;
    private List<ReviewRating> reviewRatings;

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
    Boolean isallow = false;
    private View view;

    public FranchiseDetailReviewandRating() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_franchise_detail_reviewand_rating, container, false);
        initObject(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (!isInLayout()) {
                recyclerView.requestLayout();
            }
        }
        initDataSession();
        getBundleData();
        allowAPI();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allowAPI();
                swipeRefreshLayout.setRefreshing(true);
                initRefresh();
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendreviewratingAPI();
            }
        });

        return view;
    }

    private void initRefresh(){
        reviewRatings = new ArrayList<>();
        adapter = new ChatArrayRVAdapter(reviewRatings, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getreviewratingAPI();
    }
    private void initObject(View view){
        ButterKnife.bind(this,view);
        context = getActivity();
        session = new SessionManagement(context.getApplicationContext());
        service = apiUtils.getAPIService();
    }
    private void initDataSession(){
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
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
    private void allowAPI(){
        service.allowRequest(token, extrafranchiseid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try{
                            JSONObject jsonResults = new JSONObject(response.body().string());
                            isallow = Boolean.valueOf(jsonResults.getString("allow"));
                            if(isallow){
                                allowornot.setVisibility(View.VISIBLE);
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)container.getLayoutParams();
                                params.setMargins(0,0,0,200);
                                container.requestLayout();
                            }
                            else{
                                allowornot.setVisibility(View.GONE);
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)container.getLayoutParams();
                                params.setMargins(0,0,0,0);
                                container.requestLayout();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        responseAPIfailure(t);
                    }
                });
    }
    private void sendreviewratingAPI(){
        if(TextUtils.isEmpty(chatText.getText())){
            Snackbar.make(container, "Please type your comment first !", Snackbar.LENGTH_SHORT).show();
            return;
        }
        else if(ratingBar.getRating() == 0.0){
            Snackbar.make(container, "Please click the star !", Snackbar.LENGTH_LONG).show();
            return;
        }
        service.addreviewratingRequest(token, extrafranchiseid, String.valueOf(ratingBar.getRating()), chatText.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                Boolean bool = jsonResults.getBoolean("success");
                                if(bool){
                                    Toast.makeText(context, "Thank you for your rating", Toast.LENGTH_SHORT).show();
                                    allowornot.setVisibility(View.GONE);allowornot.setVisibility(View.GONE);
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)container.getLayoutParams();
                                    params.setMargins(0,0,0,0);
                                    container.requestLayout();
                                    initRefresh();
                                }
                                if(jsonResults.getString("error").equals("something went wrong, try again later")){
                                    String message = jsonResults.getString("error");
                                    Snackbar.make(container, message, Snackbar.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        responseAPIfailure(t);
                    }
                });
    }
    private void getreviewratingAPI(){
        service.getreviewratingRequest(token, extrafranchiseid)
                .enqueue(new Callback<ReviewRatingResponse>() {
                    @Override
                    public void onResponse(Call<ReviewRatingResponse> call, Response<ReviewRatingResponse> response) {
                        if(response.body().getReviewRating().toString().equals("[]") && isallow.equals(true)){
                            responseAPI200(response);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else if(response.body().getReviewRating().toString().equals("[]")){
                            responseAPI200null();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else{
                            responseAPI200(response);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewRatingResponse> call, Throwable t) {
                        responseAPIfailure(t);
                    }
                });
    }
    private void responseAPI200(Response<ReviewRatingResponse> response){
        List<ReviewRating> reviewRating = response.body().getReviewRating();
        reviewRatings = reviewRating;
        recyclerView.setAdapter(new ChatArrayRVAdapter(reviewRatings, context));
        recyclerView.smoothScrollToPosition(0);
        nestedScrollView.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }
    private void responseAPI200null(){
        nestedScrollView.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
    }
    private void responseAPIfailure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
    }
}

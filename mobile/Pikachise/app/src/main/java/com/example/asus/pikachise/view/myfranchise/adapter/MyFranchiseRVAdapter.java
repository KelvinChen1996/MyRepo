package com.example.asus.pikachise.view.myfranchise.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.MyFranchise;
import com.example.asus.pikachise.view.myfranchise.activity.DetailMyFranchise;
import com.example.asus.pikachise.view.myfranchise.activity.PendingDetailMyFranchise;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 01/10/2017.
 */

public class MyFranchiseRVAdapter extends RecyclerView.Adapter<MyFranchiseRVAdapter.ViewHolder> {
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

    private List<MyFranchise> myFranchiseList;
    private Context context;
    private String status = "";
    public MyFranchiseRVAdapter(List<MyFranchise> myFranchises, Context context){
        this.myFranchiseList = myFranchises;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myfranchise, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MyFranchise current = myFranchiseList.get(position);
        holder.name.setText(current.getName());
//        Glide.with(context)
//                .load(current.getLogo())
//                .placeholder(R.drawable.logo404)
//                .into(holder.logo);
        Picasso.with(context)
                .load(current.getLogo())
                .placeholder(R.drawable.logo404)
                .into(holder.logo);
        status = current.getStatus();
        if(status.equals("pending")){
            holder.status.setImageResource(R.drawable.pending);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PendingDetailMyFranchise.class);
                    intent.putExtra(USER_ID, current.getUserId());
                    intent.putExtra(FRANCHISE_ID, current.getFranchiseId());
                    intent.putExtra(NAME, current.getName());
                    intent.putExtra(BANNER, current.getBanner());
                    intent.putExtra(LOGO, current.getLogo());
                    intent.putExtra(CATEGORY, current.getCategory());
                    intent.putExtra(TYPE, current.getType());
                    intent.putExtra(ESTABLISHSINCE, current.getEstablishSince());
                    intent.putExtra(INVESTMENT, current.getInvestment());
                    intent.putExtra(FRANCHISEFEE, current.getFranchiseFee());
                    intent.putExtra(WEBSITE, current.getWebsite());
                    intent.putExtra(ADDRESS, current.getAddress());
                    intent.putExtra(LOCATION, current.getLocation());
                    intent.putExtra(PHONENUMBER, current.getPhoneNumber());
                    intent.putExtra(EMAIL, current.getEmail());
                    intent.putExtra(DETAIL, current.getDetail());
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            });

        }
        else if(status.equals("active")){
            holder.status.setImageResource(R.drawable.verified);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailMyFranchise.class);
                    intent.putExtra(USER_ID, current.getUserId());
                    intent.putExtra(FRANCHISE_ID, current.getFranchiseId());
                    intent.putExtra(NAME, current.getName());
                    intent.putExtra(BANNER, current.getBanner());
                    intent.putExtra(LOGO, current.getLogo());
                    intent.putExtra(CATEGORY, current.getCategory());
                    intent.putExtra(TYPE, current.getType());
                    intent.putExtra(ESTABLISHSINCE, current.getEstablishSince());
                    intent.putExtra(INVESTMENT, current.getInvestment());
                    intent.putExtra(FRANCHISEFEE, current.getFranchiseFee());
                    intent.putExtra(WEBSITE, current.getWebsite());
                    intent.putExtra(ADDRESS, current.getAddress());
                    intent.putExtra(LOCATION, current.getLocation());
                    intent.putExtra(PHONENUMBER, current.getPhoneNumber());
                    intent.putExtra(EMAIL, current.getEmail());
                    intent.putExtra(DETAIL, current.getDetail());
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            });
        }
        else{
            holder.status.setImageResource(R.drawable.reject);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PendingDetailMyFranchise.class);
                    intent.putExtra(USER_ID, current.getUserId());
                    intent.putExtra(FRANCHISE_ID, current.getFranchiseId());
                    intent.putExtra(NAME, current.getName());
                    intent.putExtra(BANNER, current.getBanner());
                    intent.putExtra(LOGO, current.getLogo());
                    intent.putExtra(CATEGORY, current.getCategory());
                    intent.putExtra(TYPE, current.getType());
                    intent.putExtra(ESTABLISHSINCE, current.getEstablishSince());
                    intent.putExtra(INVESTMENT, current.getInvestment());
                    intent.putExtra(FRANCHISEFEE, current.getFranchiseFee());
                    intent.putExtra(WEBSITE, current.getWebsite());
                    intent.putExtra(ADDRESS, current.getAddress());
                    intent.putExtra(LOCATION, current.getLocation());
                    intent.putExtra(PHONENUMBER, current.getPhoneNumber());
                    intent.putExtra(EMAIL, current.getEmail());
                    intent.putExtra(DETAIL, current.getDetail());
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideleft, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myFranchiseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemmyfranchise_container) LinearLayout container;
        @BindView(R.id.itemmyfranchise_logo) ImageView logo;
        @BindView(R.id.itemmyfranchise_status) ImageView status;
        @BindView(R.id.itemmyfranchise_name) TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.example.asus.pikachise.view.myfranchise.adapter;

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
import com.example.asus.pikachise.model.Franchisee;
import com.example.asus.pikachise.model.MyFranchise;
import com.example.asus.pikachise.model.Outlet;
import com.example.asus.pikachise.view.myfranchise.activity.EditFranchisee;
import com.example.asus.pikachise.view.myfranchise.activity.PendingDetailMyFranchise;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 25/11/2017.
 */

public class FranchiseeRVAdapter extends RecyclerView.Adapter<FranchiseeRVAdapter.ViewHolder> {
    private final static String DATE_JOIN = "DATE_JOIN";
    private final static String OUTLET_ID = "OUTLET_ID";
    private final static String ADDRESS = "ADDRESS";
    private final static String PHONENUMBER = "PHONENUMBER";
    private final static String USER_ID = "USER_ID";
    private final static String EMAIL = "EMAIL";
    private final static String NAME = "NAME";

    private List<Franchisee> franchiseeList;
    private LayoutInflater inflater;
    Context context;
    private String logo, franchisename;
    public FranchiseeRVAdapter(List<Franchisee> franchiseeList, Context c, String logo, String franchisename){
        this.inflater = LayoutInflater.from(c);
        this.franchiseeList = franchiseeList;
        this.logo = logo;
        this.franchisename = franchisename;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_franchisee, parent, false);
        context = parent.getContext();
        return new FranchiseeRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Franchisee current = franchiseeList.get(position);
        if(position%4==0){
            holder.itemView.setBackgroundResource(R.color.superlightblue);
        }
        else if(position%4==1){
            holder.itemView.setBackgroundResource(R.color.superlightred);
        }
        else if(position%4==2){
            holder.itemView.setBackgroundResource(R.color.superlightpurple);
        }
        else {
            holder.itemView.setBackgroundResource(R.color.superlightyellow);
        }


        holder.owner.setText(current.getName());
        holder.address.setText(current.getAddress());
        holder.phonenumber.setText(current.getTelp());
        holder.since.setText(current.getDateJoin());
        Picasso.with(context)
                .load(logo)
                .placeholder(R.drawable.logo404)
                .into(holder.logo);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditFranchisee.class);
                intent.putExtra(OUTLET_ID, current.getOutletId());
                intent.putExtra(ADDRESS, current.getAddress());
                intent.putExtra(PHONENUMBER, current.getTelp());
                intent.putExtra(DATE_JOIN, current.getDateJoin());
                intent.putExtra(USER_ID, current.getUserId());
                intent.putExtra(EMAIL, current.getEmail());
                intent.putExtra(NAME, current.getName());
                Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return franchiseeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemfranchisee_edit) ImageView edit;
        @BindView(R.id.itemfranchisee_franchiseename) TextView franchiseename;
        @BindView(R.id.itemfranchisee_layout) LinearLayout linearLayout;
        @BindView(R.id.itemfranchisee_address) TextView address;
        @BindView(R.id.itemfranchisee_logo) ImageView logo;
        @BindView(R.id.itemfranchisee_owner) TextView owner;
        @BindView(R.id.itemfranchisee_phonenumber) TextView phonenumber;
        @BindView(R.id.itemfranchisee_since) TextView since;
        @BindView(R.id.itemfranchisee_status) ImageView status;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

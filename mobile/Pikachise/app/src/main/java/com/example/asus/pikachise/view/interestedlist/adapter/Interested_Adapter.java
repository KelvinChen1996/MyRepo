package com.example.asus.pikachise.view.interestedlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.MyFranchise;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by WilliamSumitro on 01/10/2017.
 */

public class Interested_Adapter extends RecyclerView.Adapter<Interested_Adapter.ViewHolder> {
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
    private String status;

    public Interested_Adapter(List<MyFranchise> myFranchises, Context context){
        this.myFranchiseList = myFranchises;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interestedlist, parent, false);
        return new Interested_Adapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyFranchise current = myFranchiseList.get(position);
        holder.namafranchise.setText(current.getName());
        Picasso.with(context)
                .load(current.getBanner())
                .placeholder(R.drawable.logo404)
                .into(holder.gambarbanner);
        holder.container.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, FranchiseDetail.class);
                        intent.putExtra(NAME,current.getName());
                        intent.putExtra(LOGO,current.getLogo());
                        intent.putExtra(BANNER,current.getBanner());
                        intent.putExtra(EMAIL,current.getEmail());
                        intent.putExtra(USER_ID,current.getUserId());
                        intent.putExtra(FRANCHISE_ID,current.getFranchiseId());
                        intent.putExtra(CATEGORY,current.getCategory());
                        intent.putExtra(TYPE,current.getType());
                        intent.putExtra(ESTABLISHSINCE,current.getEstablishSince());
                        intent.putExtra(INVESTMENT,current.getInvestment());
                        intent.putExtra(FRANCHISEFEE,current.getFranchiseFee());
                        intent.putExtra(WEBSITE,current.getWebsite());
                        intent.putExtra(ADDRESS,current.getAddress());
                        intent.putExtra(LOCATION,current.getLocation());
                        intent.putExtra(PHONENUMBER,current.getPhoneNumber());
                        intent.putExtra(DETAIL,current.getDetail());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return myFranchiseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namafranchise, namaptfranchise, nfranchisee;
        private ImageView gambarbanner;
        private View container;


        public ViewHolder(View itemView) {
            super(itemView);
            namafranchise = (TextView) itemView.findViewById(R.id.topitem_namefranchise);
            gambarbanner = (ImageView) itemView.findViewById(R.id.topitem_image);
            container = itemView.findViewById(R.id.topitem_conitemroot);
        }

    }
}

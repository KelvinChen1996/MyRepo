package com.example.asus.pikachise.view.franchiselist.listall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.ListFranchise;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 03/12/2017.
 */

public class AllRVAdapter extends RecyclerView.Adapter<AllRVAdapter.ViewHolder> {
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


    private Context context;
    private List<ListFranchise> listFranchises;

    public AllRVAdapter(List<ListFranchise> listFranchises, Context context){
        this.context = context;
        this.listFranchises = listFranchises;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_franchise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListFranchise current = listFranchises.get(position);
        holder.namefranchise.setText(current.getName());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.investments.setText(formatter.format(Double.parseDouble(current.getInvestment())));

        Picasso.with(context)
                .load(current.getBanner())
                .placeholder(R.drawable.logo404)
                .into(holder.banner);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FranchiseDetail.class);
                intent.putExtra(FRANCHISE_ID, current.getId().toString());
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
                intent.putExtra(AVERAGE_RATING, current.getAverageRating());
                intent.putExtra(DETAIL, current.getDetail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFranchises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.itemfranchise_averagerating) ImageView averagerating;
        @BindView(R.id.itemfranchise_banner) ImageView banner;
        @BindView(R.id.itemfranchise_conitemroot) LinearLayout container;
        @BindView(R.id.itemfranchise_invesments) TextView investments;
        @BindView(R.id.itemfranchise_namefranchise) TextView namefranchise;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.example.asus.pikachise.view.home.homenew.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.BrochureList;
import com.example.asus.pikachise.model.BrochureListResponse;
import com.example.asus.pikachise.model.ListFranchise;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Asus on 07/10/2017.
 */

public class NewRVAdapter extends RecyclerView.Adapter<NewRVAdapter.EventHolder> {
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

    private List<ListFranchise> franchiseLists;
    private LayoutInflater inflater;
    private Context context;
    private apiService service;
    private String token;
    public NewRVAdapter(List<ListFranchise> franchiseLists, Context c, String token){
        this.franchiseLists = franchiseLists;
        this.inflater = LayoutInflater.from(c);
        this.token = token;
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_newfranchise, parent, false);
        context = parent.getContext();
        return new EventHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final EventHolder holder, int position) {
        final ListFranchise current = franchiseLists.get(position);
        Picasso.with(context)
                .load(current.getLogo())
                .placeholder(R.drawable.logo404)
                .into(holder.logo);
        holder.namafranchise.setText(current.getName());
        service = apiUtils.getAPIService();
        service.brochurelistRequest(token, current.getId())
                .enqueue(new Callback<BrochureListResponse>() {
                    @Override
                    public void onResponse(Call<BrochureListResponse> call, Response<BrochureListResponse> response) {
                        if(response.body().getBrochureList().toString().equals("[]") || response.code() ==401 || response.code() ==500){
                            Picasso.with(context)
                                    .load(current.getBanner())
                                    .placeholder(R.drawable.logo404)
                                    .into(holder.brocuhre);
                        }
                        else{
                            List<BrochureList> brochureList = response.body().getBrochureList();
                            int length = brochureList.size();
                            Picasso.with(context)
                                    .load(brochureList.get(length-1).getBrochure())
                                    .placeholder(R.drawable.logo404)
                                    .into(holder.brocuhre);
                        }
                    }

                    @Override
                    public void onFailure(Call<BrochureListResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
        Picasso.with(context)
                .load(current.getBanner())
                .placeholder(R.drawable.logo404)
                .into(holder.brocuhre);
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
        return franchiseLists.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.new_container) LinearLayout container;
        @BindView(R.id.itemnewfranchise_namafranchise) TextView namafranchise;
        @BindView(R.id.itemnewfranchise_brochure) ImageView brocuhre;
        @BindView(R.id.itemnewfranchise_logo) ImageView logo;
        public EventHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

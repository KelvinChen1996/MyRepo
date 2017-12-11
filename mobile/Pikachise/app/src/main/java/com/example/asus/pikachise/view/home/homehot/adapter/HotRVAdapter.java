package com.example.asus.pikachise.view.home.homehot.adapter;

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
import com.example.asus.pikachise.model.HotListFranchise;
import com.example.asus.pikachise.model.ListFranchise;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.model.ItemTopFranchise;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.example.asus.pikachise.view.franchisedetail.adapter.BrochuresAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Asus on 18/09/2017.
 */

public class HotRVAdapter extends RecyclerView.Adapter<HotRVAdapter.EventHolder> {
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

    private List<HotListFranchise> hotListFranchises;
    private LayoutInflater inflater;
    private Context context;
    private apiService service;
    private String token;
    public HotRVAdapter(List<HotListFranchise> hotListFranchises, Context c, String token){
        this.hotListFranchises = hotListFranchises;
        this.inflater = LayoutInflater.from(c);
        this.token = token;
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_topfranchise, parent, false);
        context = parent.getContext();
        return new EventHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final EventHolder holder, int position) {
        final HotListFranchise current = hotListFranchises.get(position);

        service = apiUtils.getAPIService();
        service.brochurelistRequest(token, current.getFranchiseId())
                .enqueue(new Callback<BrochureListResponse>() {
                    @Override
                    public void onResponse(Call<BrochureListResponse> call, Response<BrochureListResponse> response) {
                        if(response.body().getBrochureList().toString().equals("[]")){
                            Picasso.with(context)
                                    .load(current.getBanner())
                                    .placeholder(R.drawable.logo404)
                                    .into(holder.brochure);
                        }
                        else{
                            List<BrochureList> brochureList = response.body().getBrochureList();
                            int length = brochureList.size();
                            Picasso.with(context)
                                    .load(brochureList.get(length-1).getBrochure())
                                    .placeholder(R.drawable.logo404)
                                    .into(holder.brochure);
                        }
                    }

                    @Override
                    public void onFailure(Call<BrochureListResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
        Picasso.with(context)
                .load(current.getLogo())
                .placeholder(R.drawable.logo404)
                .into(holder.logo);

        holder.namefranchise.setText(current.getName());
        holder.nfavorite.setText(current.getNumsFavorite().toString());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FranchiseDetail.class);
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
                intent.putExtra(AVERAGE_RATING, current.getAverageRating());
                intent.putExtra(DETAIL, current.getDetail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotListFranchises.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemtopfranchise_brochure) ImageView brochure;
        @BindView(R.id.itemtopfranchise_logo) ImageView logo;
        @BindView(R.id.itemtopfranchise_namafranchise) TextView namefranchise;
        @BindView(R.id.itemtopfranchise_nFavorite) TextView nfavorite;
        @BindView(R.id.itemtopfranchise_container) LinearLayout container;
        public EventHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

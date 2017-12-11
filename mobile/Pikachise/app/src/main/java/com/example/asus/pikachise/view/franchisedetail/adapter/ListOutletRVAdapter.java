package com.example.asus.pikachise.view.franchisedetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Outlet;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Asus on 20/09/2017.
 */

public class ListOutletRVAdapter extends RecyclerView.Adapter<ListOutletRVAdapter.EventHolder> {
    private List<Outlet> outletList;
    private LayoutInflater inflater;
    private String logo, franchisename;
    Context context;
    public ListOutletRVAdapter(List<Outlet> outletList, Context c, String logo, String franchisename){
        this.inflater = LayoutInflater.from(c);
        this.outletList = outletList;
        this.logo = logo;
        this.franchisename = franchisename;
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_outlet_list, parent, false);
        context = parent.getContext();
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
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
        Outlet daftarOutlet = outletList.get(position);
        String fullAlamat = daftarOutlet.getAddress();
        String[] pisah = fullAlamat.split("%");
        String alamat = pisah[0];
        String longitude = pisah[2];
        String latitude = pisah[1];
        holder.franchiseename.setText(franchisename);
//        holder.address.setText(daftarOutlet.getAddress());
        holder.address.setText(alamat);
//        holder.phonenumber.setText(daftarOutlet.getTelp());
//        holder.owner.setText(daftarOutlet.getName());
        holder.phonenumber.setText(longitude);
        holder.owner.setText(latitude);
        holder.since.setText(daftarOutlet.getDateJoin());
        Picasso.with(context)
                .load(logo)
                .placeholder(R.drawable.logo404)
                .into(holder.logo);


    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.homedetailfranchisedaftaroutlet_franchiseename) TextView franchiseename;
        @BindView(R.id.homedetailfranchisedaftaroutlet_layout) LinearLayout layout;
        @BindView(R.id.homedetailfranchisedaftaroutlet_logo) ImageView logo;
        @BindView(R.id.homedetailfranchisedaftaroutlet_owner) TextView owner;
        @BindView(R.id.homedetailfranchisedaftaroutlet_phonenumber) TextView phonenumber;
        @BindView(R.id.homedetailfranchisedaftaroutlet_since) TextView since;
        @BindView(R.id.homedetailfranchisedaftaroutlet_address) TextView address;
        public EventHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

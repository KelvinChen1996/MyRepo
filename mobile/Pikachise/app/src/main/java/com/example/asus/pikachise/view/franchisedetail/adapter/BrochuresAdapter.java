package com.example.asus.pikachise.view.franchisedetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.BrochureList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by William Sumitro on 19/10/2017.
 */

public class BrochuresAdapter extends RecyclerView.Adapter<BrochuresAdapter.EventHolder> {
    private List<BrochureList> brochureList;
    private LayoutInflater inflater;
    Context context;
    public BrochuresAdapter(List<BrochureList> brochureList, Context c){
        this.brochureList = brochureList;
        this.inflater = LayoutInflater.from(c);
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_brochure, parent, false);
        context = parent.getContext();
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final BrochureList current = brochureList.get(position);
        Picasso.with(context)
                .load(current.getBrochure())
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return brochureList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public EventHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.itembrochure_imageview);
        }
    }
}

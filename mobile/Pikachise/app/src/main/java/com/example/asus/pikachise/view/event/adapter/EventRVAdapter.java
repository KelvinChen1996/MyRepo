package com.example.asus.pikachise.view.event.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Event;
import com.example.asus.pikachise.view.event.activity.DetailEvent;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by william on 25/05/2017.
 */

public class EventRVAdapter extends RecyclerView.Adapter<EventRVAdapter.EventHolder> {

    private final static String NAME = "NAME";
    private final static String ID = "ID";
    private final static String IMAGE = "IMAGE";
    private final static String FRANCHISE_ID = "FRANCHISE_ID";
    private final static String TIME = "TIME";
    private final static String DATE = "DATE";
    private final static String PRICE = "PRICE";
    private final static String DETAIL = "DETAIL";
    private final static String VENUE = "VENUE";

    private List<Event> listevent;
    private LayoutInflater inflater;
    Context context;
    private String token;

    public EventRVAdapter(List<Event> listevent, Context c, String token){
        this.inflater = LayoutInflater.from(c);
        this.listevent = listevent;
        this.token = token;
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_events, parent, false);
        context = parent.getContext();
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final Event current = listevent.get(position);
        holder.judulevent.setText(current.getName());
        holder.alamatevent.setText(current.getVenue());
        holder.tanggalevent.setText(current.getDate());
        holder.waktuevent.setText(current.getTime());
        holder.price.setText(current.getPrice());
        holder.description.setText(current.getDetail());
        Picasso.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.logo404)
                .into(holder.gambarevent);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailEvent.class);

                intent.putExtra(ID, current.getId());
                intent.putExtra(FRANCHISE_ID, current.getFranchiseId());
                intent.putExtra(NAME, current.getName());
                intent.putExtra(DATE, current.getDate());
                intent.putExtra(TIME, current.getTime());
                intent.putExtra(VENUE, current.getVenue());
                intent.putExtra(DETAIL, current.getDetail());
                intent.putExtra(IMAGE, current.getImage());
                intent.putExtra(PRICE, current.getPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listevent.size();
    }
    public class EventHolder extends RecyclerView.ViewHolder {
        private TextView judulevent, alamatevent, tanggalevent, waktuevent, price,description;
        private ImageView gambarevent;
        private View container;

        public EventHolder(View itemView) {
            super(itemView);

            judulevent = (TextView) itemView.findViewById(R.id.event_judul);
            alamatevent = (TextView) itemView.findViewById(R.id.event_alamat);
            tanggalevent = (TextView) itemView.findViewById(R.id.event_tanggal);
            gambarevent = (ImageView) itemView.findViewById(R.id.event_Gambar);
            waktuevent= (TextView) itemView.findViewById(R.id.event_time);
            price = (TextView) itemView.findViewById(R.id.event_price);
            description = (TextView) itemView.findViewById(R.id.event_description);
            container = itemView.findViewById(R.id.cv_items);
        }
    }
}


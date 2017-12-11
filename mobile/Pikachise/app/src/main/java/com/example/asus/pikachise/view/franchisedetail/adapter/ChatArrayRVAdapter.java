package com.example.asus.pikachise.view.franchisedetail.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.ReviewRating;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asus on 20/09/2017.
 */

public class ChatArrayRVAdapter extends RecyclerView.Adapter<ChatArrayRVAdapter.EventHolder> {


    List<ReviewRating> reviewRatings;
    private LayoutInflater inflater;
    Context context;


    public ChatArrayRVAdapter(List<ReviewRating> reviewRatings, Context c){
        this.reviewRatings = reviewRatings;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public ChatArrayRVAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat, parent, false);
        context = parent.getContext();
        return new ChatArrayRVAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final ReviewRating current = reviewRatings.get(position);
        Picasso.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.logo404)
                .into(holder.image);
        Double rating;
        if(current.getRating() == null){
            rating = 0.0;
        } else{
            rating = Double.valueOf(current.getRating());
        }
        if(rating==0.0){
            Picasso.with(context)
                    .load(R.drawable.star0)
                    .into(holder.averagerating);
        }else if(rating==1.0){
            Picasso.with(context)
                    .load(R.drawable.star1)
                    .into(holder.averagerating);
        }else if(rating==2.0){
            Picasso.with(context)
                    .load(R.drawable.star2)
                    .into(holder.averagerating);
        }else if(rating==3.0){
            Picasso.with(context)
                    .load(R.drawable.star3)
                    .into(holder.averagerating);
        }else if(rating==4.0){
            Picasso.with(context)
                    .load(R.drawable.star4)
                    .into(holder.averagerating);
        }else if(rating==5.0){
            Picasso.with(context)
                    .load(R.drawable.star5)
                    .into(holder.averagerating);
        }else if(rating>0.0 && rating<1.0){
            Picasso.with(context)
                    .load(R.drawable.star0_1)
                    .into(holder.averagerating);
        }else if(rating>1.0 && rating<2.0){
            Picasso.with(context)
                    .load(R.drawable.star1_2)
                    .into(holder.averagerating);
        }else if(rating>2.0 && rating<3.0){
            Picasso.with(context)
                    .load(R.drawable.star2_3)
                    .into(holder.averagerating);
        }else if(rating>3.0 && rating<4.0){
            Picasso.with(context)
                    .load(R.drawable.star3_4)
                    .into(holder.averagerating);
        }else if(rating>4.0 && rating<5.0){
            Picasso.with(context)
                    .load(R.drawable.star4_5)
                    .into(holder.averagerating);
        }
        holder.comment.setText(current.getReview());
        holder.nameuser.setText(current.getName());


        String date = current.getCreatedAt();
        String[] pecah = date.split(" ");
        String waktu = pecah[1];
        String tanggal = pecah[0];
        long waktusekarang = System.currentTimeMillis();
//        SimpleDateFormat sdf = null;
        SimpleDateFormat sdf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        Calendar c = Calendar.getInstance();

//        Date start_date = c.getTime();
//        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//        Date date = format.parse(string);
//        Date end_date = format.;
//        long total_time = end_date.getTime() - start_date.getTime();
//        Log.i("Total TIme -> ", String.valueOf(total_time)+ " " + start_date.toString()+ " " +end_date.toString());

        Date dt = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dt = sdf.parse(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dt != null){
            c.setTime(dt);
        }
        else {
            Toast.makeText(context,c.toString(),Toast.LENGTH_SHORT).show();
        }

        long kurang = waktusekarang - c.getTimeInMillis();
        String waktutampil = "";
        if(kurang <60000){
            long seconds = TimeUnit.MILLISECONDS.toSeconds(kurang);
            waktutampil = String.valueOf(seconds) + " seconds ago";
            //detik
        }
        else if(kurang>=60000 && kurang<3600000){
            long minutes = TimeUnit.MILLISECONDS.toMinutes(kurang);
            waktutampil = String.valueOf(minutes) + " minutes ago";
            //menit
        }else if(kurang>=3600000 && kurang<86400000){
            long hours = TimeUnit.MILLISECONDS.toHours(kurang);
            waktutampil = String.valueOf(hours) + " hours ago";
            //jam
        }else{
            long days = TimeUnit.MILLISECONDS.toDays(kurang);
            waktutampil = String.valueOf(days) + " days ago";
            //hari
        }
        holder.time.setText(waktutampil);
    }

    @Override
    public int getItemCount() {
        return reviewRatings.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.chat_rate) ImageView averagerating;
        @BindView(R.id.chat_comment) TextView comment;
        @BindView(R.id.chat_image) CircleImageView image;
        @BindView(R.id.chat_namauser) TextView nameuser;
        @BindView(R.id.chat_time) TextView time;
        public EventHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

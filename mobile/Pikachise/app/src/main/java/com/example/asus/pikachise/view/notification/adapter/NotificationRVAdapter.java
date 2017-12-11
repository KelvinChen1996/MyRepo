package com.example.asus.pikachise.view.notification.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.Notification;
import com.example.asus.pikachise.model.NotificationResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by WilliamSumitro on 01/10/2017.
 */

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.ViewHolder> {
    @BindView(R.id.usermain_navigationview) NavigationView navigationView;
    TextView notif;

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
    private List<Notification> notificationList;
    String token;
    apiService service;

    public NotificationRVAdapter(List<Notification> notifications, Context context,String token){
        this.notificationList = notifications;
        this.context =context;
        this.token = token;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);

//        notif = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.usernavitem_notification));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Notification notification = notificationList.get(position);
        service = apiUtils.getAPIService();

        Picasso.with(context)
                .load(notification.getBanner())
                .placeholder(R.drawable.logo404)
                .into(holder.imagefranchise);


        String date =notification.getNotificationCreatedAt();
        long waktusekarang = System.currentTimeMillis();

        SimpleDateFormat sdf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Calendar c = Calendar.getInstance();

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

        holder.waktu.setText(waktutampil);
        holder.statusFranchise.setText("update new status");
        holder.namafranchise.setText(notification.getName());
        holder.container.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service.Read_Notification(notification.getFranchiseId(),token)
                                .enqueue(new Callback<NotificationResponse>() {
                                    @Override
                                    public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                                        Intent intent = new Intent(context, FranchiseDetail.class);
                                        intent.putExtra(FRANCHISE_ID, notification.getFranchiseId());
                                        intent.putExtra(NAME, notification.getName());
                                        intent.putExtra(BANNER, notification.getBanner());
                                        intent.putExtra(LOGO, notification.getLogo());
                                        intent.putExtra(CATEGORY, notification.getCategory());
                                        intent.putExtra(TYPE, notification.getType());
                                        intent.putExtra(ESTABLISHSINCE, notification.getEstablishSince());
                                        intent.putExtra(INVESTMENT, notification.getInvestment());
                                        intent.putExtra(FRANCHISEFEE, notification.getFranchiseFee());
                                        intent.putExtra(WEBSITE, notification.getWebsite());
                                        intent.putExtra(ADDRESS, notification.getAddress());
                                        intent.putExtra(LOCATION, notification.getLocation());
                                        intent.putExtra(PHONENUMBER, notification.getPhoneNumber());
                                        intent.putExtra(EMAIL, notification.getEmail());
                                        intent.putExtra(AVERAGE_RATING, String.valueOf(notification.getAverageRating()));
                                        intent.putExtra(DETAIL, notification.getDetail());
                                        context.startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<NotificationResponse> call, Throwable t) {
                                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }

        );


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemnotification_container) LinearLayout container;
        @BindView(R.id.itemnotification_namafranchise) TextView namafranchise;
        @BindView(R.id.itemnotification_waktu) TextView waktu;
        @BindView(R.id.itemnotification_status) TextView statusFranchise;
        @BindView(R.id.itemnotification_imageview) ImageView imagefranchise;
        @BindView(R.id.itemnotification_background) RelativeLayout background;
        @BindView(R.id.itemnotification_foreground) RelativeLayout foreground;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void removeItem(int position){
        notificationList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Notification notification, int position){
        notificationList.add(position, notification);
        notifyItemInserted(position);
    }
}
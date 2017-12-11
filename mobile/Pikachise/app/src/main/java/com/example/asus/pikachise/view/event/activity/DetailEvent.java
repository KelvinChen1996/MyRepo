package com.example.asus.pikachise.view.event.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailEvent extends AppCompatActivity {

    @BindView(R.id.detailEvent_Alamat) TextView alamat;
    @BindView(R.id.detailEvent_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.detailEvent_collaptoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailEvent_Gambar) ImageView gambar;
    @BindView(R.id.detailEvent_Isi) TextView isi;
    @BindView(R.id.detailEvent_Judul) TextView judul;
    @BindView(R.id.detailEvent_Tanggal) TextView tanggal;
    @BindView(R.id.detailEvent_toolbar) Toolbar toolbar;
    @BindView(R.id.detailEvent_Waktu) TextView waktu;

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_JUDUL = "EXTRA_JUDUL";
    private static final String EXTRA_ISI = "EXTRA_ISI";
    private static final String EXTRA_JADWAL = "EXTRA_JADWAL";
    private static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";
    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";

    private final static String NAME = "NAME";
    private final static String ID = "ID";
    private final static String IMAGE = "IMAGE";
    private final static String FRANCHISE_ID = "FRANCHISE_ID";
    private final static String TIME = "TIME";
    private final static String DATE = "DATE";
    private final static String PRICE = "PRICE";
    private final static String DETAIL = "DETAIL";
    private final static String VENUE = "VENUE";

    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);


        initObject();
        Intent getintent = getIntent();
        judul.setText(getintent.getExtras().getString(NAME));
        isi.setText(getintent.getExtras().getString(DETAIL));
        tanggal.setText(getintent.getExtras().getString(DATE));
        alamat.setText(getintent.getExtras().getString(VENUE));
        waktu.setText(getintent.getExtras().getString(TIME));
//        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);
//        judul.setText(extras.getString(EXTRA_JUDUL));
//        isi.setText(extras.getString(EXTRA_ISI));
//        tanggal.setText(extras.getString(EXTRA_JADWAL));
//        alamat.setText(extras.getString(EXTRA_ALAMAT));

        setupToolbar();
        initCollapToolbar();

        Picasso.with(this.getBaseContext()).load(apiUtils.BASE_IMAGE+getintent.getExtras().getString(IMAGE)).into(gambar);
    }
    private void initObject() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle(judul.toString());
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}

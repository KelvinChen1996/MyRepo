package com.example.asus.pikachise.view.myfranchise.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMyFranchise extends AppCompatActivity {

    public static Activity detailmyfranchiseActivity;
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
    private final static String ACTIVITY = "ACTIVITY";

    @BindView(R.id.detailmyfranchise_backgroundimage) ImageView background;
    @BindView(R.id.detailmyfranchise_brochure) LinearLayout brochure;
    @BindView(R.id.detailmyfranchise_edit) LinearLayout edit;
    @BindView(R.id.detailmyfranchise_franchisee) LinearLayout franchisee;
    @BindView(R.id.detailmyfranchise_logo) ImageView logo;
    @BindView(R.id.detailmyfranchise_namefranchise) TextView name;
    @BindView(R.id.detailmyfranchise_toolbar) Toolbar toolbar;
    @BindView(R.id.detailmyfranchise_event) LinearLayout event;
    @BindView(R.id.detailmyfranchise_legaldoc) LinearLayout legaldoc;


    Context context;
    apiService service;
    SessionManagement session;
    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid, extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee, extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail;

    String gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_franchise);
        initObject();
        getactivityIntent();
        setupToolbar();
        initData();
        initClick();
    }
    private void initObject(){
        detailmyfranchiseActivity = this;
        ButterKnife.bind(this);
        context = this;
        session = new SessionManagement(getApplicationContext());
        service = apiUtils.getAPIService();
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void initClick(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMyFranchiseEdit.class);
                initSendData(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright, R.anim.fadeout);
            }
        });
        brochure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMyFranchiseBrochure.class);
                initSendData(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright, R.anim.fadeout);
            }
        });
        franchisee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMyFranchiseFranchisee.class);
                initSendData(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright, R.anim.fadeout);

            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMyFranchiseEvent.class);
                initSendData(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright, R.anim.fadeout);
            }
        });
        legaldoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LegalDoc.class);
                initSendData(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright, R.anim.fadeout);
            }
        });
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(extraname);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void getactivityIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(FRANCHISEFEE)){
            extraname = getintent.getExtras().getString(NAME);
            extralogo = getintent.getExtras().getString(LOGO);
            extrabanner = getintent.getExtras().getString(BANNER);
            extraemail = getintent.getExtras().getString(EMAIL);
            extrauserid = getintent.getExtras().getString(USER_ID);
            extrafranchiseid = getintent.getExtras().getString(FRANCHISE_ID);
            extracategory = getintent.getExtras().getString(CATEGORY);
            extratype = getintent.getExtras().getString(TYPE);
            extraestablishsince = getintent.getExtras().getString(ESTABLISHSINCE);
            extrainvesment = getintent.getExtras().getString(INVESTMENT);
            extrafranchisefee = getintent.getExtras().getString(FRANCHISEFEE);
            extrawebsite = getintent.getExtras().getString(WEBSITE);
            extraaddress = getintent.getExtras().getString(ADDRESS);
            extralocation = getintent.getExtras().getString(LOCATION);
            extraphonenumber = getintent.getExtras().getString(PHONENUMBER);
            extradetail = getintent.getExtras().getString(DETAIL);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void initData(){
        name.setText(extraname);
        Picasso.with(context)
                .load(extrabanner)
                .placeholder(R.drawable.logo404)
                .into(background);
        Picasso.with(context)
                .load(extralogo)
                .placeholder(R.drawable.logo404)
                .into(logo);
    }
    private void initSendData(Intent intent){
        intent.putExtra(ACTIVITY, "notpending");
        intent.putExtra(USER_ID, extrauserid);
        intent.putExtra(FRANCHISE_ID, extrafranchiseid);
        intent.putExtra(NAME, extraname);
        intent.putExtra(BANNER, extrabanner);
        intent.putExtra(LOGO, extralogo);
        intent.putExtra(CATEGORY, extracategory);
        intent.putExtra(TYPE, extratype);
        intent.putExtra(ESTABLISHSINCE, extraestablishsince);
        intent.putExtra(INVESTMENT, extrainvesment);
        intent.putExtra(FRANCHISEFEE, extrafranchisefee);
        intent.putExtra(WEBSITE, extrawebsite);
        intent.putExtra(ADDRESS, extraaddress);
        intent.putExtra(LOCATION, extralocation);
        intent.putExtra(PHONENUMBER, extraphonenumber);
        intent.putExtra(EMAIL, extraemail);
        intent.putExtra(DETAIL, extradetail);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbarupdate_home) {
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
            overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
            MyFranchiseActivity.myfranchiseActivity.finish();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

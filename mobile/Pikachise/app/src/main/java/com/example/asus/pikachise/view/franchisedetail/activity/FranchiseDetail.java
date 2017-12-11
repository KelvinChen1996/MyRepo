package com.example.asus.pikachise.view.franchisedetail.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.MyFranchiseResponse;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.franchisedetail.adapter.DetailFranchiseVPAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FranchiseDetail extends AppCompatActivity {
    private String extrafranchiseid;
    private String extraname, extralogo, extrabanner, extraemail, extrauserid,
            extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee,
            extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail, extraaveragerating;
    private static int REQCODE_PHONE = 88;

    @BindView(R.id.detailfranchise_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.detailfranchise_collaptoolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailfranchise_banner)
    ImageView banner;
    @BindView(R.id.detailfranchise_favorited)
    ImageView favorite;
    @BindView(R.id.detailfranchise_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.detailfranchise_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detailfranchise_viewpager)
    ViewPager viewPager;
    @BindView(R.id.detailfranchise_logo)
    CircleImageView logo;
    @BindView(R.id.detailfranchise_mail)
    ImageView mail;
    @BindView(R.id.detailfranchise_call)
    ImageView call;
    @BindView(R.id.detailfranchise_namefranchise)
    TextView namefranchise;
    @BindView(R.id.detailfranchise_averagerating)
    ImageView averagerating;
    private boolean isFavorited = false;
    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;
    Double rating;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_franchise);
        initObject();
        initGetIntent();
        setupToolbar();
        initCollapToolbar();
        initData();
        setupViewPager();
        initOnClick();
        checkFavorite();

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
    private void initOnClick() {
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( FranchiseDetail.this, new String[] {  Manifest.permission.CALL_PHONE  }, REQCODE_PHONE );
                    Toast.makeText(context, "All Set, Please click one more time", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent callintent = new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:" + extraphonenumber));
                startActivity(callintent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Intent> emailAppLauncherIntents = new ArrayList<>();

                //Intent that only email apps can handle:
                Intent emailAppIntent = new Intent(Intent.ACTION_SENDTO);
                emailAppIntent.setData(Uri.parse("mailto:"));
                emailAppIntent.putExtra(Intent.EXTRA_EMAIL, "");
                emailAppIntent.putExtra(Intent.EXTRA_SUBJECT, "");

                PackageManager packageManager = getPackageManager();

                //All installed apps that can handle email intent:
                List<ResolveInfo> emailApps = packageManager.queryIntentActivities(emailAppIntent, PackageManager.MATCH_ALL);

                for (ResolveInfo resolveInfo : emailApps) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
                    emailAppLauncherIntents.add(launchIntent);
                }

                //Create chooser
                Intent chooserIntent = Intent.createChooser(new Intent(), "Select email app:");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, emailAppLauncherIntents.toArray(new Parcelable[emailAppLauncherIntents.size()]));
                startActivity(chooserIntent);

            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorited)
                {
                    favorite.setImageResource(R.drawable.unfavorite);
                    isFavorited = false;
                    API_unfavorite();

                }
                else{
                    favorite.setImageResource(R.drawable.favorite);
                    isFavorited = true;
                    API_favorite();

                }
            }
        });
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(FranchiseDetail.this, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle(extraname);
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void setupViewPager(){
        Bundle bundle = new Bundle();
        bundle.putString(FRANCHISE_ID, String.valueOf(extrafranchiseid));
        bundle.putString(NAME, extraname);
        bundle.putString(BANNER, extrabanner);
        bundle.putString(LOGO, extralogo);
        bundle.putString(CATEGORY, extracategory);
        bundle.putString(TYPE, extratype);
        bundle.putString(ESTABLISHSINCE, extraestablishsince);
        bundle.putString(INVESTMENT, extrainvesment);
        bundle.putString(FRANCHISEFEE, extrafranchisefee);
        bundle.putString(WEBSITE, extrawebsite);
        bundle.putString(ADDRESS, extraaddress);
        bundle.putString(LOCATION, extralocation);
        bundle.putString(PHONENUMBER, extraphonenumber);
        bundle.putString(EMAIL, extraemail);
        bundle.putString(DETAIL, extradetail);
        viewPager.setAdapter(new DetailFranchiseVPAdapter(getSupportFragmentManager(), bundle));
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(FRANCHISEFEE)){
            extrafranchiseid = getintent.getExtras().getString(FRANCHISE_ID);
            extraname = getintent.getExtras().getString(NAME);
            extralogo = getintent.getExtras().getString(LOGO);
            extrabanner = getintent.getExtras().getString(BANNER);
            extraemail = getintent.getExtras().getString(EMAIL);
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
            extraaveragerating = getintent.getExtras().getString(AVERAGE_RATING);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void initData(){
        namefranchise.setText(extraname);
        Picasso.with(context)
                .load(extrabanner)
                .placeholder(R.drawable.logo404)
                .into(banner);
        Picasso.with(context)
                .load(extralogo)
                .placeholder(R.drawable.logo404)
                .into(logo);
        if(extraaveragerating == null){
            rating = 0.0;
        } else{
            rating = Double.valueOf(extraaveragerating);
        }
        if(rating==0.0){
            Picasso.with(context)
                    .load(R.drawable.star0)
                    .into(averagerating);
        }else if(rating==1.0){
            Picasso.with(context)
                    .load(R.drawable.star1)
                    .into(averagerating);
        }else if(rating==2.0){
            Picasso.with(context)
                    .load(R.drawable.star2)
                    .into(averagerating);
        }else if(rating==3.0){
            Picasso.with(context)
                    .load(R.drawable.star3)
                    .into(averagerating);
        }else if(rating==4.0){
            Picasso.with(context)
                    .load(R.drawable.star4)
                    .into(averagerating);
        }else if(rating==5.0){
            Picasso.with(context)
                    .load(R.drawable.star5)
                    .into(averagerating);
        }else if(rating>0.0 && rating<1.0){
            Picasso.with(context)
                    .load(R.drawable.star0_1)
                    .into(averagerating);
        }else if(rating>1.0 && rating<2.0){
            Picasso.with(context)
                    .load(R.drawable.star1_2)
                    .into(averagerating);
        }else if(rating>2.0 && rating<3.0){
            Picasso.with(context)
                    .load(R.drawable.star2_3)
                    .into(averagerating);
        }else if(rating>3.0 && rating<4.0){
            Picasso.with(context)
                    .load(R.drawable.star3_4)
                    .into(averagerating);
        }else if(rating>4.0 && rating<5.0){
            Picasso.with(context)
                    .load(R.drawable.star4_5)
                    .into(averagerating);
        }
    }

    private void checkFavorite() {
        service.Get_FavouritStatus(extrafranchiseid,token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            startActivity(new Intent(context, Error401.class));
                        }
                        else{
                            try {
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                Log.i("jsonResult", "onjson > " + jsonResults.toString());
                                Boolean status = jsonResults.getBoolean("favorite");
                                if(status){
                                    favorite.setImageResource(R.drawable.favorite);
                                    isFavorited = true;
                                    Log.i("debug", "onSuccessGetStatusFavorite > ");

                                }
                                else {
                                    favorite.setImageResource(R.drawable.unfavorite);
                                    isFavorited = false;
                                    Log.i("debug", "onSuccessGetStatusUnfavorite");
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void API_unfavorite() {
        service.UnFavourite(extrafranchiseid,token)
                .enqueue(new Callback<MyFranchiseResponse>() {
                    @Override
                    public void onResponse(Call<MyFranchiseResponse> call, Response<MyFranchiseResponse> response) {
                        if(response.code() == 401 || response.code() ==500){
                            startActivity(new Intent(context, Error401.class));
                        }
                        else{
                            Log.e("debug", "onSUCCES: SUCCESS UNFAVORITE > ");
                        }
                    }

                    @Override
                    public void onFailure(Call<MyFranchiseResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR UNFAVORITE> " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void API_favorite() {
        service.Favourite(extrafranchiseid,token)
                .enqueue(new Callback<MyFranchiseResponse>() {
                    @Override
                    public void onResponse(Call<MyFranchiseResponse> call, Response<MyFranchiseResponse> response) {
                        if(response.code() == 401){
                            startActivity(new Intent(context, Error401.class));
                        }
                        else{
                            Log.e("debug", "onSUCCES: SUCCESS FAVORITE > ");
                        }
                    }

                    @Override
                    public void onFailure(Call<MyFranchiseResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR FAVORITE > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}

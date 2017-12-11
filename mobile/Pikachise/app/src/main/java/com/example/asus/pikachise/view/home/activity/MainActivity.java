package com.example.asus.pikachise.view.home.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.ChatMessage;
import com.example.asus.pikachise.model.Event;
import com.example.asus.pikachise.model.Franchise;
import com.example.asus.pikachise.model.User;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.EditUser;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.EditUser;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.example.asus.pikachise.view.franchiselist.fragment.FranchiselistFragment;
import com.example.asus.pikachise.view.franchiselist.listall.AllCategory;
import com.example.asus.pikachise.view.home.fragment.UserHomeFragment;
import com.example.asus.pikachise.view.interestedlist.fragment.Interested_List;
import com.example.asus.pikachise.view.myfranchise.activity.MyFranchiseActivity;
import com.example.asus.pikachise.view.notification.fragment.NotificationFragment;
import com.example.asus.pikachise.view.search.activity.Search;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    TextView notification;

    @BindView(R.id.usermain_collapsingtoolbarlayout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.usermain_drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.usermain_navigationview) NavigationView navigationView;
    @BindView(R.id.usermain_toolbar) Toolbar toolbar;
    @BindView(R.id.usermain_slider) SliderLayout sliderLayout;
    @BindView(R.id.usermain_appbarlayout) AppBarLayout appBarLayout;
    private MenuItem activeMenuItem;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageView imageView;
    private int CurrentSelectedPosition;
    private HashMap<String, Integer> hashfilemaps;
    private NestedScrollView nestedScrollView;
    private boolean doubleBackToExitPressedOnce = false;
    private String baseimageurl;
    private ProgressDialog progressDialog;
    private static final String TOKEN = "TOKEN";
    private final static String INTENT_CATEGORY = "INTENT_CATEGORY";
    String tokens;
    apiService service;
    Context context;
    User user;
    SessionManagement session;

    private String token, email, image, name ,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initObject();
        initActivityTransition();
        initDataSession();
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();

        checkDataAPI();
        initgetUser();
        initNav();
        initCollapToolbar();
        initImageToolbar();
        GetNotificationCount();
//        GET_Notif_ALERT();

    }

    private void GET_Notif_ALERT() {
        String menuFragment = getIntent().getStringExtra("menuFragment");

        CurrentSelectedPosition = 4;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // If menuFragment is defined, then this activity was launched with a fragment selection
        if (menuFragment != null) {
            disableCollapse();
            toolbar.setTitle("Notification");
            disableNotif();

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (menuFragment.equals("favoritesMenuItem")) {
                fragmentTransaction.replace(R.id.usermain_containerview, new NotificationFragment()).commit();

            }
        }


    }




    public void GetNotificationCount() {
        service.Get_Notification_Count(token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            startActivity(new Intent(context, Error401.class));
                        }
                        else{
                            try {
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                Integer integer = jsonResults.getInt("notifications_count");


                                Log.i("jsonResult", "Notification-> " + jsonResults.toString());


                                if(integer>0){
                                    AlertSoundNotification(integer.toString());
                                    notification = (TextView) MenuItemCompat.
                                            getActionView(navigationView.getMenu().
                                                    findItem(R.id.usernavitem_notification));
                                    notification.setVisibility(View.VISIBLE);
                                    notification.setGravity(Gravity.CENTER_VERTICAL);
                                    notification.setTypeface(null, Typeface.BOLD);
                                    notification.setTextColor(getResources().getColor(R.color.colorDot));
                                    notification.setText(integer.toString());
                                    notification.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
                        Toast.makeText(context, "There is a problem with connection", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void disableNotif() {
        notification = (TextView) MenuItemCompat.
                getActionView(navigationView.getMenu().
                        findItem(R.id.usernavitem_notification));
        notification.setVisibility(View.INVISIBLE);

    }
    private void AlertSoundNotification(String count) {
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final NotificationCompat.Builder builder  = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_notifications);
        builder.setContentTitle("Pikachise Info");
        String text = "You have "+count+" unread notification";
        builder.setStyle(new NotificationCompat.BigTextStyle(builder).bigText(text));
        builder.setSound(defaultSoundUri);
        builder.setContentText(text);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("menuFragment", "favoritesMenuItem");
        GET_Notif_ALERT();

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);


        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(0,notification);
    }




    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        progressDialog = new ProgressDialog(context);
        session = new SessionManagement(getApplicationContext());
        service = apiUtils.getAPIService();
    }
    private void initDataSession(){
        supportPostponeEnterTransition();
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }
    private void initFragmentManager(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.usermain_containerview, new UserHomeFragment()).commit();
    }
    private void initgetUser(){
        service.userRequest(token).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            startActivity(new Intent(context, Error401.class));
                            finish();
                        }
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                email = jsonResults.getJSONObject("result").getString("email");
                                name = jsonResults.getJSONObject("result").getString("name");
                                image = jsonResults.getJSONObject("result").getString("image");
                                View header = navigationView.getHeaderView(0);
                                TextView tvEmail = (TextView) header.findViewById(R.id.userheader_email);
                                TextView tvName = (TextView) header.findViewById(R.id.userheader_name);
                                CircleImageView imageView = (CircleImageView) header.findViewById(R.id.userheader_image);
                                baseimageurl = apiUtils.getUrlImage();
                                Picasso.with(getApplicationContext()).load(baseimageurl+image).placeholder(R.drawable.user).error(R.drawable.rect_error).into(imageView);
                                tvEmail.setText(email);
                                tvName.setText(name);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
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

    private void initNav(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(activeMenuItem != null){
                    activeMenuItem.setChecked(false);
                }
                activeMenuItem = item;
                drawerLayout.closeDrawers();
                item.setChecked(true);
                switch (item.getItemId()){
                    case R.id.usernavitem_myprofile:
                        Intent intent0 = new Intent(MainActivity.this, EditUser.class);
                        intent0.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent0);
                        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.usernavitem_myfranchise:
                        CurrentSelectedPosition = 2;
                        Intent intent1 = new Intent(MainActivity.this, MyFranchiseActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.usernavitem_home:
                        enableCollapse();
                        CurrentSelectedPosition = 2;
                        supportPostponeEnterTransition();
                        initActivityTransition();
                        initFragmentManager();
                        break;
                    case R.id.usernavitem_notification:
                        disableCollapse();
                        toolbar.setTitle("Notification");
                        disableNotif();
                        CurrentSelectedPosition = 4;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.usermain_containerview, new NotificationFragment()).commit();

                        break;
                    case R.id.usernavitem_interestedlist:
                        disableCollapse();
                        toolbar.setTitle("Interested List");
                        CurrentSelectedPosition = 5;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.usermain_containerview, new Interested_List()).commit();
                        break;
                    case R.id.usernavitem_search:
                        CurrentSelectedPosition = 2;
                        Intent intent = new Intent(MainActivity.this, Search.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
                        finish();
                        break;
                    case R.id.usernavitem_guides:
                        disableCollapse();
                        toolbar.setTitle("Guides");
                        CurrentSelectedPosition = 9;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.usermain_containerview, new UserHomeFragment()).commit();
                        break;
                    case R.id.usernavitem_franchiselist:
                        disableCollapse();
                        toolbar.setTitle("Franchise List");
                        CurrentSelectedPosition = 7;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.usermain_containerview, new FranchiselistFragment()).commit();
                        break;
                    case R.id.usernavitem_booked:
                        disableCollapse();
                        toolbar.setTitle("Booked Event");
                        CurrentSelectedPosition = 8;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.usermain_containerview, new FranchiselistFragment()).commit();
                        break;

                    case R.id.usernavitem_logout:
                        API_LOGOUT();
                        break;

                    default:
                        break;

                }
                return false;
            }

        });
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }



    private void initActivityTransition(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }
    private void initImageToolbar(){
        hashfilemaps = new HashMap<String, Integer>();
        hashfilemaps.put("Food",R.drawable.food_banner);
        hashfilemaps.put("Cleaning",R.drawable.cleaning_banner);
        hashfilemaps.put("Hotel",R.drawable.hotel_banner);
        hashfilemaps.put("Groceries",R.drawable.retail_banner);
        hashfilemaps.put("Travel",R.drawable.travel_banner);
        for(final String name : hashfilemaps.keySet()){
            final TextSliderView textSliderView = new TextSliderView(MainActivity.this);
            textSliderView
//                    .description(name)
                    .image(hashfilemaps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra",name);
                            if(name.toLowerCase().equals("food")){
                                Intent i = new Intent(context, AllCategory.class);
                                i.putExtra(INTENT_CATEGORY, "FOOD");
                                context.startActivity(i);
                            }
                            else if(name.toLowerCase().equals("cleaning")){
                                Intent i = new Intent(context, AllCategory.class);
                                i.putExtra(INTENT_CATEGORY, "CLEANING");
                                context.startActivity(i);

                            }
                            else if(name.toLowerCase().equals("hotel")){
                                Intent i = new Intent(context, AllCategory.class);
                                i.putExtra(INTENT_CATEGORY, "HOTEL");
                                context.startActivity(i);
                            }
                            else if(name.toLowerCase().equals("groceries")){
                                Intent i = new Intent(context, AllCategory.class);
                                i.putExtra(INTENT_CATEGORY, "RETAIL");
                                context.startActivity(i);

                            }
                            else if(name.toLowerCase().equals("travel")){
                                Intent i = new Intent(context, AllCategory.class);
                                i.putExtra(INTENT_CATEGORY, "TRAVEL");
                                context.startActivity(i);

                            }
                        }
                    });
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(5000);
        sliderLayout.addOnPageChangeListener(this);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
    }
    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo","Page Changed : " +position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {}
    private void disableCollapse(){
        appBarLayout.setActivated(false);
        collapsingToolbarLayout.setTitleEnabled(false);
        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        p.setScrollFlags(0);
        collapsingToolbarLayout.setLayoutParams(p);
        collapsingToolbarLayout.setActivated(false);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        lp.height = getResources().getDimensionPixelSize(R.dimen.toolbar_height) + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? getStatusBarHeight() : 0);
        sliderLayout.setVisibility(View.GONE);
        appBarLayout.requestLayout();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
    }
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    private void enableCollapse(){
        appBarLayout.setActivated(true);
        collapsingToolbarLayout.setActivated(true);
        sliderLayout.setVisibility(View.VISIBLE);
        collapsingToolbarLayout.setTitleEnabled(true);
        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        p.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        collapsingToolbarLayout.setLayoutParams(p);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        lp.height = getResources().getDimensionPixelSize(R.dimen.toolbar_expanded_height);
        appBarLayout.requestLayout();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dumbsearch,menu);
        MenuItem search = menu.findItem(R.id.search);
        search.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void API_LOGOUT(){
        service.logoutRequest(token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("")){
                                    Toast.makeText(context, "TOKEN INVALID", Toast.LENGTH_LONG).show();
                                    session.logoutUser();
                                    finish();
                                }else if(jsonResults.getString("message").equals("token_expired")){
                                    Toast.makeText(context, "Have a nice day !", Toast.LENGTH_SHORT).show();
                                    session.logoutUser();
                                    finish();
                                }
                                else{
                                    Toast.makeText(context, "Have a nice day !", Toast.LENGTH_SHORT).show();
                                    session.logoutUser();
                                    finish();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with upir connection", Toast.LENGTH_LONG).show();
                        session.logoutUser();
                        finish();
                    }
                });
    }
    private void checkDataAPI(){
        progressDialog.setMessage("Just a sec, retrieving data for you");
        progressDialog.show();
        progressDialog.setCancelable(false);
        service.userRequest(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==401){
                    progressDialog.dismiss();
                    startActivity(new Intent(context, Error401.class));
                    finish();
                }
                else if(response.code()==200){
                        initFragmentManager();
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(context, "Whoops something wrong :O", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(context, "There is a problem with upir connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}

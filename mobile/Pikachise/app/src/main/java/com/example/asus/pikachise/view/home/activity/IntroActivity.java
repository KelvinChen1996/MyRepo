package com.example.asus.pikachise.view.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.LoginUser;
import com.example.asus.pikachise.view.authentication.RegisterUser;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout introdotsLayout;
    private Button btnLogin, btnRegister, btnNext, btnPrev, btnGo;
    private TextView[] dots;
    private int[] layouts;
    private SessionManagement sessionManagement;
    private IntroVPAdapter introVPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
        sessionManagement = new SessionManagement(this);
//        if(!sessionManagement.isFirstTimeLaunch()){
//            launchHomeScreen();
//            finish();
//        }
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        layouts = new int[]{
                R.layout.intro_slider1,
                R.layout.intro_slider2,
                R.layout.intro_slider3,
                R.layout.intro_slider4};
        addBottomDots(0);

        changeStatusBarColor();
        initClickListener();
        introVPAdapter = new IntroVPAdapter();
        viewPager.setAdapter(introVPAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }
    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.intro_viewpager);
        introdotsLayout = (LinearLayout) findViewById(R.id.intro_layoutdots);
        btnNext = (Button) findViewById(R.id.intro_btnNext);
        btnPrev = (Button) findViewById(R.id.intro_btnPrev);
        btnGo = (Button) findViewById(R.id.intro_btnGo);
        btnLogin = (Button) findViewById(R.id.intro_btnLogin);
        btnRegister = (Button) findViewById(R.id.intro_btnRegister);
    }
    private void initClickListener(){
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(-1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                }
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, LoginUser.class));
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, RegisterUser.class));
                finish();
            }
        });
        btnPrev.setVisibility(View.GONE);
        btnGo.setVisibility(View.GONE);
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                btnNext.setVisibility(View.GONE);
                btnGo.setVisibility(View.VISIBLE);
            }
            else if (position == 0){
                btnPrev.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
            }
            else {
                btnNext.setText(getString(R.string.next));
                btnPrev.setText(getString(R.string.prev));
                btnPrev.setTextSize(40);
                btnGo.setVisibility(View.GONE);
                btnPrev.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void launchHomeScreen(){
        sessionManagement.setIsFirstTimeLaunch(false);
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }
    private void addBottomDots(int currentPage){
        dots = new TextView[layouts.length];
        introdotsLayout.removeAllViews();
        for(int i = 0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey));
            introdotsLayout.addView(dots[i]);
        }
        if(dots.length>0){
//            dots[currentPage].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dots[currentPage].getLayoutParams();
//            params.setMargins(0, 0, 0, 10);
            dots[currentPage].setPadding(0, 22, 0, 0);
            dots[currentPage].setTextSize(20);
            dots[currentPage].setText(Html.fromHtml("&#9670;"));
            dots[currentPage].setTextColor(getResources().getColor(R.color.white));
        }
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public class IntroVPAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public IntroVPAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}

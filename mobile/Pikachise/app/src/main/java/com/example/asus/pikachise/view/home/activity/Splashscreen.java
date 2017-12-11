package com.example.asus.pikachise.view.home.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Window;
import android.view.WindowManager;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.session.SessionManagement;

public class Splashscreen extends AppCompatActivity {

    private static int splashtimer = 2000;
    private SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        sessionManagement = new SessionManagement(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i;
                if(sessionManagement.isFirstTimeLaunch()){
                    i = new Intent(Splashscreen.this, IntroActivity.class);
                    sessionManagement.setIsFirstTimeLaunch(false);
                } else{
                    i = new Intent(Splashscreen.this, MainActivity.class);
                }
                startActivity(i);
                finish();
            }
        }, splashtimer);

    }
}

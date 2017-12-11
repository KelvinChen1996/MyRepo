package com.example.asus.pikachise.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.session.SessionManagement;

public class Error401 extends AppCompatActivity {

    SessionManagement session;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManagement(getApplicationContext());
        context = this;
        setContentView(R.layout.activity_error401);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                session.logoutUser();
                startActivity(new Intent(context, LoginUser.class));
                finish();
            }
        }, 2000);
    }
}

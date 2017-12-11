package com.example.asus.pikachise.view.event.eventbarcode;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.pikachise.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Barcode extends AppCompatActivity {

    @BindView(R.id.toolbarBarcode) Toolbar toolbar;
    @BindView(R.id.imageBarcode) ImageView imageBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        ButterKnife.bind(this);
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bitmap bitmap = getIntent().getParcelableExtra("pic");
        imageBarcode.setImageBitmap(bitmap);
    }


}

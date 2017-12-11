package com.example.asus.pikachise.view.myfranchise.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.example.asus.pikachise.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class getFromMaps extends AppCompatActivity implements OnMapReadyCallback {
    private final static String LATITUDE = "LATITUDE";
    private final static String LONGITUDE = "LONGITUDE";

    @BindView(R.id.getfrommaps_fab) FloatingActionButton fab;
    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Marker marker;
    Double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_from_maps);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.getfrommaps_maps);
        mapFragment.getMapAsync(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(MainActivity.this, longitude.toString() + " " + latitude.toString() , Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.putExtra(LATITUDE, String.valueOf(latitude));
                i.putExtra(LONGITUDE, String.valueOf(longitude));
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
            }
        });
        LatLng sydney = new LatLng(3.587277, 98.692222);
        marker =  mMap.addMarker(new MarkerOptions().position(sydney).title("Your marker").draggable(true));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }
}

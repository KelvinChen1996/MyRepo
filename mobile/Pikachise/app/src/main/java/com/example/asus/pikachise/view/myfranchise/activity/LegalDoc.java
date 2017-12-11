package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LegalDoc extends AppCompatActivity {
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
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
    private final static String GOTOPICTURE = "GOTOPICTURE";
    private final static String ACTIVITY = "ACTIVITY";
    private static final int SELECT_TDP = 1;
    private static final int SELECT_SIUP = 2;
    private static final int SELECT_SURATPERJANJIAN = 3;
    private static final int SELECT_STPW = 4;
    private static final int SELECT_KTP = 5;
    private static final int SELECT_COMPANYPROFILE = 6;
    private static final int SELECT_LAPORANKEUANGAN = 7;
    private static final int SELECT_SURATIZINTEKNIK = 8;
    private static final int SELECT_TDPF = 9;

    public static Activity legaldocActivity;

    @BindView(R.id.uploadlegaldoc_image_tdp) ImageView tdp;
    @BindView(R.id.uploadlegaldoc_upload_tdp) ImageView uploadtdp;
    @BindView(R.id.uploadlegaldoc_image_siup) ImageView siup;
    @BindView(R.id.uploadlegaldoc_upload_siup) ImageView uploadsiup;
    @BindView(R.id.uploadlegaldoc_image_suratperjanjian) ImageView suratperjanjian;
    @BindView(R.id.uploadlegaldoc_upload_suratperjanjian) ImageView uploadsuratperjanjian;
    @BindView(R.id.uploadlegaldoc_image_stpw) ImageView stpw;
    @BindView(R.id.uploadlegaldoc_upload_stpw) ImageView uploadstpw;
    @BindView(R.id.uploadlegaldoc_image_ktp) ImageView ktp;
    @BindView(R.id.uploadlegaldoc_upload_ktp) ImageView uploadktp;
    @BindView(R.id.uploadlegaldoc_image_companyprofile) ImageView companyprofile;
    @BindView(R.id.uploadlegaldoc_upload_companyprofile) ImageView uploadcompanyprofile;
    @BindView(R.id.uploadlegaldoc_image_laporankeuangan) ImageView laporankeuangan;
    @BindView(R.id.uploadlegaldoc_upload_laporankeuangan) ImageView uploadlaporankeuangan;
    @BindView(R.id.uploadlegaldoc_image_suratizinteknik) ImageView suratizinteknik;
    @BindView(R.id.uploadlegaldoc_upload_suratizinteknik) ImageView uploadsuratizinteknik;
    @BindView(R.id.uploadlegaldoc_image_tdpf) ImageView tdpf;
    @BindView(R.id.uploadlegaldoc_upload_tdpf) ImageView uploadtdpf;
    @BindView(R.id.uploadlegaldoc_toolbar) Toolbar toolbar;
    @BindView(R.id.uploadlegaldoc_container) LinearLayout container;
    @BindView(R.id.uploadlegaldoc_info_companyprofile) ImageView infocompanyprofile;
    @BindView(R.id.uploadlegaldoc_info_ktp) ImageView infoktp;
    @BindView(R.id.uploadlegaldoc_info_laporankeuangan2tahunterakhir) ImageView infolaporankeuangan2tahunterakhir;
    @BindView(R.id.uploadlegaldoc_info_siup) ImageView infosiup;
    @BindView(R.id.uploadlegaldoc_info_stpw) ImageView infostpw;
    @BindView(R.id.uploadlegaldoc_info_suratizinteknik) ImageView infosuratizinteknik;
    @BindView(R.id.uploadlegaldoc_info_suratperjanjian) ImageView infosuratperjanjian;
    @BindView(R.id.uploadlegaldoc_info_tdp) ImageView infotdp;
    @BindView(R.id.uploadlegaldoc_info_tdpf) ImageView infotdpf;
    @BindView(R.id.uploadlegaldoc_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    Context context;
    apiService service;
    SessionManagement session;
    private String mediapath_tdp,mediapath_siup,mediapath_suratperjanjian,mediapath_stpw,mediapath_ktp,mediapath_companyprofile,mediapath_laporankeuangan,mediapath_suratizinteknik,mediapath_tdpf;
    private String token;
    private PermissionsChecker checker;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;
    private Drawable bannerdrawable;
    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid,
            extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee, extrawebsite,
            extraaddress, extralocation, extraphonenumber, extradetail, extraactivity;
    private String checktdp, checksiup, checksuratperjanjian, checkstpw, checkktp, checkcompanyprofile, checklaporankeuangan, checksuratizinteknik, checktdpf;
    private Boolean booltdp, boolsiup, boolsuratperjanjian, boolstpw, boolktp, boolcompanyprofile, boollaporankeuangan, boolsuratizinteknik, booltdpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_doc);
        initObject();
        initGetIntent();
        setupToolbar();
        getDatafromAPI();
        initOnClick();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDatafromAPI();
            }
        });
    }
    private void initObject(){
        legaldocActivity = this;
        ButterKnife.bind(this);
        context = this;
        checker = new PermissionsChecker(this);
        service = apiUtils.getAPIService();

        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        progressDialog = new ProgressDialog(this);
        checktdp = null;
        checksiup = null;
        checksuratperjanjian = null;
        checkstpw = null;
        checkktp = null;
        checkcompanyprofile = null;
        checklaporankeuangan = null;
        checksuratizinteknik = null;
        checktdpf = null;
    }
    private void initSetPicture(){
        bannerdrawable = getResources().getDrawable(R.drawable.document);
        if (checktdp == "null"){
            tdp.setImageDrawable(bannerdrawable);
            booltdp = true;
        }
        if(checktdp != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checktdp).placeholder(R.drawable.logo404).error(bannerdrawable).into(tdp);
            booltdp = false;
        }
        if(checksiup == "null"){
            siup.setImageDrawable(bannerdrawable);
            boolsiup = true;
        }
        if(checksiup != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checksiup).placeholder(R.drawable.logo404).error(bannerdrawable).into(siup);
            boolsiup = false;
        }
        if(checksuratperjanjian == "null"){
            suratperjanjian.setImageDrawable(bannerdrawable);
            boolsuratperjanjian = true;
        }
        if(checksuratperjanjian != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checksuratperjanjian).placeholder(R.drawable.logo404).error(bannerdrawable).into(suratperjanjian);
            boolsuratperjanjian = false;
        }

        if(checkstpw == "null"){
            stpw.setImageDrawable(bannerdrawable);
            boolstpw = true;
        }
        if(checkstpw != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checkstpw).placeholder(R.drawable.logo404).error(bannerdrawable).into(stpw);
            boolstpw = false;
        }

         if(checkktp == "null"){
            ktp.setImageDrawable(bannerdrawable);
            boolktp = true;
        }
         if(checkktp != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checkktp).placeholder(R.drawable.logo404).error(bannerdrawable).into(ktp);
            boolktp = false;
        }
         if(checkcompanyprofile == "null"){
            companyprofile.setImageDrawable(bannerdrawable);
            boolcompanyprofile = true;
        }
         if(checkcompanyprofile != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checkcompanyprofile).placeholder(R.drawable.logo404).error(bannerdrawable).into(companyprofile);
            boolcompanyprofile = false;
        }
         if(checklaporankeuangan == "null"){
            laporankeuangan.setImageDrawable(bannerdrawable);
            boollaporankeuangan = true;
        }
         if(checklaporankeuangan != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checklaporankeuangan).placeholder(R.drawable.logo404).error(bannerdrawable).into(laporankeuangan);
            boollaporankeuangan = false;
        }
         if(checksuratizinteknik == "null"){
            suratizinteknik.setImageDrawable(bannerdrawable);
            boolsuratizinteknik = true;
        }
         if(checksuratizinteknik != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checksuratizinteknik).placeholder(R.drawable.logo404).error(bannerdrawable).into(suratizinteknik);
            boolsuratizinteknik = false;
        }
         if(checktdpf == "null"){
            tdpf.setImageDrawable(bannerdrawable);
            booltdpf = true;
        }
         if(checktdpf != "null"){
            Picasso.with(getApplicationContext()).load(apiUtils.getUrlImage()+checktdpf).placeholder(R.drawable.logo404).error(bannerdrawable).into(tdpf);
            booltdpf = false;
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
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(FRANCHISEFEE)){
            extraactivity = getintent.getExtras().getString(ACTIVITY);
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
    private void initOnClick(){
        uploadtdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 1);
                startActivity(intent);
//                initUploadGallery(SELECT_TDP);
            }
        });
        uploadsiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 2);
                startActivity(intent);
            }
        });
        uploadsuratperjanjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 3);
                startActivity(intent);
            }
        });
        uploadstpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 4);
                startActivity(intent);
            }
        });
        uploadktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 5);
                startActivity(intent);
            }
        });
        uploadcompanyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 6);
                startActivity(intent);
            }
        });
        uploadlaporankeuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 7);
                startActivity(intent);
            }
        });
        uploadsuratizinteknik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 8);
                startActivity(intent);
            }
        });
        uploadtdpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPicture.class);
                intent.putExtra(FRANCHISE_ID, extrafranchiseid);
                intent.putExtra(GOTOPICTURE, 9);
                startActivity(intent);
            }
        });
        infotdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 0);
            }
        });
        infosiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 1);
            }
        });
        infosuratperjanjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 2);
            }
        });
        infostpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 3);
            }
        });
        infoktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 4);
            }
        });
        infocompanyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 5);
            }
        });
        infolaporankeuangan2tahunterakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 6);
            }
        });
        infosuratizinteknik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 7);
            }
        });
        infotdpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, 8);
            }
        });
    }

    public void showDialog(View view, int cat){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Information");
        if(cat == 0){
            alertDialog.setMessage("TDP is Tanda Daftar Perusahaan aftar catatan resmi yang diadakan menurut atau berdasarkan ketentuan undang-undang atau peraturan-peraturan pelaksanaannya, dan memuat hal-hal yang wajib didaftarkan oleh setiap perusahaan serta disahkan oleh pejabat yang berwenang.");
        }
        else if(cat == 1){
            alertDialog.setMessage("SIUP is Surat Izin Usaha Perdagangan");
        }
        else if(cat == 2){
            alertDialog.setMessage("Surat Perjanjian yang dimaksud adalah surat perjanjian franchise");
        }
        else if(cat == 3){
            alertDialog.setMessage("STPW is Surat Tanda Pendaftaran Waralaba");
        }
        else if(cat == 4){
            alertDialog.setMessage("KTP is Kartu Tanda Pendudukan");
        }
        else if(cat == 5){
            alertDialog.setMessage("Company Profile about your franchise");
        }
        else if(cat == 6){
            alertDialog.setMessage("Laporan Keuangan dalam dua tahun terahkir");
        }
        else if(cat == 7){
            alertDialog.setMessage("Surat Izin Teknik");
        }
        else if(cat == 8){
            alertDialog.setMessage("Tanda Daftar Perusahaan Franchise");
        }

        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Alert dialog action goes here
                        // onClick button code here
                        dialog.dismiss();// use dismiss to cancel alert dialog
                    }
                });
        alertDialog.show();
    }


    private void getDatafromAPI(){
        service.documentstatusRequest(extrafranchiseid, token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                checktdp = jsonResults.getJSONObject("document_status").getString("tdp");
                                checksiup = jsonResults.getJSONObject("document_status").getString("siup");
                                checksuratperjanjian = jsonResults.getJSONObject("document_status").getString("suratperjanjian");
                                checkstpw = jsonResults.getJSONObject("document_status").getString("stpw");
                                checkktp = jsonResults.getJSONObject("document_status").getString("ktpfranchisor");
                                checkcompanyprofile = jsonResults.getJSONObject("document_status").getString("companyprofile");
                                checklaporankeuangan = jsonResults.getJSONObject("document_status").getString("laporankeuangan2tahunterakhir");
                                checksuratizinteknik = jsonResults.getJSONObject("document_status").getString("suratizinteknis");
                                checktdpf = jsonResults.getJSONObject("document_status").getString("tandabuktipendaftaran");
                                initSetPicture();
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                        }
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
    }
    private void error401(){
        progressDialog.dismiss();
        startActivity(new Intent(context, Error401.class));
        finish();
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
            if(extraactivity.equals("pending")){
                PendingDetailMyFranchise.pendingdetailmyfranchiseActivity.finish();
            }
            else {
                DetailMyFranchise.detailmyfranchiseActivity.finish();
            }
            MyFranchiseActivity.myfranchiseActivity.finish();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

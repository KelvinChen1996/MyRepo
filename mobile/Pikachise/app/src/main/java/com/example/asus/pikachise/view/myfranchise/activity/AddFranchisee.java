package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.AuthActivity;
import com.example.asus.pikachise.view.authentication.Error401;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

public class AddFranchisee extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    @BindView(R.id.addfranchisee_toolbar) Toolbar toolbar;
    @BindView(R.id.addfranchisee_address) TextInputEditText address;
    @BindView(R.id.addfranchisee_agreement) ImageView agreement;
    @BindView(R.id.addfranchisee_container) LinearLayout container;
    @BindView(R.id.addfranchisee_date) LinearLayout date;
    @BindView(R.id.addfranchisee_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.addfranchisee_layoutphonenumber) TextInputLayout layoutphonenumber;
    @BindView(R.id.addfranchisee_layoutuseremail) TextInputLayout layoutemail;
    @BindView(R.id.addfranchisee_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.addfranchisee_tvsince) TextView tvsince;
    @BindView(R.id.addfranchisee_useremail) TextInputEditText useremail;
    @BindView(R.id.addfranchisee_longitude) TextInputEditText longitude;
    @BindView(R.id.addfranchisee_layoutlongitude) TextInputLayout layoutlongitude;
    @BindView(R.id.addfranchisee_latitude) TextInputEditText latitude;
    @BindView(R.id.addfranchisee_layoutlatitude) TextInputLayout layoutlatitude;

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
    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid,
            extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee,
            extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail;

    private Context context;
    private apiService service;
    private SessionManagement session;
    private Drawable bannerdrawable;
    private AlertDialog dialog;
    private String token, mediapath;
    private PermissionsChecker checker;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_franchisee);
        initObject();
        setupToolbar();
        initSetPicture();
        initGetIntent();

        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUploadGallery(100);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });
    }
    private void initObject(){
        context = this;
        ButterKnife.bind(this);
        session = new SessionManagement(getApplicationContext());
        checker = new PermissionsChecker(context);
        progressDialog = new ProgressDialog(this);
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anothertoolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.anothertoolbar_save) {
            registerFranchiseeApi();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.x1);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
    private void initSetPicture(){
        bannerdrawable = getResources().getDrawable(R.drawable.addpicture);
        agreement.setImageDrawable(bannerdrawable);
    }
    private void initUploadGallery(int REQ_CODE){
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        }else{
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, REQ_CODE);
        }
    }
    private void initGetIntent(){
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
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }
    public void datePicker(View view){

        DatePickerFranchisee fragment = new DatePickerFranchisee();
        fragment.show(getSupportFragmentManager(), "date");
    }
    public static class DatePickerFranchisee extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            Date today = new Date();
            c.setTime(today);
            long maxDate = c.getTime().getTime();
            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)
                    getActivity(), year, month, day);
            pickerDialog.getDatePicker().setMaxDate(maxDate);
            return pickerDialog;
        }
    }
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        tvsince.setText(dateFormat.format(calendar.getTime()));
    }
    private void startPermissionsActivity(String[] permission) {
        PermissionActivity.startActivityForResult(this, 100, permission);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toast.makeText(this, "Unable to pick image", Toast.LENGTH_LONG).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediapath = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediapath))
                            .into(agreement);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){}
    }
    private void registerFranchiseeApi(){
        layoutaddress.setErrorEnabled(false);
        layoutemail.setErrorEnabled(false);
        layoutphonenumber.setErrorEnabled(false);
        layoutlongitude.setErrorEnabled(false);
        layoutlatitude.setErrorEnabled(false);

        if (TextUtils.isEmpty(useremail.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("User Email is required");
            return;
        } else if (!AuthActivity.isemailvalid(useremail.getText().toString())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is not valid");
            return;
        } else if (TextUtils.isEmpty(tvsince.getText())) {
            Snackbar.make(container, "Date is required", Snackbar.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        }else if (TextUtils.isEmpty(longitude.getText())) {
            layoutlongitude.setErrorEnabled(true);
            layoutlongitude.setError("Address is required");
            return;
        }else if (TextUtils.isEmpty(latitude.getText())) {
            layoutlatitude.setErrorEnabled(true);
            layoutlatitude.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphonenumber.setErrorEnabled(true);
            layoutphonenumber.setError("Phone number is required");
            return;
        } else if(agreement.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert agreement", Snackbar.LENGTH_LONG).show();
            return;
        }

        String location = address.getText().toString() + "%" + latitude.getText().toString() + "%" + longitude.getText().toString();
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        File fileAgreement = new File(mediapath);
        RequestBody requestFileAgreement = RequestBody.create(MediaType.parse("multipart/form-data"), fileAgreement);
        MultipartBody.Part bodyAgreement = MultipartBody.Part.createFormData("agreement", fileAgreement.getName(), requestFileAgreement);

        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        RequestBody request_franchiseeemail = RequestBody.create(text, useremail.getText().toString());
        RequestBody requestSince = RequestBody.create(text, tvsince.getText().toString());
        RequestBody requestAddress = RequestBody.create(text, location);
        RequestBody requestPhoneNumber = RequestBody.create(text, phonenumber.getText().toString());



        service.addFranchiseeRequest(request_token, request_franchiseid, request_franchiseeemail, bodyAgreement, requestAddress, requestPhoneNumber, requestSince)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            progressDialog.dismiss();
                            startActivity(new Intent(context, Error401.class));
                            finish();
                        }
                        else if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try {
                                String message = response.body().string();
                                if(message.equals("[\"email franchisee tidak terdaftar\"]")){
                                    Snackbar.make(container, "User Email is not registered", Snackbar.LENGTH_SHORT).show();
                                }
                                else if(message.equals("[\"Franchisee telah didaftarkan\"]")){
                                    Snackbar.make(container, "Email has been registered as franchisee of this franchise before", Snackbar.LENGTH_SHORT).show();
                                }
                                else if(message.equals("[\"tidak diperbolehkan mendaftarkan email sendiri\"]")){
                                    Snackbar.make(container, "Cannot registered using email of this franchise", Snackbar.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context, "Franchisee registered successfully", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                    finish();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();

                        }
                        else{
                            Log.i("debug", "onResponse: FAILED");
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }



}

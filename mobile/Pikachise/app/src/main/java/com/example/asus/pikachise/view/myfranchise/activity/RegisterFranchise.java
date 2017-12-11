package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.MyFranchise;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.FinancialTextWatcher;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.AuthActivity;
import com.example.asus.pikachise.view.authentication.LoginUser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFranchise extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int SELECT_LOGO = 1;
    private static final int SELECT_BANNER = 2;
    private final static String LATITUDE = "LATITUDE";
    private final static String LONGITUDE = "LONGITUDE";

    @BindView(R.id.registerfranchise_address) TextInputEditText address;
    @BindView(R.id.registerfranchise_banner) ImageView banner;
    @BindView(R.id.registerfranchise_category) Spinner category;
    @BindView(R.id.registerfranchise_email) TextInputEditText email;
    @BindView(R.id.registerfranchise_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.registerfranchise_layoutemail) TextInputLayout layoutemail;
    @BindView(R.id.registerfranchise_layoutname) TextInputLayout layoutname;
    @BindView(R.id.registerfranchise_layoutphonenumber) TextInputLayout layoutphone;
    @BindView(R.id.registerfranchise_layoutwebsite) TextInputLayout layoutwebsite;
    @BindView(R.id.registerfranchise_logo) CircleImageView logo;
    @BindView(R.id.registerfranchise_name) TextInputEditText name;
    @BindView(R.id.registerfranchise_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.registerfranchise_toolbar) Toolbar toolbar;
    @BindView(R.id.registerfranchise_type) Spinner type;
    @BindView(R.id.registerfranchise_website) TextInputEditText website;
    @BindView(R.id.registerfranchise_container) LinearLayout container;
    @BindView(R.id.registerfranchise_since) LinearLayout sinces;
    @BindView(R.id.registerfranchise_tvsince) TextView tvsince;
    @BindView(R.id.registerfranchise_franchisefee) TextInputEditText franchisefee;
    @BindView(R.id.registerfranchise_layoutfranchisefee) TextInputLayout layoutfranchisefee;
    @BindView(R.id.registerfranchise_layoutdetail) TextInputLayout layoutdetail;
    @BindView(R.id.registerfranchise_detail) TextInputEditText detail;
    @BindView(R.id.registerfranchise_layoutinvestment) TextInputLayout layoutinvestmens;
    @BindView(R.id.registerfranchise_investment) TextInputEditText investments;
    @BindView(R.id.registerfranchise_openfromgooglemaps) LinearLayout openfromgooglemaps;
    @BindView(R.id.registerfranchise_layoutlongitude) TextInputLayout layoutllongitude;
    @BindView(R.id.registerfranchise_layoutlatitude) TextInputLayout layoutlatitude;
    @BindView(R.id.registerfranchise_latitude) TextInputEditText latitude;
    @BindView(R.id.registerfranchise_longitude) TextInputEditText longitude;
    private Context context;
    private ArrayAdapter<CharSequence> categoryAdapter, typeAdapter, countryAdapter;
    private apiService service;
    private String mediaPathLogo,mediaPathBanner;
    private ProgressDialog progressDialog;
    private PermissionsChecker checker;
    private String choosencategory, choosentype, choosencountry, token;
    private Drawable logodrawable, bannerdrawable;
    private AlertDialog dialog;
    SessionManagement session;
    String lat, lo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_franchise);
        initObject();
        initSpinner();
        setupToolbar();
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUploadGallery(SELECT_LOGO);
            }
        });

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUploadGallery(SELECT_BANNER);
            }
        });
        sinces.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePicker(v);
                }
            });

        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);

        franchisefee.addTextChangedListener(new FinancialTextWatcher(franchisefee));
        franchisefee.addTextChangedListener(new FinancialTextWatcher(franchisefee));
        investments.addTextChangedListener(new FinancialTextWatcher(investments));

        openfromgooglemaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, getFromMaps.class);
                startActivityForResult(i, 1);
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }
    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }
    public static class DatePickerFragment extends DialogFragment {
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
    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        checker = new PermissionsChecker(this);
        progressDialog = new ProgressDialog(this);
        logodrawable = getResources().getDrawable(R.drawable.addcamera);
        logo.setImageDrawable(logodrawable);
        bannerdrawable = getResources().getDrawable(R.drawable.addpicture);
        banner.setImageDrawable(bannerdrawable);
        session = new SessionManagement(getApplicationContext());
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anothertoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.anothertoolbar_save) {
            postDataToAPI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initSpinner(){
        categoryAdapter = ArrayAdapter.createFromResource(context, R.array.categoryspinner, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        typeAdapter = ArrayAdapter.createFromResource(context, R.array.typespinner, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
//
//        countryAdapter = ArrayAdapter.createFromResource(context, R.array.countryspinner, android.R.layout.simple_spinner_item);
//        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        country.setAdapter(countryAdapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosencategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosentype = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                choosencountry = (String) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                if(resultCode == RESULT_OK) {
                    lo = data.getStringExtra(LONGITUDE);
                    lat = data.getStringExtra(LATITUDE);
                    latitude.setText(lat);
                    longitude.setText(lo);
                }
            }
            if (requestCode == SELECT_LOGO && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toast.makeText(this, "Unable to pick image", Toast.LENGTH_LONG).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathLogo = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathLogo))
                            .into(logo);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            if (requestCode == SELECT_BANNER && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toast.makeText(this, "Unable to pick image", Toast.LENGTH_LONG).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathBanner = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathBanner))
                            .into(banner);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){}
    }
    private void startPermissionsActivity(String[] permission) {
        PermissionActivity.startActivityForResult(this, SELECT_BANNER, permission);
        PermissionActivity.startActivityForResult(this, SELECT_LOGO, permission);
    }
    private void postDataToAPI(){
        layoutaddress.setErrorEnabled(false);
        layoutemail.setErrorEnabled(false);
        layoutname.setErrorEnabled(false);
        layoutphone.setErrorEnabled(false);
        layoutwebsite.setErrorEnabled(false);
        layoutfranchisefee.setErrorEnabled(false);
        layoutinvestmens.setErrorEnabled(false);
        layoutdetail.setErrorEnabled(false);
        layoutlatitude.setErrorEnabled(false);
        layoutllongitude.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText())) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name is required");
            return;
        } else if (TextUtils.isEmpty(tvsince.getText())) {
            Toast.makeText(context, "Date of Established is required", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(investments.getText())) {
            layoutinvestmens.setErrorEnabled(true);
            layoutinvestmens.setError("Investment is required");
            return;
        } else if (TextUtils.isEmpty(franchisefee.getText())) {
            layoutfranchisefee.setErrorEnabled(true);
            layoutfranchisefee.setError("Franchisee fee is required");
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(latitude.getText())) {
            layoutlatitude.setErrorEnabled(true);
            layoutlatitude.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(longitude.getText())) {
            layoutllongitude.setErrorEnabled(true);
            layoutllongitude.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphone.setErrorEnabled(true);
            layoutphone.setError("Phone number is required");
            return;
        } else if (TextUtils.isEmpty(email.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is required");
            return;
        } else if (!AuthActivity.isemailvalid(email.getText().toString())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is not valid");
            return;
        } else if (TextUtils.isEmpty(website.getText())) {
            layoutwebsite.setErrorEnabled(true);
            layoutwebsite.setError("Website is required");
            return;
        } else if (TextUtils.isEmpty(detail.getText())) {
            layoutdetail.setErrorEnabled(true);
            layoutdetail.setError("Address is required");
            return;
        } else if(logo.getDrawable().equals(logodrawable)){
            Snackbar.make(container, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
        } else if(banner.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();

        MediaType text = MediaType.parse("text/plain");

        File filelogo = new File(mediaPathLogo);
        RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filelogo);
        MultipartBody.Part bodylogo = MultipartBody.Part.createFormData("logo", filelogo.getName(), requestFileLogo);

        File filebanner = new File(mediaPathBanner);
        RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
        MultipartBody.Part bodybanner = MultipartBody.Part.createFormData("banner", filebanner.getName(), requestFileBanner);
//
        String gabung = lat + "%" + lo;

        RequestBody requestName = RequestBody.create(text, name.getText().toString());
        RequestBody requestCategory = RequestBody.create(text, choosencategory);
        RequestBody requestType = RequestBody.create(text, choosentype);
        RequestBody requestSince = RequestBody.create(text, tvsince.getText().toString());
        RequestBody requestInvestment = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(investments.getText().toString()));
        RequestBody requestFranchisefee = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(franchisefee.getText().toString()));
        RequestBody requestWebsite = RequestBody.create(text, website.getText().toString());
        RequestBody requestAddress = RequestBody.create(text, address.getText().toString());
        RequestBody requestLocation = RequestBody.create(text, gabung);
        RequestBody requestPhoneNumber = RequestBody.create(text, phonenumber.getText().toString());
        RequestBody requestEmail = RequestBody.create(text, email.getText().toString());
        RequestBody requestAveragerating = RequestBody.create(text, "0");
        RequestBody requestDetail = RequestBody.create(text, detail.getText().toString());
        RequestBody requestToken = RequestBody.create(text, token);
//        RequestBody requestName = RequestBody.create(text, "test1");
//        RequestBody requestCategory = RequestBody.create(text, "test1");
//        RequestBody requestType = RequestBody.create(text, "test1");
//        RequestBody requestSince = RequestBody.create(text, "test1");
//        RequestBody requestInvestment = RequestBody.create(text, "test1");
//        RequestBody requestFranchisefee = RequestBody.create(text, "test1");
//        RequestBody requestWebsite = RequestBody.create(text, "test1");
//        RequestBody requestAddress = RequestBody.create(text, "test1");
//        RequestBody requestLocation = RequestBody.create(text, "test1");
//        RequestBody requestPhoneNumber = RequestBody.create(text, "test1");
//        RequestBody requestEmail = RequestBody.create(text, "test1");
//        RequestBody requestAveragerating = RequestBody.create(text, "test1");
//        RequestBody requestDetail = RequestBody.create(text, "test1");
//        RequestBody requestToken = RequestBody.create(text, token);
//
        service = apiUtils.getAPIService();
        service.registerFranchiseRequest(requestName, bodylogo, bodybanner, requestCategory, requestType, requestSince, requestInvestment, requestFranchisefee, requestWebsite, requestAddress
        ,requestLocation, requestPhoneNumber, requestEmail, requestAveragerating, requestDetail, requestToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("debug", "onResponse: SUCCESS");
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getString("message").equals("Franchise registered successfully")){
                            String message = jsonResults.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context, MyFranchiseActivity.class));
                            finish();
                        }else{
                            String message = jsonResults.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                        //"message": "Nama Franchise sudah didaftarkan"
                        //"message": "Franchise registered successfully",
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    Log.i("debug", "onResponse: FAILED");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }



}

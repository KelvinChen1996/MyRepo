package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.FinancialTextWatcher;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.AuthActivity;
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

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

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
    private final static String ACTIVITY = "ACTIVITY";

    @BindView(R.id.addevent_address) TextInputEditText address;
    @BindView(R.id.addevent_banner) ImageView banner;
    @BindView(R.id.addevent_container) LinearLayout container;
    @BindView(R.id.addevent_detail) TextInputEditText detail;
    @BindView(R.id.addevent_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.addevent_layoutdetail) TextInputLayout layoutdetail;
    @BindView(R.id.addevent_layoutname) TextInputLayout layoutname;
    @BindView(R.id.addevent_layoutprice) TextInputLayout layoutprice;
    @BindView(R.id.addevent_name) TextInputEditText name;
    @BindView(R.id.addevent_price) TextInputEditText price;
    @BindView(R.id.addevent_since) LinearLayout since;
    @BindView(R.id.addevent_time) LinearLayout time;
    @BindView(R.id.addevent_toolbar) Toolbar toolbar;
    @BindView(R.id.addevent_tvsince) TextView tvsince;
    @BindView(R.id.addevent_tvtime) TextView tvtime;

    private apiService service;
    private String mediaPathLogo,mediaPathBanner;
    private ProgressDialog progressDialog;
    private PermissionsChecker checker;
    private String choosencategory, choosentype, choosencountry, token;
    private Drawable logodrawable, bannerdrawable;
    private AlertDialog dialog;
    private Context context;
    SessionManagement session;

    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid, extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee, extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initObject();
        getactivityIntent();
        setupToolbar();
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUploadGallery(0);
            }
        });
        since.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvtime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        price.addTextChangedListener(new FinancialTextWatcher(price));

    }
    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        checker = new PermissionsChecker(this);
        progressDialog = new ProgressDialog(this);
        bannerdrawable = getResources().getDrawable(R.drawable.picture);
        banner.setImageDrawable(bannerdrawable);
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anothertoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.anothertoolbar_save) {
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }
    public void datePicker(View view){

        eventDatepickerFragment fragment = new eventDatepickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }
    public static class eventDatepickerFragment extends DialogFragment {
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
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
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
        PermissionActivity.startActivityForResult(this, 0, permission);
    }
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you sure to save changes ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addEvent();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void addEvent(){
        layoutname.setErrorEnabled(false);
        layoutaddress.setErrorEnabled(false);
        layoutprice.setErrorEnabled(false);
        layoutdetail.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText())) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name of event is required");
            return;
        } else if (TextUtils.isEmpty(tvsince.getText())) {
            Toast.makeText(context, "Date of Event Start is required", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(tvtime.getText())) {
            Toast.makeText(context, "Time of Event Start is required", Toast.LENGTH_LONG).show();
            return;
        }else if (TextUtils.isEmpty(price.getText())) {
            layoutprice.setErrorEnabled(true);
            layoutprice.setError("Price is required");
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(detail.getText())) {
            layoutdetail.setErrorEnabled(true);
            layoutdetail.setError("Address is required");
            return;
        } else if(banner.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
        }

        MediaType text = MediaType.parse("text/plain");

        File filebanner = new File(mediaPathBanner);
        RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
        MultipartBody.Part bodybanner = MultipartBody.Part.createFormData("image", filebanner.getName(), requestFileBanner);

        RequestBody requestName = RequestBody.create(text, name.getText().toString());
        RequestBody requestSince = RequestBody.create(text, tvsince.getText().toString());
        RequestBody requestTime = RequestBody.create(text, tvtime.getText().toString());
        RequestBody requestPrice = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(price.getText().toString()));
        RequestBody requestAddress = RequestBody.create(text, address.getText().toString());
        RequestBody requestDetail = RequestBody.create(text, detail.getText().toString());
        RequestBody requestToken = RequestBody.create(text, token);
        RequestBody requestFranchiseId = RequestBody.create(text, extrafranchiseid);
        service = apiUtils.getAPIService();
        service.addEvent(requestToken, requestFranchiseId, requestName, requestSince, requestTime, requestAddress, requestDetail, bodybanner, requestPrice)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("Event added successfully")){
                                    Toast.makeText(context, "Success, please pull down to refresh", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                    finish();
                                }else{
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getactivityIntent(){
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

}

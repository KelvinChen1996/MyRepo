package com.example.asus.pikachise.view.myfranchise.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFranchisee extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private final static String DATE_JOIN = "DATE_JOIN";
    private final static String OUTLET_ID = "OUTLET_ID";
    private final static String ADDRESS = "ADDRESS";
    private final static String PHONENUMBER = "PHONENUMBER";
    private final static String USER_ID = "USER_ID";
    private final static String EMAIL = "EMAIL";
    private final static String NAME = "NAME";

    @BindView(R.id.editfranchisee_address) TextInputEditText address;
    @BindView(R.id.editfranchisee_container) LinearLayout container;
    @BindView(R.id.editfranchisee_date) LinearLayout date;
    @BindView(R.id.editfranchisee_email) TextView email;
    @BindView(R.id.editfranchisee_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.editfranchisee_layoutphonenumber) TextInputLayout layoutphonenumber;
    @BindView(R.id.editfranchisee_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.editfranchisee_toolbar) Toolbar toolbar;
    @BindView(R.id.editfranchisee_tvsince) TextView since;
    @BindView(R.id.editfranchisee_name) TextView name;
    private Context context;
    private apiService service;
    private SessionManagement session;
    private Drawable bannerdrawable;
    private AlertDialog dialog;
    private String token, mediapath;
    private PermissionsChecker checker;

    private String extraname, extrauserid, extraemail, extradate, extraphonenumber, extraaddress, extraoutletid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_franchisee);
        initObject();
        initGetIntent();
        setupToolbar();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });
        email.setText(extraemail);
        name.setText(extraname);
        address.setText(extraaddress);
        since.setText(extradate);
        phonenumber.setText(extraphonenumber);
    }

    private void initObject(){
        context = this;
        ButterKnife.bind(this);
        session = new SessionManagement(getApplicationContext());
        checker = new PermissionsChecker(context);
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
            showDialog();
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
        getSupportActionBar().setTitle("Edit Franchisee");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(OUTLET_ID)){
            extraoutletid = getintent.getExtras().getString(OUTLET_ID);
            extradate = getintent.getExtras().getString(DATE_JOIN);
            extraemail = getintent.getExtras().getString(EMAIL);
            extrauserid = getintent.getExtras().getString(USER_ID);
            extraname = getintent.getExtras().getString(NAME);
            extraaddress = getintent.getExtras().getString(ADDRESS);
            extraphonenumber = getintent.getExtras().getString(PHONENUMBER);
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

        DatePickerEditFranchisee fragment = new DatePickerEditFranchisee();
        fragment.show(getSupportFragmentManager(), "date");
    }
    public static class DatePickerEditFranchisee extends DialogFragment {
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
        since.setText(dateFormat.format(calendar.getTime()));
    }
    private void editfranchisee(){
        layoutaddress.setErrorEnabled(false);
        layoutphonenumber.setErrorEnabled(false);
        if (TextUtils.isEmpty(since.getText())) {
            Snackbar.make(container, "Date is required", Snackbar.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        }else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphonenumber.setErrorEnabled(true);
            layoutphonenumber.setError("Phone number is required");
            return;
        }
        service.editmyfranchisee(extraoutletid, address.getText().toString(), phonenumber.getText().toString(), since.getText().toString(), token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Toast.makeText(context, "Success, please pull down to refresh", Toast.LENGTH_LONG).show();
                            onBackPressed();
                            finish();
                        }
                        else{
                            Log.i("debug", "onResponse: FAILED");
                            Toast.makeText(context, "Whoops, something wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void failure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "There is a problem with internet connection", Toast.LENGTH_SHORT).show();
    }
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you sure to save changes ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editfranchisee();
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
}

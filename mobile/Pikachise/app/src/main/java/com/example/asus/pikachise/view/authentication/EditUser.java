package com.example.asus.pikachise.view.authentication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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

public class EditUser extends AppCompatActivity {
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    public static Activity edituserActivity;
    @BindView(R.id.edituser_layoutname) TextInputLayout layoutname;
    @BindView(R.id.edituser_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.edituser_layoutphonenumber) TextInputLayout layoutphonenumber;
    @BindView(R.id.edituser_name) TextInputEditText name;
    @BindView(R.id.edituser_address) TextInputEditText address;
    @BindView(R.id.edituser_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.edituser_toolbar) Toolbar toolbar;
    @BindView(R.id.edituser_imageprofile) CircleImageView profilepic;
    @BindView(R.id.edituser_email) TextView email;
    @BindView(R.id.edituser_container) LinearLayout container;
    @BindView(R.id.edituser_changepassword) Button changepassword;
    apiService service;
    Context context;
    SessionManagement session;
    PermissionsChecker checker;
    AlertDialog dialog;
    String mediaPath;
    private String baseimageurl;


    private String token, semail, image, sname , saddress, id, sphonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        initObject();
        setupToolbar();
        initDataSession();
        initgetUser();
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUploadGallery();
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, ChangePassword.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                overridePendingTransition(R.anim.slideright, R.anim.fadeout);
            }
        });

    }
    private void initObject(){
        edituserActivity = this;
        ButterKnife.bind(this);
        context = this;
        session = new SessionManagement(getApplicationContext());
        checker = new PermissionsChecker(context);
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void initgetUser(){
        service.userRequest(token).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            responseAPI401();
                        }
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                semail = jsonResults.getJSONObject("result").getString("email");
                                sname = jsonResults.getJSONObject("result").getString("name");
                                image = jsonResults.getJSONObject("result").getString("image");
                                saddress = jsonResults.getJSONObject("result").getString("address");
                                sphonenumber = jsonResults.getJSONObject("result").getString("phone_number");
                                name.setText(sname);
                                address.setText(saddress);
                                phonenumber.setText(sphonenumber);
                                email.setText(semail);
                                baseimageurl = apiUtils.getUrlImage();
                                Picasso.with(getApplicationContext()).load(baseimageurl+image).placeholder(R.drawable.user).error(R.drawable.rect_error).into(profilepic);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void initDataSession(){
        session.checkLogin();
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
        getSupportActionBar().setTitle("Edit Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGoHome();
            }
        });
    }
    private void initUploadGallery(){
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        }else{
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 0);
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
                    mediaPath = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPath))
                            .into(profilepic);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){}
    }

    private void responseAPI401(){
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    private void sendEdittoApi(){
        layoutname.setErrorEnabled(false);
        layoutaddress.setErrorEnabled(false);
        layoutphonenumber.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText())) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name is required");
            return;
        } else if (name.getText().length() < 3) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name minimal 3");
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphonenumber.setErrorEnabled(true);
            layoutphonenumber.setError("Phone number is required");
            return;
        }
        MediaType text = MediaType.parse("text/plain");
        File file = new File(mediaPath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody requestName = RequestBody.create(text, name.getText().toString());
        RequestBody requestAddress = RequestBody.create(text, address.getText().toString());
        RequestBody requestPhoneNumber = RequestBody.create(text, phonenumber.getText().toString());
        RequestBody requestToken = RequestBody.create(text, token);

        service = apiUtils.getAPIService();
        service.editProfile(requestToken, requestName, requestAddress, requestPhoneNumber, body).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==401 || response.code()==400){
                            responseAPI401();
                        }
                        else if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("Edited successfully")){
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    initGoHome();

                                }else{
                                    String message = jsonResults.getString("message");
                                    Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                            Toast.makeText(context, "Whoops something wrong !", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with upir connection", Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you sure to save changes ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendEdittoApi();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editdetailfranchisemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menueditdetailfranchise_home) {
            initGoHome();
            return true;
        }
        if(id==R.id.menueditdetailfranchise_save){
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private void startPermissionsActivity(String[] permission) {
        PermissionActivity.startActivityForResult(this, 0, permission);
    }
    private void initGoHome(){
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
        finish();
    }
}

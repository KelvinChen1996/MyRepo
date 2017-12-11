package com.example.asus.pikachise.view.authentication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.model.User;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

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

public class RegisterUser extends AppCompatActivity{
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @BindView(R.id.registeruser_container) LinearLayout linearLayout;
    @BindView(R.id.registeruser_layoutName) TextInputLayout textInputLayoutName;
    @BindView(R.id.registeruser_layoutEmail) TextInputLayout textInputLayoutEmail;
    @BindView(R.id.registeruser_layoutPassword) TextInputLayout textInputLayoutPassword;
    @BindView(R.id.registeruser_layoutConfirmPassword) TextInputLayout textInputLayoutConfirmPassword;
    @BindView(R.id.registeruser_Name) TextInputEditText textInputEditTextName;
    @BindView(R.id.registeruser_Email) TextInputEditText textInputEditTextEmail;
    @BindView(R.id.registeruser_Password) TextInputEditText textInputEditTextPassword;
    @BindView(R.id.registeruser_ConfirmPassword) TextInputEditText textInputEditTextConfirmPassword;
    @BindView(R.id.registeruser_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.registeruser_address) TextInputEditText address;
    @BindView(R.id.registeruser_layoutphonenumber) TextInputLayout layoutphonenumber;
    @BindView(R.id.registeruser_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.registeruser_register) Button ButtonRegister;
    @BindView(R.id.registeruser_login) TextView TextViewLoginLink;
    @BindView(R.id.registeruser_camera) CircleImageView camera;
    Typeface typeface;
    Context context;
    apiService service;
    String mediaPath;
    ProgressDialog progressDialog;
    AlertDialog dialog;
    PermissionsChecker checker;
    Drawable myDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        initObjects();

        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToAPI();
            }
        });
        TextViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginUser.class);
                startActivity(i);
                finish();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUploadGallery();
            }
        });
    }


    private void initObjects() {
        ButterKnife.bind(this);
        context = this;
        checker = new PermissionsChecker(this);
        progressDialog = new ProgressDialog(this);

        myDrawable = getResources().getDrawable(R.drawable.addcamera);
        camera.setImageDrawable(myDrawable);
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
                            .into(camera);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){}
    }

    private void postDataToAPI(){
        textInputLayoutName.setErrorEnabled(false);
        textInputLayoutEmail.setErrorEnabled(false);
        textInputLayoutPassword.setErrorEnabled(false);
        textInputLayoutConfirmPassword.setErrorEnabled(false);
        layoutaddress.setErrorEnabled(false);
        layoutphonenumber.setErrorEnabled(false);
        if (TextUtils.isEmpty(textInputEditTextName.getText())) {
            textInputLayoutName.setErrorEnabled(true);
            textInputLayoutName.setError("Name is required");
            return;
        } else if (textInputEditTextName.getText().length() < 3) {
            textInputLayoutName.setErrorEnabled(true);
            textInputLayoutName.setError("Name minimal 3");
            return;
        } else if (TextUtils.isEmpty(textInputEditTextEmail.getText())) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Email is required");
            return;
        } else if (!AuthActivity.isemailvalid(textInputEditTextEmail.getText().toString())) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Email is not valid");
            return;
        } else if (TextUtils.isEmpty(textInputEditTextPassword.getText())) {
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError("Password is required");
            return;
        } else if (!AuthActivity.ispasswordvalid(textInputEditTextPassword.getText().toString())) {
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError("Password is not valid. Password minimum 8 characters");
            return;
        } else if (TextUtils.isEmpty(textInputEditTextConfirmPassword.getText())) {
            textInputLayoutConfirmPassword.setErrorEnabled(true);
            textInputLayoutConfirmPassword.setError("Re-Password is required");
            return;
        } else if (!textInputEditTextPassword.getText().toString().equals(textInputEditTextConfirmPassword.getText().toString())) {
            textInputLayoutConfirmPassword.setErrorEnabled(true);
            textInputLayoutConfirmPassword.setError("Password not match");
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            layoutaddress.setErrorEnabled(true);
            layoutaddress.setError("Address is required");
            return;
        } else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphonenumber.setErrorEnabled(true);
            layoutphonenumber.setError("Phone number is required");
            return;
        } else if(camera.getDrawable().equals(myDrawable)){
            Snackbar.make(linearLayout, "Please insert your photo", Snackbar.LENGTH_LONG).show();
            return;
    }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();

        MediaType text = MediaType.parse("text/plain");

        File file = new File(mediaPath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody requestEmail = RequestBody.create(text, textInputEditTextEmail.getText().toString());
        RequestBody requestPassword = RequestBody.create(text, textInputEditTextPassword.getText().toString());
        RequestBody requestName = RequestBody.create(text, textInputEditTextName.getText().toString());
        RequestBody requestAddress = RequestBody.create(text, address.getText().toString());
        RequestBody requestPhoneNumber = RequestBody.create(text, phonenumber.getText().toString());

        service = apiUtils.getAPIService();
        service.registerRequest(requestEmail, requestPassword, requestName, body, requestAddress, requestPhoneNumber).
            enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Log.i("debug", "onResponse: SUCCESS");
                        try{
                            JSONObject jsonResults = new JSONObject(response.body().string());
                            if(jsonResults.getString("message").equals("User created successfully")){
                                String message = jsonResults.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(context, LoginUser.class));
                                finish();
                            }else{
                                String message = jsonResults.getString("message");
                                Snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
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
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    Toast.makeText(context, "There is a problem with upir connection", Toast.LENGTH_LONG).show();
                }
            });


    }

    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
    private void startPermissionsActivity(String[] permission) {
        PermissionActivity.startActivityForResult(this, 0, permission);
    }
}

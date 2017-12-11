package com.example.asus.pikachise.view.authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.User;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.home.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUser extends AppCompatActivity{
    private static final String TOKEN = "TOKEN";

    @BindView(R.id.loginuser_Email) TextInputEditText email;
    @BindView(R.id.loginuser_layoutEmail) TextInputLayout layoutemail;
    @BindView(R.id.loginuser_Password) TextInputEditText password;
    @BindView(R.id.loginuser_layoutPassword) TextInputLayout layoutpassword;
    @BindView(R.id.loginuser_createaccount) TextView createaccount;
    @BindView(R.id.loginuser_login)  Button login;
    @BindView(R.id.userlogin_container) LinearLayout container;

    private SessionManagement session;
    Typeface typeface;
    apiService service;
    Context context;
    private String token;
    private User user;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        initObjects();
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        onClickListener();
    }
    private void onClickListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToAPI();
            }
        });
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(intentRegister);
                finish();
            }
        });

    }
    private void initObjects() {
        ButterKnife.bind(this);
        context = this;
        session = new SessionManagement(getApplicationContext());
        progressDialog = new ProgressDialog(this);
    }
    private void postDataToAPI(){
        layoutemail.setErrorEnabled(false);
        layoutpassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(email.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is required");
            return;
        }else if (TextUtils.isEmpty(password.getText())) {
            layoutpassword.setErrorEnabled(true);
            layoutpassword.setError("Password is required");
            return;
        }
        progressDialog.setMessage("Wait a sec..");
        progressDialog.show();
        api_login();
    }
    private void api_login(){
        service = apiUtils.getAPIService();
        service.loginRequest(email.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                token = jsonResults.getString("token");
                                if(token.equals("")){
                                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                                }else{
                                    Log.i("debug", "onResponse: Congratulations");
//                                    intent.putExtra(TOKEN, jsonResults.getString("token"));
//                                    session.createLoginSession(token);
                                    api_user(token);
//                                    session.createLoginSession(user);
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            Snackbar.make(container, "Invalid Email or Password", Snackbar.LENGTH_LONG).show();
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

    private void api_user(final String tokens){
        service = apiUtils.getAPIService();
        service.userRequest(tokens).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                session.createLoginSession(jsonResults.getJSONObject("result").getInt("id"),
                                        jsonResults.getJSONObject("result").getString("name"),
                                        jsonResults.getJSONObject("result").getString("email"),
                                        jsonResults.getJSONObject("result").getString("image"),
                                        jsonResults.getJSONObject("result").getString("address"),
                                        jsonResults.getJSONObject("result").getString("phone_number"),
                                        tokens
                                        );
                                intent.putExtra(TOKEN, tokens);
                                startActivity(intent);
                                finish();
                                String nama = jsonResults.getJSONObject("result").getString("name");
                                Toast.makeText(context, "Hey "+nama+", Good to see you :)", Toast.LENGTH_LONG).show();
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
//        Call<UserResponse> call = service.userRequest(tokens);
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if(response.code() == 200) {
//                    User user = response.body().getUser();
//                    Log.i("debug", "onResponse: SUCCESS");
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                        JSONObject jsonResults = new JSONObject(response.body().string());
//
////                        session.createLoginSession(jsonResults.getJSONObject("result").getInt("id"),
////                                jsonResults.getJSONObject("result").getString("name"),
////                                jsonResults.getJSONObject("result").getString("email"),
////                                jsonResults.getJSONObject("result").getString("image"),
////                                jsonResults.getJSONObject("result").getString("address"),
////                                jsonResults.getJSONObject("result").getString("address"),
////                                tokens
////                                );
//                        session.createLoginSession(user);
//                        finish();
//                        startActivity(intent);
//                        Toast.makeText(context, "SUCCESS", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
//                Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
     }
//    private void verifyFromSQLite() {
//        layoutemail.setErrorEnabled(false);
//        layoutpassword.setErrorEnabled(false);
//        if (TextUtils.isEmpty(email.getText())) {
//            layoutemail.setErrorEnabled(true);
//            layoutemail.setError("Email is required");
//            return;
//        } else if (!AuthActivity.isemailvalid(email.getText().toString())) {
//            layoutemail.setErrorEnabled(true);
//            layoutemail.setError("Email is not valid.");
//            return;
//        } else if (TextUtils.isEmpty(password.getText())) {
//            layoutpassword.setErrorEnabled(true);
//            layoutpassword.setError("Password is required");
//            return;
//        }
//
//        if (databaseHelper.checkUser(email.getText().toString().trim()
//                , password.getText().toString().trim())) {
//            session = new SessionManagement(getApplicationContext());
//            User _user = databaseHelper.getUser(email.getText().toString().trim());
//            session.createLoginSession(_user.getName(), email.getText().toString().trim());
//            emptyInputEditText();
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            finish();
//            startActivity(i);
//        } else {
//            Snackbar.make(container, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
//        }
//    }
    private void emptyInputEditText() {
        email.setText(null);
        password.setText(null);
    }
}

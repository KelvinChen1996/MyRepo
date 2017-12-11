package com.example.asus.pikachise.view.authentication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.home.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    @BindView(R.id.changepassword_confirmnewpassword) TextInputEditText confirmnewpassword;
    @BindView(R.id.changepassword_layoutconfirmnewpassword) TextInputLayout layoutconfirmnewpassword;
    @BindView(R.id.changepassword_layoutnewpassword) TextInputLayout layoutnewpassword;
    @BindView(R.id.changepassword_layoutoldpassword) TextInputLayout layoutoldpassword;
    @BindView(R.id.changepassword_newpassword) TextInputEditText newpassword;
    @BindView(R.id.changepassword_oldpassword) TextInputEditText oldpassword;
    @BindView(R.id.changepassword_toolbar)
    Toolbar toolbar;
    @BindView(R.id.changepassword_container) LinearLayout container;
    apiService service;
    Context context;
    SessionManagement session;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initObject();
        initDataSession();
        setupToolbar();
    }
    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
        service = apiUtils.getAPIService();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void initGoHome(){
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
        EditUser.edituserActivity.finish();
        finish();
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
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void responseAPI401(){
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you sure to save it ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changePassword();
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
    private void changePassword(){
        layoutoldpassword.setErrorEnabled(false);
        layoutnewpassword.setErrorEnabled(false);
        layoutconfirmnewpassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(oldpassword.getText())) {
            layoutoldpassword.setErrorEnabled(true);
            layoutoldpassword.setError("Old password is required");
            return;
        } else if (TextUtils.isEmpty(newpassword.getText())) {
            layoutnewpassword.setErrorEnabled(true);
            layoutnewpassword.setError("Password is required");
            return;
        } else if (!AuthActivity.ispasswordvalid(newpassword.getText().toString())) {
            layoutnewpassword.setErrorEnabled(true);
            layoutnewpassword.setError("Password is not valid. Password minimum 8 characters");
            return;
        } else if (TextUtils.isEmpty(confirmnewpassword.getText())) {
            layoutconfirmnewpassword.setErrorEnabled(true);
            layoutconfirmnewpassword.setError("Re-Password is required");
            return;
        } else if (!AuthActivity.ispasswordvalid(confirmnewpassword.getText().toString())) {
            layoutconfirmnewpassword.setErrorEnabled(true);
            layoutconfirmnewpassword.setError("Password is not valid. Password minimum 8 characters");
            return;
        } else if (!newpassword.getText().toString().equals(confirmnewpassword.getText().toString())) {
            layoutconfirmnewpassword.setErrorEnabled(true);
            layoutconfirmnewpassword.setError("Password not match");
            return;
        }
        service.changePassword(token,oldpassword.getText().toString(), newpassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==401){
                                    Snackbar.make(container, "Your old password is wrong", Snackbar.LENGTH_SHORT).show();
                        }
                        else if(response.code()==200){
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("Password changed successfully")){
                                    String pesan = jsonResults.getString("message");
                                    Snackbar.make(container, pesan, Snackbar.LENGTH_SHORT).show();
                                    initGoHome();
                                }else{
                                    String pesan = jsonResults.getString("message");
                                    Snackbar.make(container, pesan, Snackbar.LENGTH_SHORT).show();
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
}

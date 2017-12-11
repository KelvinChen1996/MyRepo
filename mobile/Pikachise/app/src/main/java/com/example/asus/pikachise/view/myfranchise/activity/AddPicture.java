package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
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

import java.io.File;
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

public class AddPicture extends AppCompatActivity {
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    private final static String GOTOPICTURE = "GOTOPICTURE";

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
    private int extravalue;
    @BindView(R.id.addpicture_toolbar) Toolbar toolbar;
    @BindView(R.id.addpicture_imageview) ImageView imageView;
    @BindView(R.id.addpicture_container) LinearLayout container;
    private File fileTDP, fileSIUP, fileSURATPERJANJIAN, fileSTPW, fileKTP, fileCOMPANYPROFILE, fileLAPORANKEUANGAN, fileSURATIZINTEKNIK, fileTDPF;
    private RequestBody requestFileTDP, requestFileSIUP, requestFileSURATPERJANJIAN, requestFileSTPW, requestFileKTP, requestFileCOMPANYPROFILE, requestFileLAPORANKEUANGAN, requestFileSURATIZINTEKNIK, requestFileTDPF;
    private MultipartBody.Part bodyTDP, bodySIUP, bodySURATPERJANJIAN, bodySTPW, bodyKTP, bodyCOMPANYPROFILE, bodyLAPORANKEUANGAN, bodySURATIZINTEKNIK, bodyTDPF;

    private PermissionsChecker checker;
    private Context context;
    private apiService service;
    private SessionManagement session;
    private Drawable bannerdrawable;
    private AlertDialog dialog;
    private String token, mediapath;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        initObject();
        initGetIntent();
        setupToolbar();
        initSetPicture();
        initSetOnClickListener();
    }
    private void initSetOnClickListener(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUploadGallery(100);
            }
        });
    }
    private void initObject(){
        ButterKnife.bind(this);
        context = this;
        checker = new PermissionsChecker(this);
        service = apiUtils.getAPIService();
        progressDialog = new ProgressDialog(this);
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.x1);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(extravalue == 0){
            getSupportActionBar().setTitle("Upload Brochure");
        }else if(extravalue == 1){
            getSupportActionBar().setTitle("Upload TDP");
        }else if(extravalue == 2){
            getSupportActionBar().setTitle("Upload SIUP");
        }else if(extravalue == 3){
            getSupportActionBar().setTitle("Upload SP");
        }else if(extravalue == 4){
            getSupportActionBar().setTitle("Upload STPW");
        }else if(extravalue == 5){
            getSupportActionBar().setTitle("Upload KTP");
        }else if(extravalue == 6){
            getSupportActionBar().setTitle("Upload C.Profile");
        }else if(extravalue == 7){
            getSupportActionBar().setTitle("Upload L.Keuangan");
        }else if(extravalue == 8){
            getSupportActionBar().setTitle("Upload SIT");
        }else if(extravalue == 9){
            getSupportActionBar().setTitle("Upload TDPF");
        }
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
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you sure to save changes ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(extravalue == 0){
                    addBrochuretoApi();
                }
                else if(extravalue == 1){
                    add_TDP_toAPI();
                }
                else if(extravalue == 2){
                    add_SIUP_toAPI();
                }
                else if(extravalue == 3){
                    add_Suratperjanjian_toAPI();
                }
                else if(extravalue == 4){
                    add_STPW_toAPI();
                }
                else if(extravalue == 5){
                    add_KTP_toAPI();
                }
                else if(extravalue == 6){
                    add_COMPANYPROFILE_toAPI();
                }
                else if(extravalue == 7){
                    add_LAPORANKEUANGAN_toAPI();
                }
                else if(extravalue == 8){
                    add_SURATIZINTEKNIK_toAPI();
                }
                else if(extravalue == 9){
                    add_TDPF_toAPI();
                }
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
    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(GOTOPICTURE)){
            extravalue = getintent.getExtras().getInt(GOTOPICTURE);
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
    private void initSetPicture(){
        bannerdrawable = getResources().getDrawable(R.drawable.addpicture);
        imageView.setImageDrawable(bannerdrawable);
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
                            .into(imageView);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){}
    }

    private void addBrochuretoApi(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        File fileBrochure = new File(mediapath);
        RequestBody requestFileBrochure = RequestBody.create(MediaType.parse("multipart/form-data"), fileBrochure);
        MultipartBody.Part bodyBrochure = MultipartBody.Part.createFormData("brochure", fileBrochure.getName(), requestFileBrochure);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.addBrochureRequest(request_franchiseid, request_token, bodyBrochure)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });
    }
    private void add_TDP_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        File fileTDP = new File(mediapath);
        requestFileTDP = RequestBody.create(MediaType.parse("multipart/form-data"), fileTDP);
        bodyTDP = MultipartBody.Part.createFormData("tdp", fileTDP.getName(), requestFileTDP);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadTDP(request_franchiseid, bodyTDP, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });
    }
    private void add_SIUP_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        fileSIUP = new File(mediapath);
        requestFileSIUP = RequestBody.create(MediaType.parse("multipart/form-data"), fileSIUP);
        bodySIUP = MultipartBody.Part.createFormData("siup", fileSIUP.getName(), requestFileSIUP);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadSIUP(request_franchiseid, bodySIUP, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });
    }
    private void add_Suratperjanjian_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");

        fileSURATPERJANJIAN = new File(mediapath);
        requestFileSURATPERJANJIAN = RequestBody.create(MediaType.parse("multipart/form-data"), fileSURATPERJANJIAN);
        bodySURATPERJANJIAN = MultipartBody.Part.createFormData("suratperjanjian", fileSURATPERJANJIAN.getName(), requestFileSURATPERJANJIAN);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadSuratPerjanjian(request_franchiseid, bodySURATPERJANJIAN, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void add_STPW_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        fileSTPW = new File(mediapath);
        requestFileSTPW = RequestBody.create(MediaType.parse("multipart/form-data"), fileSTPW);
        bodySTPW = MultipartBody.Part.createFormData("stpw", fileSTPW.getName(), requestFileSTPW);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadSTPW(request_franchiseid, bodySTPW, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void add_KTP_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        fileKTP = new File(mediapath);
        requestFileKTP = RequestBody.create(MediaType.parse("multipart/form-data"), fileKTP);
        bodyKTP = MultipartBody.Part.createFormData("ktpfranchisor", fileKTP.getName(), requestFileKTP);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadKTP(request_franchiseid, bodyKTP, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void add_LAPORANKEUANGAN_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        fileLAPORANKEUANGAN = new File(mediapath);
        requestFileLAPORANKEUANGAN = RequestBody.create(MediaType.parse("multipart/form-data"), fileLAPORANKEUANGAN);
        bodyLAPORANKEUANGAN = MultipartBody.Part.createFormData("laporankeuangan2tahunterakhir", fileLAPORANKEUANGAN.getName(), requestFileLAPORANKEUANGAN);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadLaporanKeuangan(request_franchiseid, bodyLAPORANKEUANGAN, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void add_COMPANYPROFILE_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        fileCOMPANYPROFILE = new File(mediapath);
        requestFileCOMPANYPROFILE = RequestBody.create(MediaType.parse("multipart/form-data"), fileCOMPANYPROFILE);
        bodyCOMPANYPROFILE = MultipartBody.Part.createFormData("companyprofile", fileCOMPANYPROFILE.getName(), requestFileCOMPANYPROFILE);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadCompanyProfile(request_franchiseid, bodyCOMPANYPROFILE, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void add_SURATIZINTEKNIK_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        fileSURATIZINTEKNIK = new File(mediapath);
        requestFileSURATIZINTEKNIK = RequestBody.create(MediaType.parse("multipart/form-data"), fileSURATIZINTEKNIK);
        bodySURATIZINTEKNIK = MultipartBody.Part.createFormData("suratizinteknis", fileSURATIZINTEKNIK.getName(), requestFileSURATIZINTEKNIK);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadSuratIzinTeknik(request_franchiseid, bodySURATIZINTEKNIK, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void add_TDPF_toAPI(){
        if(imageView.getDrawable().equals(bannerdrawable)){
            Snackbar.make(container, "Please insert your brochure", Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MediaType text = MediaType.parse("text/plain");
        fileTDPF = new File(mediapath);
        requestFileTDPF = RequestBody.create(MediaType.parse("multipart/form-data"), fileTDPF);
        bodyTDPF = MultipartBody.Part.createFormData("tandabuktipendaftaran", fileTDPF.getName(), requestFileTDPF);
        RequestBody request_franchiseid = RequestBody.create(text, extrafranchiseid);
        RequestBody request_token = RequestBody.create(text, token);
        service.uploadTDPF(request_franchiseid, bodyTDPF, request_token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 401){
                            error401();
                        }
                        else if(response.code() == 200){
                            success();
                        }
                        else {
                            error();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        failure(t);
                    }
                });

    }
    private void error401(){
        progressDialog.dismiss();
        startActivity(new Intent(context, Error401.class));
        finish();
    }
    private void success(){
        progressDialog.dismiss();
        Log.i("debug", "onResponse: SUCCESS");
        Toast.makeText(context, "Success, please pull down to refresh", Toast.LENGTH_LONG).show();
        onBackPressed();
        finish();
    }
    private void error(){
        Log.i("debug", "onResponse: FAILED");
        Toast.makeText(context, "Whoops, something wrong", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
    private void failure(Throwable t){
        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
        Toast.makeText(context, "There is a problem with internet connection", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

}

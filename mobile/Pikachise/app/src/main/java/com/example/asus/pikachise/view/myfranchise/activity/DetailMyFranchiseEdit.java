package com.example.asus.pikachise.view.myfranchise.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.helper.FinancialTextWatcher;
import com.example.asus.pikachise.presenter.helper.PermissionActivity;
import com.example.asus.pikachise.presenter.helper.PermissionsChecker;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.home.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailMyFranchiseEdit extends AppCompatActivity {

    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int SELECT_LOGO = 1;
    private static final int SELECT_BANNER = 2;

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

    @BindView(R.id.editdetailmyfranchise_address) TextInputEditText address;
    @BindView(R.id.editdetailmyfranchise_banner) ImageView banner;
    @BindView(R.id.editdetailmyfranchise_category) Spinner category;
    @BindView(R.id.editdetailmyfranchise_email) TextInputEditText email;
    @BindView(R.id.editdetailmyfranchise_layoutaddress) TextInputLayout layoutaddress;
    @BindView(R.id.editdetailmyfranchise_layoutemail) TextInputLayout layoutemail;
    @BindView(R.id.editdetailmyfranchise_layoutname) TextInputLayout layoutname;
    @BindView(R.id.editdetailmyfranchise_layoutphonenumber) TextInputLayout layoutphone;
    @BindView(R.id.editdetailmyfranchise_layoutwebsite) TextInputLayout layoutwebsite;
    @BindView(R.id.editdetailmyfranchise_logo) CircleImageView logo;
    @BindView(R.id.editdetailmyfranchise_name) TextInputEditText name;
    @BindView(R.id.editdetailmyfranchise_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.editdetailmyfranchise_toolbar) Toolbar toolbar;
    @BindView(R.id.editdetailmyfranchise_type) Spinner type;
    @BindView(R.id.editdetailmyfranchise_website) TextInputEditText website;
    @BindView(R.id.editdetailmyfranchise_container) LinearLayout container;
    @BindView(R.id.editdetailmyfranchise_since) LinearLayout sinces;
    @BindView(R.id.editdetailmyfranchise_tvsince) TextView tvsince;
    @BindView(R.id.editdetailmyfranchise_franchisefee) TextInputEditText franchisefee;
    @BindView(R.id.editdetailmyfranchise_layoutfranchisefee) TextInputLayout layoutfranchisefee;
    @BindView(R.id.editdetailmyfranchise_country) Spinner country;
    @BindView(R.id.editdetailmyfranchise_layoutdetail) TextInputLayout layoutdetail;
    @BindView(R.id.editdetailmyfranchise_detail) TextInputEditText detail;
    @BindView(R.id.editdetailmyfranchise_layoutinvestment) TextInputLayout layoutinvestmens;
    @BindView(R.id.editdetailmyfranchise_investment) TextInputEditText investments;
    private String extraname, extralogo, extrabanner, extraemail, extrauserid, extrafranchiseid, extracategory, extratype, extraestablishsince, extrainvesment, extrafranchisefee, extrawebsite, extraaddress, extralocation, extraphonenumber, extradetail;
    private String choosencategory, choosentype, choosencountry;
    private String mediaPathLogo,mediaPathBanner;
    private ProgressDialog progressDialog;
    SessionManagement session;
    Context context;
    private AlertDialog dialog;

    private Drawable logodrawable, bannerdrawable;

    private PermissionsChecker checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_my_franchise);
        initObject();
        setupToolbar();
        initGetIntent();
        initDetail();
        franchisefee.addTextChangedListener(new FinancialTextWatcher(franchisefee));
        investments.addTextChangedListener(new FinancialTextWatcher(investments));
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
        initSpinner();

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
    private void initSpinner(){
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

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosencountry = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    private void initDetail(){
        Picasso.with(context)
                .load(extrabanner)
                .placeholder(R.drawable.logo404)
                .into(banner);
        Picasso.with(context)
                .load(extralogo)
                .placeholder(R.drawable.logo404)
                .into(logo);
        name.setText(extraname);
        String compareCategory = extracategory;
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.categoryspinner, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapterCategory);
        if (!compareCategory.equals(null)) {
            int spinnerPosition = adapterCategory.getPosition(compareCategory);
            category.setSelection(spinnerPosition);
        }
        String compareType = extratype;
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.typespinner, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);
        if (!compareType.equals(null)) {
            int spinnerPosition = adapterType.getPosition(compareType);
            type.setSelection(spinnerPosition);
        }
        tvsince.setText(extraestablishsince);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        investments.setText(formatter.format(Double.parseDouble(extrainvesment)));
        franchisefee.setText(formatter.format(Double.parseDouble(extrafranchisefee)));
        address.setText(extraaddress);
        String compareCountry = extralocation;
        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this, R.array.countryspinner, android.R.layout.simple_spinner_item);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapterCountry);
        if (!compareCountry.equals(null)) {
            int spinnerPosition = adapterCountry.getPosition(compareCountry);
            country.setSelection(spinnerPosition);
        }
        phonenumber.setText(extraphonenumber);
        email.setText(extraemail);
        website.setText(extrawebsite);
        detail.setText(extradetail);
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

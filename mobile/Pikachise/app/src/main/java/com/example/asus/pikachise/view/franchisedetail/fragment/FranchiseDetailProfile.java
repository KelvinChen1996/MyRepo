package com.example.asus.pikachise.view.franchisedetail.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
import com.example.asus.pikachise.presenter.session.SessionManagement;

import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FranchiseDetailProfile extends Fragment{
    @BindView(R.id.franchisedetailprofile_address) TextView address;
    @BindView(R.id.franchisedetailprofile_category) TextView category;
    @BindView(R.id.franchisedetailprofile_container) LinearLayout container;
    @BindView(R.id.franchisedetailprofile_deskripsi) TextView deskripsi;
    @BindView(R.id.franchisedetailprofile_email) TextView email;
    @BindView(R.id.franchisedetailprofile_franchise_name) TextView franchisename;
    @BindView(R.id.franchisedetailprofile_franchisefee) TextView franchisefee;
    @BindView(R.id.franchisedetailprofile_imagecategory) ImageView imagecategory;
    @BindView(R.id.franchisedetailprofile_investment) TextView invesment;
    @BindView(R.id.franchisedetailprofile_location) TextView location;
    @BindView(R.id.franchisedetailprofile_phonenumber) TextView phonenumber;
    @BindView(R.id.franchisedetailprofile_since) TextView since;
    @BindView(R.id.franchisedetailprofile_type) TextView type;
    @BindView(R.id.franchisedetailprofile_website) TextView website;


    private SessionManagement session;
    private apiService service;
    private Context context;
    private String token;

    private final static String NAME = "NAME";
    private final static String LOGO = "LOGO";
    private final static String BANNER = "BANNER";
    private final static String EMAIL = "EMAIL";
    private final static String AVERAGE_RATING = "AVERAGE_RATING";
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
    public FranchiseDetailProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_franchise_detail_profile, container, false);
        initObject(view);
        initDataSession();
        getBundleData();
        setData();
        return view;
    }
    private void initObject(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        session = new SessionManagement(context.getApplicationContext());
        service = apiUtils.getAPIService();
    }

    private void initDataSession(){
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManagement.USER_TOKEN);
    }

    private void getBundleData(){
        Bundle args = getArguments();
        if(args.getString(FRANCHISE_ID) != null){
            extraname = args.getString(NAME);
            extralogo = args.getString(LOGO);
            extrabanner = args.getString(BANNER);
            extraemail = args.getString(EMAIL);
            extrafranchiseid = args.getString(FRANCHISE_ID);
            extracategory = args.getString(CATEGORY);
            extratype = args.getString(TYPE);
            extraestablishsince = args.getString(ESTABLISHSINCE);
            extrainvesment = args.getString(INVESTMENT);
            extrafranchisefee = args.getString(FRANCHISEFEE);
            extrawebsite = args.getString(WEBSITE);
            extraaddress = args.getString(ADDRESS);
            extralocation = args.getString(LOCATION);
            extraphonenumber = args.getString(PHONENUMBER);
            extradetail = args.getString(DETAIL);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void setData(){
        franchisename.setText(extraname);
        if(extracategory.toLowerCase().equals("automotive")){
            imagecategory.setImageResource(R.drawable.automotive_category);
        }
        if(extracategory.toLowerCase().equals("beauty")){
            imagecategory.setImageResource(R.drawable.beauty_category);
        }
        if(extracategory.toLowerCase().equals("cleaning")){
            imagecategory.setImageResource(R.drawable.cleaning_category);
        }
        if(extracategory.toLowerCase().equals("education")){
            imagecategory.setImageResource(R.drawable.education_category);
        }
        if(extracategory.toLowerCase().equals("food")){
            imagecategory.setImageResource(R.drawable.food_category);
        }
        if(extracategory.toLowerCase().equals("hotel")){
            imagecategory.setImageResource(R.drawable.hotel_category);
        }
        if(extracategory.toLowerCase().equals("maintenance")){
            imagecategory.setImageResource(R.drawable.maintenance_category);
        }
        if(extracategory.toLowerCase().equals("medic")){
            imagecategory.setImageResource(R.drawable.medic_category);
        }
        if(extracategory.toLowerCase().equals("pet service")){
            imagecategory.setImageResource(R.drawable.pet_category);
        }
        if(extracategory.toLowerCase().equals("retail")){
            imagecategory.setImageResource(R.drawable.retail_category);
        }
        if(extracategory.toLowerCase().equals("technology")){
            imagecategory.setImageResource(R.drawable.tech_category);
        }
        if(extracategory.toLowerCase().equals("travel")){
            imagecategory.setImageResource(R.drawable.travel_category);
        }
        category.setText(extracategory);
        type.setText(extratype);
        since.setText(extraestablishsince);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        invesment.setText("IDR" + formatter.format(Double.parseDouble(extrainvesment)));
        franchisefee.setText("IDR " + formatter.format(Double.parseDouble(extrafranchisefee)));
        location.setText(extralocation);
        address.setText(extraaddress);
        phonenumber.setText(extraphonenumber);
        website.setText(extrawebsite);
        deskripsi.setText(extradetail);
        email.setText(extraemail);
    }

}

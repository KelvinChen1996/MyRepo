package com.example.asus.pikachise.view.home.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.view.home.adapter.UserHomeVPAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean doubleBackToExitPressedOnce = false;

    public UserHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, null);
        initView(view);
        viewPager.setAdapter(new UserHomeVPAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    private void initView(View v){
        tabLayout = (TabLayout) v.findViewById(R.id.userhome_tabs);
        viewPager = (ViewPager) v.findViewById(R.id.userhome_viewpager);
    }


}

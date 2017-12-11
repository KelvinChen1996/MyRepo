package com.example.asus.pikachise.view.franchisedetail.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.asus.pikachise.view.franchisedetail.fragment.FranchiseDetailBrochures;
import com.example.asus.pikachise.view.franchisedetail.fragment.FranchiseDetailDaftarOutlet;
import com.example.asus.pikachise.view.franchisedetail.fragment.FranchiseDetailProfile;
import com.example.asus.pikachise.view.franchisedetail.fragment.FranchiseDetailReviewandRating;

/**
 * Created by Asus on 20/09/2017.
 */

public class DetailFranchiseVPAdapter extends FragmentPagerAdapter {
    public static int int_items = 4;
    private final Bundle fragmentbundle;

    public DetailFranchiseVPAdapter(FragmentManager fm, Bundle data) {
        super(fm);
        fragmentbundle = data;
    }

    /**
     * Return fragment with respect to Position .
     */

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                final Fragment f = new FranchiseDetailBrochures();
                f.setArguments(this.fragmentbundle);
                return f;
            case 1:
                final Fragment g = new FranchiseDetailProfile();
                g.setArguments(this.fragmentbundle);
                return g;
            case 2:
                final Fragment h = new FranchiseDetailDaftarOutlet();
                h.setArguments(this.fragmentbundle);
                return h;
            case 3:

                final Fragment i = new FranchiseDetailReviewandRating();
                i.setArguments(this.fragmentbundle);
                return i;
        }
        return null;
    }

    @Override
    public int getCount() {
        return int_items;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Brochures";
            case 1:
                return "Profile";
            case 2:
                return "Outlet List";
            case 3:
                return "Review";
        }
        return null;
    }
}

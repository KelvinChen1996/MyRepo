package com.example.asus.pikachise.view.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.pikachise.view.event.fragment.EventFragment;
import com.example.asus.pikachise.view.home.homenew.fragment.NewFragment;
import com.example.asus.pikachise.view.home.homehot.fragment.HotFragment;

/**
 * Created by Asus on 17/09/2017.
 */

public class UserHomeVPAdapter extends FragmentPagerAdapter {
    public static int pages = 3;
    public UserHomeVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HotFragment();
            case 1:
                return new NewFragment();
            case 2:
                return new EventFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return pages;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Hot";
            case 1:
                return "New";
            case 2:
                return "Event";
        }
        return null;
    }
}

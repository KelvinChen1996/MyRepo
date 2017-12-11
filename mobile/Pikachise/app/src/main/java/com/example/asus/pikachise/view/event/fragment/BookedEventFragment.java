package com.example.asus.pikachise.view.event.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.pikachise.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookedEventFragment extends Fragment {


    public BookedEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booked_event, container, false);
    }

}

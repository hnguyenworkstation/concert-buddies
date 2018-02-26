package com.app.concertbud.concertbuddies.ViewFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.concertbud.concertbuddies.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocateEventFragment extends Fragment {
    public LocateEventFragment() {
        // Required empty public constructor
    }

    public static LocateEventFragment newInstance() {
        LocateEventFragment fragment = new LocateEventFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locate_event, container, false);
    }

}

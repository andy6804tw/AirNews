package com.openweather.airnews.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.openweather.airnews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_page extends Fragment {

    private ImageView imageView;

    public fragment_page() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_fragment_page, container, false);

        return view;
    }


}

package com.openweather.airnews.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.openweather.airnews.Main2Activity;
import com.openweather.airnews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_page extends Fragment {

    private ImageView imageView;

    public fragment_page() {
        // Required empty public constructor
    }
    Button btn;
    TextView t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_fragment_page, container, false);
        t=(TextView)view.findViewById(R.id.textView);
        t.setText("1235");
        btn=(Button)view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"!!!!!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(),Main2Activity.class));
            }
        });

        return view;
    }


}

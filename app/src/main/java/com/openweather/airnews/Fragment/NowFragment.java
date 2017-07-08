package com.openweather.airnews.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openweather.airnews.DataBase.DBAccess;
import com.openweather.airnews.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowFragment extends Fragment {

    private DBAccess mAccess;
    public NowFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_now, container, false);
        mAccess = new DBAccess(getContext(), "weather", null,1);
        Cursor c = mAccess.getData("Location", null, null);
        c.moveToFirst();
        Log.e("Loc",c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)+" "+c.getString(5)+" "+c.getString(6));


        return view;
    }

}

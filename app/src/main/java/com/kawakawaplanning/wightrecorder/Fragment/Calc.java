package com.kawakawaplanning.wightrecorder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kawakawaplanning.wightrecorder.R;

/**
 * Created by KP on 2015/03/25.
 */
public class Calc extends Fragment{

    public Calc() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calc, container, false);
        return v;
    }
}

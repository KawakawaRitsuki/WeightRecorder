package com.kawakawaplanning.wightrecorder;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager vp = (ViewPager)findViewById(R.id.mypager);//定義

        vp.setAdapter(new PAdapter(getSupportFragmentManager()));//アダプタ入れる

        PagerTabStrip pts = (PagerTabStrip)findViewById(R.id.pagertabstrip);

        pts.setDrawFullUnderline(true);
    }

}
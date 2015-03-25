package com.kawakawaplanning.wightrecorder;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = (ViewPager)findViewById(R.id.mypager);//定義

        vp.setAdapter(new PAdapter(this.getSupportFragmentManager()));//アダプタ入れる

        PagerTabStrip pts = (PagerTabStrip)findViewById(R.id.pagertabstrip);

        pts.setDrawFullUnderline(true);
    }

}
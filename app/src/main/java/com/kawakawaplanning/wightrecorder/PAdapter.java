package com.kawakawaplanning.wightrecorder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kawakawaplanning.wightrecorder.Fragment.Calc;
import com.kawakawaplanning.wightrecorder.Fragment.Graph;
import com.kawakawaplanning.wightrecorder.Fragment.List;
import com.kawakawaplanning.wightrecorder.Fragment.Record;



/**
 * Created by KP on 2015/03/25.
 */
public class PAdapter extends FragmentPagerAdapter {

    public PAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:return new Record();
            case 1:return new List();
            case 2:return new Graph();
            case 3:return new Calc();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:return "記録";
            case 1:return "リスト";
            case 2:return "グラフ";
            case 3:return "計算";
        }
        return null;
    }
}

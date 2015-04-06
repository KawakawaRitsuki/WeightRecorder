package com.kawakawaplanning.weightrecorder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kawakawaplanning.weightrecorder.Fragment.LifeListFragment;
import com.kawakawaplanning.weightrecorder.Fragment.MypageFragment;
import com.kawakawaplanning.weightrecorder.Fragment.PressGraphFragment;
import com.kawakawaplanning.weightrecorder.Fragment.RecordFragment;
import com.kawakawaplanning.weightrecorder.Fragment.WeightGraphFragment;


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
            case 0:return new LifeListFragment();
            case 1:return new RecordFragment();
            case 2:return new MypageFragment();
            case 3:return new WeightGraphFragment();
            case 4:return new PressGraphFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:return "リスト";
            case 1:return "記録";
            case 2:return "マイページ";
            case 3:return "体重グラフ";
            case 4:return "血圧グラフ";
        }
        return null;
    }
}

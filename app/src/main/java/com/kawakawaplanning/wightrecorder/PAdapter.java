package com.kawakawaplanning.wightrecorder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kawakawaplanning.wightrecorder.Fragment.GraphFragment;
import com.kawakawaplanning.wightrecorder.Fragment.LifeListFragment;
import com.kawakawaplanning.wightrecorder.Fragment.MypageFragment;
import com.kawakawaplanning.wightrecorder.Fragment.RecordFragment;


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
            case 0:return new RecordFragment();
            case 1:return new MypageFragment();
            case 2:return new LifeListFragment();
            case 3:return new GraphFragment();
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
            case 1:return "マイページ";
            case 2:return "リスト";
            case 3:return "グラフ";
        }
        return null;
    }
}

package com.kawakawaplanning.wightrecorder.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.baoyz.widget.PullRefreshLayout;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.kawakawaplanning.wightrecorder.LifeItem;
import com.kawakawaplanning.wightrecorder.R;

import java.util.List;

/**
 * Created by KP on 2015/03/25.
 */
public class MypageFragment extends Fragment implements View.OnClickListener{

    PieGraph graph;
    TextView wigTv;
    TextView fatTv;
    TextView haiTv;
    TextView name1Tv;
    TextView name2Tv;
    int latistWight;
    int latistFat;
    PullRefreshLayout layout;

    public void assignViews(View v){
        graph = (PieGraph) v.findViewById(R.id.graph);
        wigTv = (TextView) v.findViewById(R.id.textView13);
        fatTv = (TextView) v.findViewById(R.id.textView15);
        haiTv = (TextView) v.findViewById(R.id.textView17);
        name1Tv = (TextView) v.findViewById(R.id.textView8);
        name2Tv = (TextView) v.findViewById(R.id.textView10);
        layout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
    }

    public MypageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mypage, container, false);
        assignViews(v);
        return v;

    }

    public void onResume(){
        super.onResume();



        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            // start refresh
            read();
            layout.setRefreshing(false);
            }
        });

        read();

    }

    public void read(){
        List<LifeItem> list = new Select().from(LifeItem.class).execute();

        for (LifeItem i : list) {
            latistWight = i.wight;
            latistFat = i.fat;
        }
        SharedPreferences data = getActivity().getSharedPreferences("HeightSave", Context.MODE_PRIVATE);
        int height = data.getInt("Height",0 );
        String name = data.getString("Name","No name");

        name1Tv.setText(name);
        name2Tv.setText(name);

        wigTv.setText(latistWight+ "kg");
        fatTv.setText(latistFat+"%");
        haiTv.setText(height+"cm");

        PieSlice slice = new PieSlice();
        graph.removeSlices();
        slice.setColor(Color.parseColor("#337575"));
        slice.setValue(latistFat);
        graph.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#23cbcb"));
        slice.setValue(100 - latistFat);
        graph.addSlice(slice);
    }


    @Override
    public void onClick(View v) {

    }


}

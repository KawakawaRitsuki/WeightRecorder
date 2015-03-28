package com.kawakawaplanning.wightrecorder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.baoyz.widget.PullRefreshLayout;
import com.kawakawaplanning.wightrecorder.LifeItem;
import com.kawakawaplanning.wightrecorder.R;

import java.util.List;

/**
 * Created by KP on 2015/03/25.
 */
public class LifeListFragment extends Fragment{

    private ListView mListView;
    private View view;
    PullRefreshLayout layout;

    private void assignViews(View v) {
        mListView = (ListView) v.findViewById(R.id.listView);
    }


    public LifeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list, container, false);
        assignViews(view);



        return view;
    }

    public void onStart(){
        super.onStart();

        List<LifeItem> list = new Select().from(LifeItem.class).execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

        for (LifeItem i : list) {
            adapter.add(i.day + ":" + "日付/"+i.wight + ":体重" + i.fat + ":体脂肪" + i.bmi + ":bmi" );
        }

        mListView.setAdapter(adapter);

        mListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                List<LifeItem> list = new Select().from(LifeItem.class).execute();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

                for (LifeItem i : list) {
                    adapter.add(i.day + ":" + "日付/"+i.wight + ":体重" + i.fat + ":体脂肪" + i.bmi + ":bmi" );
                }

                mListView.setAdapter(adapter);
                layout.setRefreshing(false);
            }
        });


    }
}

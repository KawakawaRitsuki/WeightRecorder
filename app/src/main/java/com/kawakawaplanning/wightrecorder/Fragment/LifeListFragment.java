package com.kawakawaplanning.wightrecorder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.kawakawaplanning.wightrecorder.LifeItem;
import com.kawakawaplanning.wightrecorder.R;

import java.util.List;

/**
 * Created by KP on 2015/03/25.
 */
public class LifeListFragment extends Fragment{

    private ListView mListView;

    private void assignViews(View v) {
        mListView = (ListView) v.findViewById(R.id.listView);
    }

    public LifeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        assignViews(v);
        return v;
    }

    public void onStart(){
        super.onStart();
        List<LifeItem> list = new Select().from(LifeItem.class).execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        for (LifeItem i : list) {
            adapter.add(i.day + ":" + "体重/"+i.wight);
        }
        mListView.setAdapter(adapter);
    }
}

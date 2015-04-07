package com.kawakawaplanning.weightrecorder.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.activeandroid.query.Select;
import com.baoyz.widget.PullRefreshLayout;
import com.kawakawaplanning.weightrecorder.LifeItem;
import com.kawakawaplanning.weightrecorder.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KP on 2015/03/25.
 */
public class LifeListFragment extends Fragment{

    public static ListView mListView;
    private View view;
    PullRefreshLayout layout;
    public static Activity act;
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

        act = getActivity();

        return view;
    }

    public void onStart(){
        super.onStart();

        Map<String, String> data;
        List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
        List<LifeItem> list = new Select().from(LifeItem.class).execute();
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

        for (LifeItem i : list) {
            data = new HashMap<String, String>();
            data.put("weight", "体重:" + i.weight + "Kg" + "　体脂肪:" + i.fat + "%");
            data.put("comment", "日付:" + i.day + "　BMI:" + i.bmi );
            retDataList.add(data);
//                    adapter.add("日付:" + i.day + "/体重:" + i.weight + "/体脂肪:" + i.fat + "\r/bmi:" + i.bmi   );
        }
        if(retDataList.isEmpty()){
            data = new HashMap<String, String>();
            data.put("weight", "データがありません。記録画面から登録してください。");
            data.put("comment","");

            retDataList.add(data);
        }
        SimpleAdapter adapter2 = new SimpleAdapter(act, retDataList,
                R.layout.raw, new String[] { "weight", "comment" },
                new int[] {R.id.textView1, R.id.textView2});
        mListView.setAdapter(adapter2);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "どうしたものか。。。\n" + position + "つ目を選択しました。", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                //ここに処理を書く
//                LifeItem item = LifeItem.load(LifeItem.class, position);
//                item.delete();
//                return false;
//            }
//        });

        layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                Map<String, String> data;
                List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
                List<LifeItem> list = new Select().from(LifeItem.class).execute();              for (LifeItem i : list) {
                    data = new HashMap<String, String>();
                    data.put("weight", "体重:" + i.weight + "Kg" + "　体脂肪:" + i.fat + "%");
                    data.put("comment", "日付:" + i.day + "　BMI:" + i.bmi );
                    retDataList.add(data);
                }
                if(retDataList.isEmpty()){
                    data = new HashMap<String, String>();
                    data.put("weight", "データがありません。記録画面から登録してください。");
                    data.put("comment","");
                   retDataList.add(data);
                }
                SimpleAdapter adapter2 = new SimpleAdapter(act, retDataList,
                        R.layout.raw, new String[] { "weight", "comment" },
                        new int[] {R.id.textView1, R.id.textView2});
                mListView.setAdapter(adapter2);
                layout.setRefreshing(false);
            }
        });


    }
}

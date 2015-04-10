package com.kawakawaplanning.weightrecorder.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
            data.put("day", i.day);
            data.put("weight", "　　体重:" + i.weight + "kg　体脂肪:" + i.fat + "%");
            data.put("pu", "　　血圧:" + i.puu + "/" + i.pud + "　BMI:" + i.bmi);
            data.put("id" , i.getId() + "");
            retDataList.add(data);
//                    adapter.add("日付:" + i.day + "/体重:" + i.weight + "/体脂肪:" + i.fat + "\r/bmi:" + i.bmi   );
        }
        if(retDataList.isEmpty()){
            data = new HashMap<String, String>();
            data.put("day", "データがありません。記録画面から登録してください。");
            data.put("weight","");
            data.put("pu","");
            data.put("id" , "");
            retDataList.add(data);
        }
        SimpleAdapter adapter2 = new SimpleAdapter(act, retDataList,
                R.layout.raw, new String[] { "day", "weight" ,"pu" ,"id"},
                new int[] {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textViewId});
        mListView.setAdapter(adapter2);


        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Object getitem = mListView.getItemAtPosition(pos);
                Log.v("kp", getitem.toString());
                String splStr[] = getitem.toString().split("=");
                String getStr[] = splStr[1].split(",");
                if(!getStr[0].isEmpty()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("確認");
                    alertDialogBuilder.setMessage("この項目を削除しますか？訂正しますか？");
                    alertDialogBuilder.setPositiveButton("削除",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Object item = mListView.getItemAtPosition(pos);
                                    Log.v("kp", item.toString());
                                    String splStr[] = item.toString().split("=");
                                    String getStr[] = splStr[1].split(",");
                                    int getId = Integer.parseInt(getStr[0]);
                                    LifeItem delItem = LifeItem.load(LifeItem.class, getId);
                                    delItem.delete();
                                    reload();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("訂正",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Object getitem = mListView.getItemAtPosition(pos);
                                    Log.v("kp", getitem.toString());
                                    String splStr[] = getitem.toString().split("=");
                                    String getStr[] = splStr[1].split(",");

                                    final int getId = Integer.parseInt(getStr[0]);
                                    LifeItem item = LifeItem.load(LifeItem.class, getId);

                                    AlertDialog.Builder alertDialogBuildername = new AlertDialog.Builder(getActivity());
                                    LayoutInflater inflatername = (LayoutInflater) getActivity().getSystemService(
                                            getActivity().LAYOUT_INFLATER_SERVICE);
                                    View viewname = inflatername.inflate(R.layout.editdialog,
                                            (ViewGroup) getActivity().findViewById(R.id.RootAreDg));

                                    final EditText AlertET1 = (EditText) viewname.findViewById(R.id.AlartET1);
                                    final EditText AlertET2 = (EditText) viewname.findViewById(R.id.AlartET2);
                                    final EditText AlertET3 = (EditText) viewname.findViewById(R.id.AlartET3);
                                    final EditText AlertET4 = (EditText) viewname.findViewById(R.id.AlartET4);
                                    AlertET1.setText(String.valueOf(item.weight));
                                    AlertET2.setText(String.valueOf(item.puu));
                                    AlertET3.setText(String.valueOf(item.fat));
                                    AlertET4.setText(String.valueOf(item.pud));
                                    alertDialogBuildername.setTitle("訂正");
                                    alertDialogBuildername.setView(viewname);
                                    alertDialogBuildername.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    LifeItem item = LifeItem.load(LifeItem.class, getId);
                                                    String e1 = AlertET1.getEditableText().toString();
                                                    String e2 = AlertET2.getEditableText().toString();
                                                    String e3 = AlertET3.getEditableText().toString();
                                                    String e4 = AlertET4.getEditableText().toString();

                                                    if (!e1.isEmpty()) {
                                                        item.weight = Float.parseFloat(e1);
                                                        item.bmi = RecordFragment.bmi(Float.parseFloat(e1));
                                                    }
                                                    if (!e2.isEmpty()) {
                                                        item.fat = Integer.parseInt(e3);
                                                    }
                                                    if (!e3.isEmpty()) {
                                                        item.puu = Integer.parseInt(e2);
                                                    }
                                                    if (!e4.isEmpty()) {
                                                        item.pud = Integer.parseInt(e4);
                                                    }
                                                    item.save();
                                                }
                                            });
                                    alertDialogBuildername.setCancelable(true);
                                    AlertDialog alertDialogname = alertDialogBuildername.create();
                                    alertDialogname.show();

                                }
                            });
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                return false;
            }
        });

        layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                Map<String, String> data;
                List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
                List<LifeItem> list = new Select().from(LifeItem.class).execute();
                for (LifeItem i : list) {
                    data = new HashMap<String, String>();
                    data.put("day", i.day);
                    data.put("weight", "　　体重:" + i.weight + "kg　体脂肪:" + i.fat + "%");
                    data.put("pu", "　　血圧:" + i.puu + "/" + i.pud + "　BMI:" + i.bmi);
                    data.put("id" , i.getId() + "");
                    retDataList.add(data);
//                    adapter.add("日付:" + i.day + "/体重:" + i.weight + "/体脂肪:" + i.fat + "\r/bmi:" + i.bmi   );
                }
                if(retDataList.isEmpty()){
                    data = new HashMap<String, String>();
                    data.put("day", "データがありません。記録画面から登録してください。");
                    data.put("weight","");
                    data.put("pu","");
                    data.put("id" , "");
                    retDataList.add(data);
                }
                SimpleAdapter adapter2 = new SimpleAdapter(act, retDataList,
                        R.layout.raw, new String[] { "day", "weight" ,"pu" ,"id"},
                        new int[] {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textViewId});
                mListView.setAdapter(adapter2);;
                layout.setRefreshing(false);
            }
        });


    }
    public void reload(){
        Map<String, String> data;
        List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
        List<LifeItem> list = new Select().from(LifeItem.class).execute();
        for (LifeItem i : list) {
            data = new HashMap<String, String>();
            data.put("day", i.day);
            data.put("weight", "　　体重:" + i.weight + "kg　体脂肪:" + i.fat + "%");
            data.put("pu", "　　血圧:" + i.puu + "/" + i.pud + "　BMI:" + i.bmi);
            data.put("id" , i.getId() + "");
            retDataList.add(data);
//                    adapter.add("日付:" + i.day + "/体重:" + i.weight + "/体脂肪:" + i.fat + "\r/bmi:" + i.bmi   );
        }
        if(retDataList.isEmpty()){
            data = new HashMap<String, String>();
            data.put("day", "データがありません。記録画面から登録してください。");
            data.put("weight","");
            data.put("pu","");
            data.put("id" , "");
            retDataList.add(data);
        }
        SimpleAdapter adapter2 = new SimpleAdapter(act, retDataList,
                R.layout.raw, new String[] { "day", "weight" ,"pu" ,"id"},
                new int[] {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textViewId});
        mListView.setAdapter(adapter2);;
    }
}

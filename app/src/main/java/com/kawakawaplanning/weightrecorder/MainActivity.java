package com.kawakawaplanning.weightrecorder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.jjoe64.graphview.GraphView;
import com.kawakawaplanning.weightrecorder.Fragment.LifeListFragment;
import com.kawakawaplanning.weightrecorder.Fragment.PressGraphFragment;
import com.kawakawaplanning.weightrecorder.Fragment.WeightGraphFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    public static final int PREFERENCE_INIT = 0;
    public static final int PREFERENCE_BOOTED = 1;
    public EditText nameEt;
    public EditText heiEt;
    static ViewPager vp;
    static LinearLayout[] ll = null;
    static ImageView iv_plus = null;
    float scale = 0;
    float scaled_px1 = 0;
    float scaled_px2 = 0;
    static boolean menu_opened = false;// メニュータップフラグ
    private static final long ANIMATION_TIMES = 100;//ミリ秒

    private ImageView mImgitem1;
    private TextView mItem1Tv;
    private ImageView mImgitem2;
    private TextView mItem2Tv;
    private ImageView mImgitem3;
    private TextView mItem3Tv;
    private ImageView mImgitem4;
    private TextView mItem4Tv;
    private ImageView mImgitem5;
    private TextView mItem5Tv;
    private ImageView mImgitem6;
    private TextView mItem6Tv;
    static LinearLayout rootLo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setState(PREFERENCE_BOOTED);

        vp = (ViewPager)findViewById(R.id.mypager);//定義
        vp.setAdapter(new PAdapter(this.getSupportFragmentManager()));//アダプタ入れる
        vp.setCurrentItem(2);
        PagerTabStrip pts = (PagerTabStrip)findViewById(R.id.pagertabstrip);
        pts.setDrawFullUnderline(true);
        vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_DRAGGING == state) {
                    // スライド検知
                    onKieru();
                }
            }
        });
        findItemView();
//        LifeItem item = LifeItem.load(LifeItem.class, 4);
    }

    public void onResume() {
        super.onResume();
        findItemView();
        get_scale();
    }


    private void findItemView() {
        mImgitem1 = (ImageView) findViewById(R.id.imgitem1);
        mItem1Tv = (TextView) findViewById(R.id.item1Tv);
        mImgitem2 = (ImageView) findViewById(R.id.imgitem2);
        mItem2Tv = (TextView) findViewById(R.id.item2Tv);
        mImgitem3 = (ImageView) findViewById(R.id.imgitem3);
        mItem3Tv = (TextView) findViewById(R.id.item3Tv);
        mImgitem4 = (ImageView) findViewById(R.id.imgitem4);
        mItem4Tv = (TextView) findViewById(R.id.item4Tv);
        mImgitem5 = (ImageView) findViewById(R.id.imgitem5);
        mItem5Tv = (TextView) findViewById(R.id.item5Tv);
        mImgitem6 = (ImageView) findViewById(R.id.imgitem6);
        mItem6Tv = (TextView) findViewById(R.id.item6Tv);
        rootLo = (LinearLayout) findViewById(R.id.rootLo);

        ll = new LinearLayout[6];
        ll[0] = (LinearLayout) findViewById(R.id.item1);
        ll[1] = (LinearLayout) findViewById(R.id.item2);
        ll[2] = (LinearLayout) findViewById(R.id.item3);
        ll[3] = (LinearLayout) findViewById(R.id.item4);
        ll[4] = (LinearLayout) findViewById(R.id.item5);
        ll[5] = (LinearLayout) findViewById(R.id.item6);
        iv_plus = (ImageView) findViewById(R.id.plus_ic);

        mImgitem1.setOnClickListener(this);
        mImgitem2.setOnClickListener(this);
        mImgitem3.setOnClickListener(this);
        mImgitem4.setOnClickListener(this);
        mImgitem5.setOnClickListener(this);
        mImgitem6.setOnClickListener(this);
    }

    private void get_scale() {
        scale = getResources().getDisplayMetrics().density;
        scaled_px1 = -384 * scale;
        scaled_px2 = 64 * scale;
    }
    public void plus_ic(View v) {
        // フラグ管理

        rootLo.setBackgroundColor(Color.parseColor("#880c1b1b"));

        if (menu_opened == false) {
            ll[0].setVisibility(View.VISIBLE);
            ll[1].setVisibility(View.VISIBLE);
            ll[2].setVisibility(View.VISIBLE);
            ll[3].setVisibility(View.VISIBLE);
            ll[4].setVisibility(View.VISIBLE);
            ll[5].setVisibility(View.VISIBLE);
            switch (vp.getCurrentItem()){

                case 0://リストページなら
                    mImgitem2.setImageResource(R.drawable.ic_all);
                    mImgitem3.setImageResource(R.drawable.ic_site);
                    mImgitem4.setImageResource(R.drawable.ic_year);
                    mImgitem5.setImageResource(R.drawable.ic_month);
                    mImgitem6.setImageResource(R.drawable.ic_week);
                    mItem1Tv.setText("Item1");mItem2Tv.setText("全期間表示");mItem3Tv.setText("期間指定");
                    mItem4Tv.setText("１年分表示");mItem5Tv.setText("１ヶ月分表示");mItem6Tv.setText("１週間分表示");
                    ll[0].setVisibility(View.INVISIBLE);
                    break;
                case 1://記録ページなら
                    mImgitem5.setImageResource(R.drawable.ic_pen);
                    mImgitem6.setImageResource(R.drawable.ic_pen);
                    mItem1Tv.setText("Item1");mItem2Tv.setText("Item2");mItem3Tv.setText("Item3");
                    mItem4Tv.setText("Item4");mItem5Tv.setText("名前変更");mItem6Tv.setText("身長変更");
                    ll[0].setVisibility(View.INVISIBLE);
                    ll[1].setVisibility(View.INVISIBLE);
                    ll[2].setVisibility(View.INVISIBLE);
                    ll[3].setVisibility(View.INVISIBLE);
                    break;
                case 2://マイページなら
                    mImgitem5.setImageResource(R.drawable.ic_open);
                    mImgitem6.setImageResource(R.drawable.ic_i);
                    mItem1Tv.setText("Item1");mItem2Tv.setText("Item2");mItem3Tv.setText("Item3");
                    mItem4Tv.setText("Item4");mItem5Tv.setText("オープンソースライセンス");mItem6Tv.setText("このアプリについて");
                    ll[0].setVisibility(View.INVISIBLE);
                    ll[1].setVisibility(View.INVISIBLE);
                    ll[2].setVisibility(View.INVISIBLE);
                    ll[3].setVisibility(View.INVISIBLE);
                    break;
                case 3://体重グラフなら
                    mImgitem2.setImageResource(R.drawable.ic_all);
                    mImgitem3.setImageResource(R.drawable.ic_site);
                    mImgitem4.setImageResource(R.drawable.ic_year);
                    mImgitem5.setImageResource(R.drawable.ic_month);
                    mImgitem6.setImageResource(R.drawable.ic_week);
                    mItem1Tv.setText("Item1");mItem2Tv.setText("全期間表示");mItem3Tv.setText("期間指定");
                    mItem4Tv.setText("１年分表示");mItem5Tv.setText("１ヶ月分表示");mItem6Tv.setText("１週間分表示");
                    ll[0].setVisibility(View.INVISIBLE);
                    break;
                case 4://血圧グラフなら
                    mImgitem2.setImageResource(R.drawable.ic_all);
                    mImgitem3.setImageResource(R.drawable.ic_site);
                    mImgitem4.setImageResource(R.drawable.ic_year);
                    mImgitem5.setImageResource(R.drawable.ic_month);
                    mImgitem6.setImageResource(R.drawable.ic_week);
                    mItem1Tv.setText("Item1");mItem2Tv.setText("全期間表示");mItem3Tv.setText("期間指定");
                    mItem4Tv.setText("１年分表示");mItem5Tv.setText("１ヶ月分表示");mItem6Tv.setText("１週間分表示");
                    ll[0].setVisibility(View.INVISIBLE);
                    break;
            }
            menu_opened = true;
            menu_open();
        } else {

            menu_opened = false;
            menu_close();
        }
    }


    private void menu_open() {
        iv_plus.animate().setInterpolator(new OvershootInterpolator());
        iv_plus.animate().setDuration(ANIMATION_TIMES);
        iv_plus.animate().rotation(135).alpha(0.9f);
        for (int i = 0, j = ll.length; i < j; i++) {
            ll[i].animate().setInterpolator(new AccelerateDecelerateInterpolator());
            ll[i].animate().setDuration(ANIMATION_TIMES);
            ll[i].animate().x(0).y(scaled_px1 + (scaled_px2 * i)).alpha(0.9f);
        }
    }

    private static void menu_close() {
        iv_plus.animate().setInterpolator(new OvershootInterpolator());
        iv_plus.animate().setDuration(ANIMATION_TIMES);
        iv_plus.animate().rotation(0).alpha(0.9f);
        for (int i = 0, j = ll.length; i < j; i++) {
            ll[i].animate().setInterpolator(new LinearInterpolator());
            ll[i].animate().setDuration(ANIMATION_TIMES);
            ll[i].animate().x(0).y(0).alpha(0f);
        }
        rootLo.setBackgroundColor(Color.parseColor("#00000000"));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (vp.getCurrentItem() != 2){
                vp.setCurrentItem(2);
            }else finish();

            return true;

        }
        return false;
    }

    private void setState(int state) {
        // SharedPreferences設定を保存
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt("InitState", state).commit();

        //ログ表示
        // output( String.valueOf(state) );
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgitem2:
                menu_close();
                switch (vp.getCurrentItem()){
                    case 0://1ページ目の時
                        listshow(100000000);
                        break;
                    case 1://2ページ目の時

                        break;
                    case 2://3ページ目の時

                        break;
                    case 3://4ページ目の時
                        WeightGraphFragment.drowGraph(getWTData(100000000), getFATData(100000000));
                        break;
                    case 4://5ページ目の時
                        PressGraphFragment.drowGraph(getPUData(100000000),getPDData(100000000));
                        break;
                }
                menu_opened = false;
                break;
            case R.id.imgitem3:
                menu_close();
                switch (vp.getCurrentItem()){
                    case 0://1ページ目の時
                        AlertDialog.Builder alertDialogBuildername = new AlertDialog.Builder(this);
                        LayoutInflater inflatername = (LayoutInflater)this.getSystemService(
                                LAYOUT_INFLATER_SERVICE);
                        View viewname =  inflatername.inflate(R.layout.dialogday,
                                (ViewGroup)findViewById(R.id.dialogname_layout));

                        nameEt = (EditText)viewname.findViewById(R.id.editText);
                        alertDialogBuildername.setTitle("日数指定");
                        alertDialogBuildername.setView(viewname);
                        alertDialogBuildername.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                        String name = nameEt.getEditableText().toString();
                                        if(!name.isEmpty())
                                        {
                                            listshow(Integer.parseInt(name));
                                        }
                                    }
                                });
                        alertDialogBuildername.setCancelable(true);
                        AlertDialog alertDialogname = alertDialogBuildername.create();
                        alertDialogname.show();
                        
                        break;
                    case 1://2ページ目の時

                        break;
                    case 2://3ページ目の時

                        break;
                    case 3://4ページ目の時
                        AlertDialog.Builder alertDialogBuilderday = new AlertDialog.Builder(this);
                        LayoutInflater inflaterday = (LayoutInflater)this.getSystemService(
                                LAYOUT_INFLATER_SERVICE);
                        View viewday =  inflaterday.inflate(R.layout.dialogday,
                                (ViewGroup)findViewById(R.id.dialogname_layout));

                        nameEt = (EditText)viewday.findViewById(R.id.editText);
                        alertDialogBuilderday.setTitle("日数指定");
                        alertDialogBuilderday.setView(viewday);
                        alertDialogBuilderday.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String day = nameEt.getEditableText().toString();
                                        if(!day.isEmpty()) {
                                            WeightGraphFragment.drowGraph(getWTData(Integer.parseInt(day)), getFATData(Integer.parseInt(day)));
                                        }
                                    }
                                });
                        alertDialogBuilderday.setCancelable(true);
                        AlertDialog alertDialogday = alertDialogBuilderday.create();
                        alertDialogday.show();
                        break;
                    case 4://5ページ目の時
                        AlertDialog.Builder alertDialogBuilderday1 = new AlertDialog.Builder(this);
                        LayoutInflater inflaterday1 = (LayoutInflater)this.getSystemService(
                                LAYOUT_INFLATER_SERVICE);
                        View viewday1 =  inflaterday1.inflate(R.layout.dialogday,
                                (ViewGroup)findViewById(R.id.dialogname_layout));

                        nameEt = (EditText)viewday1.findViewById(R.id.editText);
                        alertDialogBuilderday1.setTitle("日数指定");
                        alertDialogBuilderday1.setView(viewday1);
                        alertDialogBuilderday1.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String day = nameEt.getEditableText().toString();
                                        if (!day.isEmpty()) {
                                            PressGraphFragment.drowGraph(getPUData(Integer.parseInt(day)), getPDData(Integer.parseInt(day)));
                                        }
                                    }
                                });
                        alertDialogBuilderday1.setCancelable(true);
                        AlertDialog alertDialogday1 = alertDialogBuilderday1.create();
                        alertDialogday1.show();
                        break;
                }
                menu_opened = false;

                break;
            case R.id.imgitem4:
                menu_close();
                switch (vp.getCurrentItem()){
                    case 0://1ページ目の時
                        listshow(365);
                        break;
                    case 1://2ページ目の時

                        break;
                    case 2://3ページ目の時

                        break;
                    case 3://4ページ目の時
                        WeightGraphFragment.drowGraph(getWTData(365), getFATData(365));
                        break;
                    case 4://5ページ目の時
                        PressGraphFragment.drowGraph(getPUData(365),getPDData(365));
                        break;
                }
                menu_opened = false;
                break;
            case R.id.imgitem5:
                menu_close();
                switch (vp.getCurrentItem()){
                    case 0://1ページ目の時
                        listshow(30);
                        break;
                    case 1://2ページ目の時
                        AlertDialog.Builder alertDialogBuildername = new AlertDialog.Builder(this);
                        LayoutInflater inflatername = (LayoutInflater)this.getSystemService(
                                LAYOUT_INFLATER_SERVICE);
                        View viewname =  inflatername.inflate(R.layout.dialogname,
                                (ViewGroup)findViewById(R.id.dialogname_layout));

                        nameEt = (EditText)viewname.findViewById(R.id.editText);
                        alertDialogBuildername.setTitle("名前設定");
                        alertDialogBuildername.setView(viewname);
                        alertDialogBuildername.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String na = nameEt.getEditableText().toString();
                                        if (!na.isEmpty()) {
                                            SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = data.edit();
                                            editor.putString("Name", na);
                                            editor.apply();
                                        }
                                    }
                                });
                        alertDialogBuildername.setCancelable(true);
                        AlertDialog alertDialogname = alertDialogBuildername.create();
                        alertDialogname.show();
                        break;
                    case 2://3ページ目の時
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = (LayoutInflater)this.getSystemService(
                        LAYOUT_INFLATER_SERVICE);
                View view =  inflater.inflate(R.layout.opensourcelicense,
                        (ViewGroup)findViewById(R.id.rootLayout));
                        setSpannableString(view);
                alertDialogBuilder.setTitle("オープンソースライセンス");
                alertDialogBuilder.setView(view);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertDialogBuilder.setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                        break;
                    case 3://4ページ目の時
                        WeightGraphFragment.drowGraph(getWTData(30), getFATData(30));
                        break;
                    case 4://5ページ目の時
                        PressGraphFragment.drowGraph(getPUData(30),getPDData(30));
                        break;
                }
                menu_opened = false;
                break;
            case R.id.imgitem6:
                menu_close();
                switch (vp.getCurrentItem()){
                    case 0://1ページ目の時
                        listshow(7);
                        break;
                    case 1://2ページ目の時
                        AlertDialog.Builder alertDialogBuilderhei = new AlertDialog.Builder(this);
                LayoutInflater inflaterhei = (LayoutInflater)this.getSystemService(
                        LAYOUT_INFLATER_SERVICE);
                View viewhei =  inflaterhei.inflate(R.layout.dialoghei,
                        (ViewGroup)findViewById(R.id.dialoghei_layout));

                heiEt = (EditText)viewhei.findViewById(R.id.editText);

                alertDialogBuilderhei.setTitle("身長設定");
                alertDialogBuilderhei.setView(viewhei);
                alertDialogBuilderhei.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String he = heiEt.getEditableText().toString();
                                if (!he.isEmpty()) {
                                    SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = data.edit();

                                    editor.putInt("Height", Integer.parseInt(he));
                                    editor.apply();
                                }

                            }
                        });
                alertDialogBuilderhei.setCancelable(true);
                AlertDialog alertDialoghei = alertDialogBuilderhei.create();
                alertDialoghei.show();
                        break;
                    case 2://3ページ目の時

                        break;
                    case 3://4ページ目の時
                        WeightGraphFragment.drowGraph(getWTData(7), getFATData(7));
                        break;
                    case 4://5ページ目の時
                        PressGraphFragment.drowGraph(getPUData(7),getPDData(7));
                        break;
                }
                menu_opened = false;
                break;

        }
    }

    public static void onKieru(){
        menu_close();
        menu_opened = false;
    }

    public GraphView.GraphViewData[] getPUData(int day){
        int s = 0;
        List<LifeItem> list = new Select().from(LifeItem.class).execute();

        int len = 0;
        for (LifeItem i : list) {
            len++;
        }
        int[] puu = new int[len];
        for (LifeItem a : list) {
            puu[s] = a.puu;
            s++;
        }

        s = 0;

        int num = puu.length;

        int minus = 0;
        if(day > num){
            minus = 0;
            day = num;
        }else if (day < num){
            minus = num - day;
        }

        GraphView.GraphViewData[] puuGraph;
        if (num == 0){
            puuGraph = new GraphView.GraphViewData[1];
            puuGraph[0] = new GraphView.GraphViewData(0, 0);
        }else {
            puuGraph = new GraphView.GraphViewData[day];
            for (int i = 0; i < day; i++) {
                puuGraph[i] = new GraphView.GraphViewData(i, puu[minus]);
                minus++;
            }
        }
        return puuGraph;
    }
    public GraphView.GraphViewData[] getPDData(int day){

        int s = 0;
        List<LifeItem> list = new Select().from(LifeItem.class).execute();

        int len = 0;
        for (LifeItem i : list) {
            len++;
        }
        int[] pud = new int[len];
        for (LifeItem a : list) {
            pud[s] = a.pud;
            s++;
        }

        s = 0;
        int num = pud.length;
        int minus = 0;
        if(day > num){
            minus = 0;
            day = num;
        }else if (day < num){
            minus = num - day;
        }
        GraphView.GraphViewData[] pudGraph;
        if (num == 0){
            pudGraph = new GraphView.GraphViewData[1];
            pudGraph[0] = new GraphView.GraphViewData(0, 0);
        }else {
            pudGraph = new GraphView.GraphViewData[day];
            for (int i = 0; i < day; i++) {
                pudGraph[i] = new GraphView.GraphViewData(i, pud[minus]);
                minus++;
            }
        }
        return pudGraph;
    }
    public GraphView.GraphViewData[] getWTData(int day){
        int s = 0;
        List<LifeItem> list = new Select().from(LifeItem.class).execute();

        int len = 0;
        for (LifeItem i : list) {
            len++;
        }
        Float[] wei = new Float[len];
        for (LifeItem a : list) {
            wei[s] = a.weight;
            s++;
        }

        s = 0;
        int num = wei.length;

        int minus = 0;
        if(day > num){
            minus = 0;
            day = num;
        }else if (day < num){
            minus = num - day;
        }
        GraphView.GraphViewData[] weiGraph;
        if (num == 0){
            weiGraph = new GraphView.GraphViewData[1];
            weiGraph[0] = new GraphView.GraphViewData(0, 0);
        }else {
            weiGraph = new GraphView.GraphViewData[day];
            for (int i = 0; i < day; i++) {
                weiGraph[i] = new GraphView.GraphViewData(i, wei[minus]);
                minus++;
            }
        }
        return weiGraph;
    }
    public GraphView.GraphViewData[] getFATData(int day){
        int s = 0;
        List<LifeItem> list = new Select().from(LifeItem.class).execute();

        int len = 0;
        for (LifeItem i : list) {
            len++;
        }
        int[] fat = new int[len];
        for (LifeItem a : list) {
            fat[s] = a.fat;
            s++;
        }

        s = 0;
        int num = fat.length;
        int minus = 0;
        if(day > num){
            minus = 0;
            day = num;
        }else if (day < num){
            minus = num - day;
        }
        GraphView.GraphViewData[] fatGraph;
        if (num == 0){
            fatGraph = new GraphView.GraphViewData[1];
            fatGraph[0] = new GraphView.GraphViewData(0, 0);
        }else {
            fatGraph = new GraphView.GraphViewData[day];
            for (int i = 0; i < day; i++) {
                fatGraph[i] = new GraphView.GraphViewData(i, fat[minus]);
                minus++;
            }
        }
        return fatGraph;
    }

    public void listshow(int day){
        int s = 0;
        Map<String, String> data;
        List<LifeItem> list1 = new Select().from(LifeItem.class).execute();
        List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
        int len = 0;
        for (LifeItem i : list1) {
            len++;
        }
        Float[] weight = new Float[len];
        int[] fat = new int[len];
        int[] puu = new int[len];
        int[] pud = new int[len];
        String[] days = new String[len];
        double[] bmi = new double[len];
        long[] id = new long[len];

        for (LifeItem a : list1) {
            weight[s] = a.weight;
            fat[s] = a.fat;
            days[s] = a.day;
            bmi[s] = a.bmi;
            puu[s] = a.puu;
            pud[s] = a.pud;
            id[s] = a.getId();
            s++;
        }


        int num = weight.length;
        int minus = 0;
        if(day > num){
            minus = 0;
            day = num;
        }else if (day < num){
            minus = num - day;
        }
        if (num == 0){
            data = new HashMap<String, String>();
            data.put("day", "データがありません。記録画面から登録してください。");
            data.put("weight","");
            data.put("pu","");
            data.put("id", "");

            retDataList.add(data);
        }else {
            for (int i = 0; i < day; i++) {
                data = new HashMap<String, String>();
                data.put("day", days[minus]);
                data.put("weight", "　　体重:" + weight[minus] + "kg　体脂肪:" + fat[minus] + "%");
                data.put("pu", "　　血圧:" + puu[minus] + "/" + pud[minus] + "　BMI:" + bmi[minus]);
                data.put("id", id[minus] + "");
                retDataList.add(data);
                minus++;
                SimpleAdapter adapter2 = new SimpleAdapter(LifeListFragment.act, retDataList,
                        R.layout.raw, new String[] { "day", "weight" ,"pu" ,"id" },
                        new int[] {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textViewId});
                LifeListFragment.mListView.setAdapter(adapter2);
            }

        }
    }
    private void setSpannableString(View view) {

        String message = "このソフトウェアは、オープンソースソフトウェアによって実現しました。";

        // リンク化対象の文字列、リンク先 URL を指定する
        Map<String, String> map = new HashMap<String, String>();
        map.put("オープンソースソフトウェア", "");

        // SpannableString の取得
        SpannableString ss = createSpannableString(message, map);

        // SpannableString をセットし、リンクを有効化する
        TextView textView = (TextView) view.findViewById(R.id.textView19);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private SpannableString createSpannableString(String message, Map<String, String> map) {

        SpannableString ss = new SpannableString(message);

        for (final Map.Entry<String, String> entry : map.entrySet()) {
            int start = 0;
            int end = 0;

            // リンク化対象の文字列の start, end を算出する
            Pattern pattern = Pattern.compile(entry.getKey());
            Matcher matcher = pattern.matcher(message);
            while (matcher.find()) {
                start = matcher.start();
                end = matcher.end();
                break;
            }

            // SpannableString にクリックイベント、パラメータをセットする
            ss.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Intent intent = new Intent(getApplicationContext(), OpenSourceLicenseActivity.class);
                    startActivity(intent);
                }
            }, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return ss;
    }
}

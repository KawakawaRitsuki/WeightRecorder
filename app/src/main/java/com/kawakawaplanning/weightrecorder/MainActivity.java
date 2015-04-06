package com.kawakawaplanning.weightrecorder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.jjoe64.graphview.GraphView;
import com.kawakawaplanning.weightrecorder.Fragment.PressGraphFragment;

import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    public static final int PREFERENCE_INIT = 0;
    public static final int PREFERENCE_BOOTED = 1;
    public EditText nameEt;
    public EditText heiEt;
    static ViewPager vp;
    LinearLayout[] ll = null;
    ImageView iv_plus = null;
    float scale = 0;
    float scaled_px1 = 0;
    float scaled_px2 = 0;
    boolean menu_opened = false;// メニュータップフラグ
    private static final long ANIMATION_TIMES = 200;//ミリ秒

    private LinearLayout mItem1;
    private ImageView mImgitem1;
    private TextView mItem1Tv;
    private LinearLayout mItem2;
    private ImageView mImgitem2;
    private TextView mItem2Tv;
    private LinearLayout mItem3;
    private ImageView mImgitem3;
    private TextView mItem3Tv;
    private LinearLayout mItem4;
    private ImageView mImgitem4;
    private TextView mItem4Tv;
    private LinearLayout mItem5;
    private ImageView mImgitem5;
    private TextView mItem5Tv;
    private LinearLayout mItem6;
    private ImageView mImgitem6;
    private TextView mItem6Tv;
    private ImageView mPlusIc;

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

    }

    public void onResume() {
        super.onResume();
        findItemView();
        get_scale();
    }



    private void assignViews() {

    }

    private void findItemView() {
        mItem1 = (LinearLayout) findViewById(R.id.item1);
        mImgitem1 = (ImageView) findViewById(R.id.imgitem1);
        mItem1Tv = (TextView) findViewById(R.id.item1Tv);
        mItem2 = (LinearLayout) findViewById(R.id.item2);
        mImgitem2 = (ImageView) findViewById(R.id.imgitem2);
        mItem2Tv = (TextView) findViewById(R.id.item2Tv);
        mItem3 = (LinearLayout) findViewById(R.id.item3);
        mImgitem3 = (ImageView) findViewById(R.id.imgitem3);
        mItem3Tv = (TextView) findViewById(R.id.item3Tv);
        mItem4 = (LinearLayout) findViewById(R.id.item4);
        mImgitem4 = (ImageView) findViewById(R.id.imgitem4);
        mItem4Tv = (TextView) findViewById(R.id.item4Tv);
        mItem5 = (LinearLayout) findViewById(R.id.item5);
        mImgitem5 = (ImageView) findViewById(R.id.imgitem5);
        mItem5Tv = (TextView) findViewById(R.id.item5Tv);
        mItem6 = (LinearLayout) findViewById(R.id.item6);
        mImgitem6 = (ImageView) findViewById(R.id.imgitem6);
        mItem6Tv = (TextView) findViewById(R.id.item6Tv);
        mPlusIc = (ImageView) findViewById(R.id.plus_ic);
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


        if (menu_opened == false) {
            if(vp.getCurrentItem() == 1){
                mItem6Tv.setText("記録(ここ)");
            }else{
                mItem6Tv.setText("記録");
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

    private void menu_close() {
        iv_plus.animate().setInterpolator(new OvershootInterpolator());
        iv_plus.animate().setDuration(ANIMATION_TIMES - 100);
        iv_plus.animate().rotation(0).alpha(0.9f);
        for (int i = 0, j = ll.length; i < j; i++) {
            ll[i].animate().setInterpolator(new LinearInterpolator());
            ll[i].animate().setDuration(ANIMATION_TIMES-100);
            ll[i].animate().x(0).y(0).alpha(0f);
        }
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
            case R.id.imgitem1:
                menu_close();
                Toast.makeText(MainActivity.this, "Ktkr1", Toast.LENGTH_SHORT).show();
                PressGraphFragment.drowGraph(getPUData(2),getPUData(2));
                menu_opened = false;
                break;
            case R.id.imgitem2:
                menu_close();
                Toast.makeText(MainActivity.this, "Ktkr2", Toast.LENGTH_SHORT).show();
                menu_opened = false;
                break;
            case R.id.imgitem3:
                menu_close();
                Toast.makeText(MainActivity.this, "Ktkr3", Toast.LENGTH_SHORT).show();
                menu_opened = false;
                break;
            case R.id.imgitem4:
                menu_close();
                Toast.makeText(MainActivity.this, "Ktkr4", Toast.LENGTH_SHORT).show();
                menu_opened = false;
                break;
            case R.id.imgitem5:
                menu_close();
                Toast.makeText(MainActivity.this, "Ktkr5", Toast.LENGTH_SHORT).show();
                menu_opened = false;
                break;
            case R.id.imgitem6:
                menu_close();
                vp.setCurrentItem(1);
                menu_opened = false;
                break;

        }
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
        int minus = num - day;
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
    public boolean onCreateOptionsMenu(Menu menu){

        menu.add(0, 0, 0, "身長変更");
        menu.add(0, 1, 0, "名前変更");
        menu.add(0, 2, 0, "オープンソースライセンス");

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Log.d("Menu", "Select Menu A");
                AlertDialog.Builder alertDialogBuilderhei = new AlertDialog.Builder(this);
                LayoutInflater inflaterhei = (LayoutInflater)this.getSystemService(
                        LAYOUT_INFLATER_SERVICE);
                View viewhei =  inflaterhei.inflate(R.layout.dialoghei,
                        (ViewGroup)findViewById(R.id.dialoghei_layout));

                heiEt = (EditText)viewhei.findViewById(R.id.editText);

                // アラートダイアログのタイトルを設定します
                alertDialogBuilderhei.setTitle("身長設定");
                // アラートダイアログのメッセージを設定します
                alertDialogBuilderhei.setView(viewhei);
                // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します





                alertDialogBuilderhei.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = data.edit();
                                String he = heiEt.getEditableText().toString();
                                editor.putInt("Height", Integer.parseInt(he));
                                editor.apply();

                            }
                        });
                alertDialogBuilderhei.setCancelable(true);
                AlertDialog alertDialoghei = alertDialogBuilderhei.create();
                // アラートダイアログを表示します
                alertDialoghei.show();
                return true;

            case 1:
                Log.d("Menu","Select Menu B");
                AlertDialog.Builder alertDialogBuildername = new AlertDialog.Builder(this);
                LayoutInflater inflatername = (LayoutInflater)this.getSystemService(
                        LAYOUT_INFLATER_SERVICE);
                View viewname =  inflatername.inflate(R.layout.dialogname,
                        (ViewGroup)findViewById(R.id.dialogname_layout));

                nameEt = (EditText)viewname.findViewById(R.id.editText);

                // アラートダイアログのタイトルを設定します
                alertDialogBuildername.setTitle("名前設定");
                // アラートダイアログのメッセージを設定します
                alertDialogBuildername.setView(viewname);
                // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
                alertDialogBuildername.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = data.edit();
                                editor.putString("Name", nameEt.getEditableText().toString());
                                editor.apply();

                            }
                        });
                alertDialogBuildername.setCancelable(true);
                AlertDialog alertDialogname = alertDialogBuildername.create();
                // アラートダイアログを表示します
                alertDialogname.show();
                return true;

            case 2:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = (LayoutInflater)this.getSystemService(
                        LAYOUT_INFLATER_SERVICE);
                View view =  inflater.inflate(R.layout.opensourcelicense,
                        (ViewGroup)findViewById(R.id.rootLayout));
                // アラートダイアログのタイトルを設定します
                alertDialogBuilder.setTitle("オープンソースライセンス");
                // アラートダイアログのメッセージを設定します
                alertDialogBuilder.setView(view);
                // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialogBuilder.setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                // アラートダイアログを表示します
                alertDialog.show();
                return true;

        }
        return false;
    }

}

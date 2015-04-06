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
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    public static final int PREFERENCE_INIT = 0;
    public static final int PREFERENCE_BOOTED = 1;
    public EditText nameEt;
    public EditText heiEt;
    static ViewPager vp;
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

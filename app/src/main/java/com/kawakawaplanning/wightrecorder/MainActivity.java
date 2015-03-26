package com.kawakawaplanning.wightrecorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = (ViewPager)findViewById(R.id.mypager);//定義

        vp.setAdapter(new PAdapter(this.getSupportFragmentManager()));//アダプタ入れる

        PagerTabStrip pts = (PagerTabStrip)findViewById(R.id.pagertabstrip);

        pts.setDrawFullUnderline(true);
    }
    @Override
    public void onResume(){
        super.onResume();

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        // ダイアログの設定
        alertDialog.setTitle("FirstBoot");          //タイトル
        alertDialog.setMessage("初回メッセージ");      //内容
        alertDialog.setIcon(R.drawable.icon);   //アイコン設定

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //初回表示完了
                setState(PREFERENCE_BOOTED);
            }
        });

        // ダイアログの作成と表示
        if(PREFERENCE_INIT == getState() ){
            //初回起動時のみ表示する
            alertDialog.create();
            alertDialog.show();
        }
    }
}
package com.kawakawaplanning.weightrecorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by KP on 2015/03/29.
 */
public class StartActivity extends ActionBarActivity{
    private TextView mTextView21;
    private TextView mTextView5;
    private EditText mEditText3;
    private TextView mTextView7;
    private EditText mEditText4;
    private TextView mTextView;
    private EditText mEditText2;
    private Button mOkBtn;
    private Button mNoBtn;
    private String getPass;

    private void assignViews() {
        mTextView21 = (TextView) findViewById(R.id.textView21);
        mTextView5 = (TextView) findViewById(R.id.textView5);
        mEditText3 = (EditText) findViewById(R.id.editText3);
        mTextView7 = (TextView) findViewById(R.id.textView7);
        mEditText4 = (EditText) findViewById(R.id.editText4);
        mTextView = (TextView) findViewById(R.id.textView);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mOkBtn = (Button) findViewById(R.id.okBtn);
        mNoBtn = (Button) findViewById(R.id.noBtn);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        assignViews();

        if(0 == getState() ){


        }else if (1 == getState()){
            mTextView21.setText("ようこそ！\nこのアプリを利用するためにはパスワードを入力する必要があります。");
            mNoBtn.setVisibility(View.GONE);
            mTextView5.setVisibility(View.GONE);
            mTextView7.setVisibility(View.GONE);
            mEditText3.setVisibility(View.GONE);
            mEditText4.setVisibility(View.GONE);
            SharedPreferences prefs = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
            getPass = prefs.getString("pass", "-dj#sf+djk");//絶対にない（と思う）PW

            if(getPass.equals("-dj#sf+djk")){

                Intent intent=new Intent();
                intent.setClassName("com.kawakawaplanning.weightrecorder","com.kawakawaplanning.weightrecorder.MainActivity");
                startActivity(intent);

            }
        }



    }

    private int getState() {
        // 読み込み
        int state;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        state = sp.getInt("InitState", 0);

        //ログ表示
//        output( String.valueOf(state) );
        return state;
    }

    public void onClick(View v){
        switch (v.getId()){

            case R.id.okBtn:
                if(0 == getState() ){
                    boolean flag = false;
                    if(mEditText3.getText().toString().equals("")){
                        mEditText3.setError("入力してください。");
                        flag = true;
                    }
                    if(mEditText2.getText().toString().equals("")){
                        mEditText2.setError("入力してください。");
                        flag = true;
                    }
                    if(mEditText4.getText().toString().equals("")){
                        mEditText4.setError("入力してください。");
                        flag = true;
                    }
                    if (flag){}else {
                        if (mEditText2.length() < 6) {
                            mEditText2.setError("6文字以上で入力してください");
                        } else {
                            String hei =mEditText4.getText().toString();
                            SharedPreferences prefs = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();

                            editor.putString("pass", mEditText2.getText().toString());
                            editor.putInt("Height", Integer.parseInt(hei));
                            editor.putString("Name", mEditText3.getText().toString());
                            editor.apply();
                            Intent intent = new Intent();
                            intent.setClassName("com.kawakawaplanning.weightrecorder", "com.kawakawaplanning.weightrecorder.MainActivity");

                            startActivity(intent);
                        }
                    }
                }else if (1 == getState()){
                    SharedPreferences prefs = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                    getPass = prefs.getString("pass", "");//絶対にない（と思う）PW
                    if(getPass.equals(mEditText2.getText().toString())){
                        Intent intent=new Intent();
                        intent.setClassName("com.kawakawaplanning.weightrecorder","com.kawakawaplanning.weightrecorder.MainActivity");

                        startActivity(intent);
                    }else{
                        mEditText2.setError("パスワードが違います。");
                    }
                }
                break;

            case R.id.noBtn:
                boolean flag = false;
                if(mEditText3.getText().toString().equals("")){
                    mEditText3.setError("入力してください。");
                    flag = true;
                }
                if(mEditText4.getText().toString().equals("")){
                    mEditText4.setError("入力してください。");
                    flag = true;
                }
                if (flag){}else {
                    String hei =mEditText4.getText().toString();
                    SharedPreferences prefs = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putInt("Height", Integer.parseInt(hei));
                    editor.putString("Name", mEditText3.getText().toString());
                    editor.apply();
                    Intent intent = new Intent();
                    intent.setClassName("com.kawakawaplanning.weightrecorder", "com.kawakawaplanning.weightrecorder.MainActivity");

                    startActivity(intent);
                }
                break;
        }

    }
}

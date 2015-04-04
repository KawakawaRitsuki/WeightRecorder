package com.kawakawaplanning.weightrecorder.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kawakawaplanning.weightrecorder.LifeItem;
import com.kawakawaplanning.weightrecorder.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by KP on 2015/03/25.
 */
public class RecordFragment extends Fragment implements View.OnClickListener{

    private TextView mTextView1;
    private EditText mweightET;
    private TextView mTextView3;
    private EditText mPressureupET;
    private TextView mTextView2;
    private EditText mFatET;
    private TextView mTextView4;
    private EditText mPressuredownET;
    private Button mCommitBtn;
    Vibrator vibrator;

    private void assignViews(View v) {
        mTextView1 = (TextView) v.findViewById(R.id.textView1);
        mweightET = (EditText) v.findViewById(R.id.weightET);
        mTextView3 = (TextView) v.findViewById(R.id.textView3);
        mPressureupET = (EditText) v.findViewById(R.id.pressureupET);
        mTextView2 = (TextView) v.findViewById(R.id.textView2);
        mFatET = (EditText) v.findViewById(R.id.fatET);
        mTextView4 = (TextView) v.findViewById(R.id.textView4);
        mPressuredownET = (EditText) v.findViewById(R.id.pressuredownET);
        mCommitBtn = (Button) v.findViewById(R.id.commitBtn);
        mCommitBtn.setOnClickListener(this);
        vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
    }



    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_record, container, false);

        assignViews(v);
        return v;
    }

    public void onStart(){
        super.onStart();

    }

    @Override
    public void onClick(View v) {

     vibrator.vibrate(50);

        switch (v.getId()) {

            case R.id.commitBtn:

                Boolean flag = false;
                if (mweightET.length() == 0){
                    mweightET.setError("入力してください。");
                    flag = true;
                }
                if (mFatET.length() == 0){
                    mFatET.setError("入力してください。");
                    flag = true;
                }
                if (mPressuredownET.length() == 0){
                    mPressuredownET.setError("入力してください。");
                    flag = true;
                }
                if (mPressureupET.length() == 0){
                    mPressureupET.setError("入力してください。");
                    flag = true;
                }
                if (!flag){
                    commit();
                }

                break;


        }

    }

    public void commit(){
        float getweight = Float.parseFloat(mweightET.getEditableText().toString());
        int getFat = Integer.parseInt(mFatET.getEditableText().toString());
        int getPuu = Integer.parseInt(mPressureupET.getEditableText().toString());
        int getPud = Integer.parseInt(mPressuredownET.getEditableText().toString());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        LifeItem lifeItem = new LifeItem();

        lifeItem.day = sdf.format(c.getTime());
        lifeItem.weight = getweight;
        lifeItem.fat = getFat;
        lifeItem.puu = getPuu;
        lifeItem.pud = getPud;

        double bmi = bmi(Float.parseFloat(mweightET.getEditableText().toString()));
        lifeItem.bmi = bmi;

        lifeItem.save();

    }



    public double bmi(float weight){

        SharedPreferences data = getActivity().getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        double height = data.getInt("Height",-1 ) / 100F;




        double hei = height * height;

        double bmi = weight / hei;

        BigDecimal bd = new BigDecimal(bmi);
        BigDecimal bd3 = bd.setScale(1, BigDecimal.ROUND_DOWN);

        Log.v("kp","height" + height + "weight" + weight + "bmi" + bmi);

        return bd3.doubleValue();
    }

}

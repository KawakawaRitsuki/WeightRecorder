package com.kawakawaplanning.weightrecorder.Fragment;

import android.app.Activity;
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
import android.widget.NumberPicker;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.kawakawaplanning.weightrecorder.LifeItem;
import com.kawakawaplanning.weightrecorder.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KP on 2015/03/25.
 */
public class RecordFragment extends Fragment implements View.OnClickListener{

//    private EditText mweightET;
    private Button mCommitBtn;
    private NumberPicker mNumberPicker;
    private NumberPicker mNumberPicker2;
    private NumberPicker mNumberPicker3;
    private NumberPicker mNumberPicker4;
    private NumberPicker mNumberPicker5;
    public static Activity act;

    Vibrator vibrator;

    private void assignViews(View v) {
//        mweightET = (EditText) v.findViewById(R.id.weightET);
        mCommitBtn = (Button) v.findViewById(R.id.commitBtn);
        mCommitBtn.setOnClickListener(this);
        mNumberPicker = (NumberPicker)v.findViewById(R.id.numberPicker);
        mNumberPicker2 = (NumberPicker)v.findViewById(R.id.numberPicker2);
        mNumberPicker3 = (NumberPicker)v.findViewById(R.id.numberPicker3);
        mNumberPicker4 = (NumberPicker)v.findViewById(R.id.numberPicker4);
        mNumberPicker5 = (NumberPicker)v.findViewById(R.id.numberPicker5);
        vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
    }



    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_record, container, false);
        act = getActivity();
        assignViews(v);
        return v;
    }

    public void onStart(){
        super.onStart();
        mNumberPicker.setMaxValue(200);
        mNumberPicker.setMinValue(0);
        mNumberPicker2.setMaxValue(9);
        mNumberPicker2.setMinValue(0);
        mNumberPicker3.setMaxValue(300);
        mNumberPicker3.setMinValue(0);
        mNumberPicker4.setMaxValue(100);
        mNumberPicker4.setMinValue(0);
        mNumberPicker5.setMaxValue(300);
        mNumberPicker5.setMinValue(0);

        List<LifeItem> list = new Select().from(LifeItem.class).execute();
        Float weight = 0F;
        int fat = 0;
        int puu = 0;
        int pud = 0;
        for (LifeItem i : list) {
            weight = i.weight;
            fat = i.fat;
            puu = i.puu;
            pud = i.pud;
        }
        double seisuu = Math.floor(weight);
        double syousuu = weight - seisuu;

        mNumberPicker.setValue((int)seisuu);
        mNumberPicker2.setValue((int)(syousuu * 10));
        mNumberPicker3.setValue(puu);
        mNumberPicker4.setValue(fat);
        mNumberPicker5.setValue(pud);

//        mweightET.setText(weight + "");
//        mFatET.setText(fat + "");
//        mPressureupET.setText(puu + "");
//        mPressuredownET.setText(pud + "");

    }

    @Override
    public void onClick(View v) {

     vibrator.vibrate(50);
        Toast.makeText(getActivity(),"記録しました。",Toast.LENGTH_SHORT).show();
        switch (v.getId()) {

            case R.id.commitBtn:
                    commit();
                break;


        }

    }

    public void commit(){
        String getWeightStr = mNumberPicker.getValue() + "." + mNumberPicker2.getValue();
        float getweight = Float.parseFloat(getWeightStr);
        int getFat = mNumberPicker4.getValue();
        int getPuu = mNumberPicker3.getValue();
        int getPud = mNumberPicker5.getValue();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        LifeItem lifeItem = new LifeItem();

        lifeItem.day = sdf.format(c.getTime());
        lifeItem.weight = getweight;
        lifeItem.fat = getFat;
        lifeItem.puu = getPuu;
        lifeItem.pud = getPud;

        double bmi = bmi(getweight);
        lifeItem.bmi = bmi;

        lifeItem.save();

    }



    public static double bmi(float weight){

        SharedPreferences data = act.getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        double height = data.getInt("Height",-1 ) / 100F;




        double hei = height * height;

        double bmi = weight / hei;

        BigDecimal bd = new BigDecimal(bmi);
        BigDecimal bd3 = bd.setScale(1, BigDecimal.ROUND_DOWN);

        Log.v("kp","height" + height + "weight" + weight + "bmi" + bmi);

        return bd3.doubleValue();
    }

}

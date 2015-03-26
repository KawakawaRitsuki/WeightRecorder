package com.kawakawaplanning.wightrecorder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kawakawaplanning.wightrecorder.LifeItem;
import com.kawakawaplanning.wightrecorder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by KP on 2015/03/25.
 */
public class RecordFragment extends Fragment implements View.OnClickListener{

    private TextView mTextView1;
    private EditText mWightET;
    private TextView mTextView3;
    private EditText mPressureupET;
    private TextView mTextView2;
    private EditText mFatET;
    private TextView mTextView4;
    private EditText mPressuredownET;
    private Button mCommitBtn;

    private void assignViews(View v) {
        mTextView1 = (TextView) v.findViewById(R.id.textView1);
        mWightET = (EditText) v.findViewById(R.id.wightET);
        mTextView3 = (TextView) v.findViewById(R.id.textView3);
        mPressureupET = (EditText) v.findViewById(R.id.pressureupET);
        mTextView2 = (TextView) v.findViewById(R.id.textView2);
        mFatET = (EditText) v.findViewById(R.id.fatET);
        mTextView4 = (TextView) v.findViewById(R.id.textView4);
        mPressuredownET = (EditText) v.findViewById(R.id.pressuredownET);
        mCommitBtn = (Button) v.findViewById(R.id.commitBtn);
        mCommitBtn.setOnClickListener(this);
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

        switch (v.getId()) {

            case R.id.commitBtn:
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                LifeItem lifeItem = new LifeItem();

                lifeItem.day = sdf.format(c.getTime());
                lifeItem.wight = Integer.parseInt(mWightET.getEditableText().toString());
                lifeItem.fat = Integer.parseInt(mFatET.getEditableText().toString());
                lifeItem.puu = Integer.parseInt(mPressureupET.getEditableText().toString());
                lifeItem.pud = Integer.parseInt(mPressuredownET.getEditableText().toString());
                int bmi = bmi(Integer.parseInt(mWightET.getEditableText().toString()));

                lifeItem.bmi = bmi;

                lifeItem.save();
                break;


        }

    }

    public int bmi(int wight){

        int bmi = wight;
        return bmi;
    }

}

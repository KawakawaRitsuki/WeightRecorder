package com.kawakawaplanning.wightrecorder.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kawakawaplanning.wightrecorder.R;

/**
 * Created by KP on 2015/03/25.
 */
public class CalcFragment extends Fragment implements View.OnClickListener{

    private TextView mTextView1;
    private EditText mSaikouEt;
    private TextView mTextView;
    private EditText mSaiteiEt;
    private Button mCalcBtn;
    private TextView mTextView5;
    private TextView mHeikin;
    private TextView mTextView7;
    private TextView mMyakuatu;

    private void assignViews(View v) {
        mTextView1 = (TextView) v.findViewById(R.id.textView1);
        mSaikouEt = (EditText) v.findViewById(R.id.saikouEt);
        mTextView = (TextView) v.findViewById(R.id.textView);
        mSaiteiEt = (EditText) v.findViewById(R.id.saiteiEt);
        mCalcBtn = (Button) v.findViewById(R.id.calcBtn);
        mTextView5 = (TextView) v.findViewById(R.id.textView5);
        mHeikin = (TextView) v.findViewById(R.id.heikin);
        mTextView7 = (TextView) v.findViewById(R.id.textView7);
        mMyakuatu = (TextView) v.findViewById(R.id.myakuatu);
    }


    public CalcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calc, container, false);
        assignViews(v);
        mCalcBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onClick(View v){
        String saikou = mSaikouEt.getText().toString();
        String saitei = mSaiteiEt.getText().toString();
        Log.v("kp", saikou + "");
        mHeikin.setText(heikin(Integer.parseInt(saikou),Integer.parseInt(saitei))+"");
        mMyakuatu.setText(myakuatu(Integer.parseInt(saikou),Integer.parseInt(saitei))+"");
    }

    public int heikin(int up,int down){
        int heikin = down + (up - down) / 3;
        return heikin;
    }

    public int myakuatu(int up,int down){
        int myakuatu = up - down;
        return myakuatu;
    }

}

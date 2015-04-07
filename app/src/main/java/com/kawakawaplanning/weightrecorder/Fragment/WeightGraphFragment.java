package com.kawakawaplanning.weightrecorder.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.activeandroid.query.Select;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
import com.kawakawaplanning.weightrecorder.LifeItem;
import com.kawakawaplanning.weightrecorder.R;

import java.util.List;


public class WeightGraphFragment extends Fragment{

    public static FrameLayout graph;
    int s = 0;
    public static Activity activity;

    public WeightGraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        graph = (FrameLayout)v.findViewById(R.id.graph);
        activity = (Activity)v.getContext();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        List<LifeItem> list = new Select().from(LifeItem.class).execute();

        int len = 0;

        for (LifeItem i : list) {
            len ++;
        }

        float[] weight = new float[len];
        int[] fat = new int[len];

        for (LifeItem a : list) {
            weight[s]= a.weight;
            fat[s] = a.fat;
            s++;
        }

        s=0;

        int num = weight.length;

        GraphView.GraphViewData[] weightGraph;
        GraphView.GraphViewData[] fatGraph;


        if (num == 0){
            weightGraph = new GraphView.GraphViewData[1];
            fatGraph = new GraphView.GraphViewData[1];
            weightGraph[0] = new GraphView.GraphViewData(0, 0);
            fatGraph[0] = new GraphView.GraphViewData(0, 0);
        }else {
            weightGraph = new GraphView.GraphViewData[num];
            fatGraph = new GraphView.GraphViewData[num];
            for (int i = 0; i < num; i++) {
                weightGraph[i] = new GraphView.GraphViewData(i, weight[i]);
                fatGraph[i] = new GraphView.GraphViewData(i, fat[i]);
            }
        }
        drowGraph(weightGraph,fatGraph);




    }
    public static void drowGraph(GraphView.GraphViewData[] weightGraph,GraphView.GraphViewData[] fatGraph){
            graph.removeAllViews();
            LineGraphView graphView = new LineGraphView(activity, "体重の推移");
        int thickness = 10;//　線の太さ

        GraphViewSeries.GraphViewSeriesStyle styleWeight = new GraphViewSeries.GraphViewSeriesStyle(Color.parseColor("#52bbbb"), thickness);
        GraphViewSeries.GraphViewSeriesStyle styleFat = new GraphViewSeries.GraphViewSeriesStyle(Color.parseColor("#1e4343"), thickness);
        GraphViewSeries seriesWeight = new GraphViewSeries("体重", styleWeight, weightGraph);
        GraphViewSeries seriesFat = new GraphViewSeries("体脂肪", styleFat, fatGraph);

        graphView.addSeries(seriesWeight);
        graphView.addSeries(seriesFat);// GraphViewStyleの取得
        GraphViewStyle graphViewStyle = graphView.getGraphViewStyle();// 背景の線の色（格子になっているやつ）
        graphViewStyle.setGridColor(Color.DKGRAY);// 横軸（X軸）のラベルの色
        graphViewStyle.setHorizontalLabelsColor(Color.DKGRAY);// 縦軸（Y軸）のラベルの色
        graphViewStyle.setVerticalLabelsColor(Color.DKGRAY);// 縦軸（Y軸）のラベルのAlign
        graphViewStyle.setVerticalLabelsAlign(Paint.Align.CENTER);// 横軸のラベルの数(0 = auto)
        graphViewStyle.setNumHorizontalLabels(5);// 縦軸のラベルの数(0 = auto)
        graphViewStyle.setNumVerticalLabels(5);// 各ラベルのテキストサイズ
        graphViewStyle.setTextSize(40.0f);// 縦軸のラベルの幅（0 = auto）
        graphViewStyle.setVerticalLabelsWidth(0);// 背景
        graphView.setBackgroundColor(Color.DKGRAY);// グラフを拡大出来るようにするか
        graphView.setScalable(true);// グラフを拡大したときにスクロールできるようにするか
        graphView.setScrollable(true);// Legendを表示するかどうか
        graphView.setShowLegend(true);// Legendを表示する幅
        graphViewStyle.setLegendWidth(250);// 各項目のスペース
        graphViewStyle.setLegendSpacing(10);// ボーダーの幅
        graphViewStyle.setLegendBorder(10);// MarginBottom
        graphViewStyle.setLegendMarginBottom(250);
        graphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);


        graph.addView(graphView);
    }

}

package com.kawakawaplanning.weightrecorder.Fragment;

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


public class PressGraphFragment extends Fragment{

    FrameLayout graph;
    int s = 0;

    public PressGraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        graph = (FrameLayout)v.findViewById(R.id.graph);
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

        int[] puu = new int[len];
        int[] pud = new int[len];

        for (LifeItem a : list) {
            puu[s]= a.puu;
            pud[s] = a.pud;
            s++;
        }

        s=0;

        int num = puu.length;
        LineGraphView graphView = new LineGraphView(getActivity(), "血圧の推移");

        GraphView.GraphViewData[] puuGraph = new GraphView.GraphViewData[num];
        GraphView.GraphViewData[] pudGraph = new GraphView.GraphViewData[num];

        for (int i = 0; i < num; i++) {
            puuGraph[i] = new GraphView.GraphViewData(i,puu[i]);
            pudGraph[i] = new GraphView.GraphViewData(i,pud[i]);
        }

        //　線の太さ
        int thickness = 10;

        GraphViewSeries.GraphViewSeriesStyle stylepuu = new GraphViewSeries.GraphViewSeriesStyle(Color.parseColor("#df0f0f"), thickness);
        GraphViewSeries.GraphViewSeriesStyle stylepud = new GraphViewSeries.GraphViewSeriesStyle(Color.parseColor("#041fbc"), thickness);

        GraphViewSeries seriesPuu = new GraphViewSeries("最高血圧", stylepuu, puuGraph);
        GraphViewSeries seriesPud = new GraphViewSeries("最低血圧", stylepud, pudGraph);

        graphView.addSeries(seriesPuu);
        graphView.addSeries(seriesPud);


        // GraphViewStyleの取得
        GraphViewStyle graphViewStyle = graphView.getGraphViewStyle();
        // 背景の線の色（格子になっているやつ）
        graphViewStyle.setGridColor(Color.DKGRAY);
        // 横軸（X軸）のラベルの色
        graphViewStyle.setHorizontalLabelsColor(Color.DKGRAY);
        // 縦軸（Y軸）のラベルの色
        graphViewStyle.setVerticalLabelsColor(Color.DKGRAY);
        // 縦軸（Y軸）のラベルのAlign
        graphViewStyle.setVerticalLabelsAlign(Paint.Align.CENTER);
        // 横軸のラベルの数(0 = auto)
        graphViewStyle.setNumHorizontalLabels(5);
        // 縦軸のラベルの数(0 = auto)
        graphViewStyle.setNumVerticalLabels(5);
        // 各ラベルのテキストサイズ
        graphViewStyle.setTextSize(40.0f);
        // 縦軸のラベルの幅（0 = auto）
        graphViewStyle.setVerticalLabelsWidth(0);
        // 背景
        graphView.setBackgroundColor(Color.DKGRAY);
        // グラフを拡大出来るようにするか
        graphView.setScalable(true);
        // グラフを拡大したときにスクロールできるようにするか
        graphView.setScrollable(true);

        // Legendを表示するかどうか
        graphView.setShowLegend(true);
// Legendを表示する幅
        graphViewStyle.setLegendWidth(250);
// 各項目のスペース
        graphViewStyle.setLegendSpacing(10);
// ボーダーの幅
        graphViewStyle.setLegendBorder(10);
// MarginBottom
        graphViewStyle.setLegendMarginBottom(100);

        graphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);


        graph.addView(graphView);





//        List<LifeItem> list = new Select().from(LifeItem.class).execute();
//
//        StringBuilder weightStr = new StringBuilder();
//        for (LifeItem i : list) {
//            weightStr.append(i.weight+"/");
//        }
//
//        String[] sprit= weightStr.toString().split("/");
//
//        int spLeng = sprit.length;
//
//
//        Line l = new Line();
//
//        LinePoint p;
//        for(int i = 0; i < spLeng; i ++) {
//            p = new LinePoint();
//            p.setX(i);
//            p.setY(Integer.parseInt(sprit[i]));
//            l.addPoint(p);
//        }
//
//        l.setColor(Color.parseColor("#9acd32"));
//        LineGraph graph = (LineGraph) view.findViewById(R.id.graph);
//        graph.addLine(l);
//        graph.setRangeY(0, 100);
//        graph.setLineToFill(0);
//

    }


}

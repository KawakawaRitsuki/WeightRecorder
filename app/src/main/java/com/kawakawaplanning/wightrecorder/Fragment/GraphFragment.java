package com.kawakawaplanning.wightrecorder.Fragment;

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
import com.kawakawaplanning.wightrecorder.LifeItem;
import com.kawakawaplanning.wightrecorder.R;

import java.util.List;


public class GraphFragment extends Fragment{

    FrameLayout graph;
    int s = 0;

    public GraphFragment() {
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

        int[] weight = new int[len];

        for (LifeItem a : list) {
            weight[s]= a.wight;
            s++;
        }

        s=0;

        int num = weight.length;
        LineGraphView graphView = new LineGraphView(getActivity(), "sample");

        GraphView.GraphViewData[] data = new GraphView.GraphViewData[num];

//        Object[] weight = list.toArray();

        for (int i = 0; i < num; i++) {
            data[i] = new GraphView.GraphViewData(i,weight[i]);
            if(weight[i] <= 60){

            }
        }

        //　線の太さ
        int thickness = 5;
        GraphViewSeries.GraphViewSeriesStyle style = new GraphViewSeries.GraphViewSeriesStyle(Color.BLUE, thickness);
        GraphViewSeries series = new GraphViewSeries("体重の推移", style, data);
        graphView.addSeries(series);

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

        graph.addView(graphView);





//        List<LifeItem> list = new Select().from(LifeItem.class).execute();
//
//        StringBuilder wightStr = new StringBuilder();
//        for (LifeItem i : list) {
//            wightStr.append(i.wight+"/");
//        }
//
//        String[] sprit= wightStr.toString().split("/");
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

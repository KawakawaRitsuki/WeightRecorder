package com.kawakawaplanning.wightrecorder.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.kawakawaplanning.wightrecorder.R;


public class GraphFragment extends Fragment {

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_graph, container, false);

        LineGraphView graphView = new LineGraphView(this, "SampleGraphView");
// 適当にデータを作成
        int num = 10;
        GraphView.GraphViewData[] data = new GraphView.GraphViewData[num];
        double a = 0.0d;
        for (int i = 0; i < num; i++) {
            a += 0.5d;
            data[i] = new GraphView.GraphViewData(i, Math.sin(a));
        }
// 線の太さ
        int thickness = 5;
        GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle = new GraphViewSeries.GraphViewSeriesStyle(Color.BLUE, thickness);
        GraphViewSeries graphViewSeries = new GraphViewSeries("Sample", graphViewSeriesStyle, data);
        graphView.addSeries(graphViewSeries);
        LinearLayout graphLayout = (LinearLayout)v.findViewById(R.id.graphLayout);
        graphLayout.addSubView(graphView);



        return v;
    }
}

package com.kawakawaplanning.wightrecorder.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.activeandroid.query.Select;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;
import com.kawakawaplanning.wightrecorder.LifeItem;
import com.kawakawaplanning.wightrecorder.R;

import java.util.List;


public class GraphFragment extends Fragment {

    LinearLayout graphLayout;
    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_graph, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<LifeItem> list = new Select().from(LifeItem.class).execute();

        StringBuilder wightStr = new StringBuilder();
        for (LifeItem i : list) {
            wightStr.append(i.wight+"/");
        }

        String[] sprit= wightStr.toString().split("/");

        int spLeng = sprit.length;


        Line l = new Line();

        LinePoint p;
        for(int i = 0; i < spLeng; i ++) {
            p = new LinePoint();
            p.setX(i);
            p.setY(Integer.parseInt(sprit[i]));
            l.addPoint(p);
        }

        l.setColor(Color.parseColor("#9acd32"));
        LineGraph graph = (LineGraph) view.findViewById(R.id.graph);
        graph.addLine(l);
        graph.setRangeY(0, 100);
        graph.setLineToFill(0);


    }


}

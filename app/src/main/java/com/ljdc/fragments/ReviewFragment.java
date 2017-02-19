package com.ljdc.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ljdc.R;
import com.ljdc.activitys.MainActivity;
import com.ljdc.activitys.WordExamActivity;
import com.ljdc.utils.Act;

import java.util.ArrayList;

/**
 * @Describe: TODO 首页
 * * * * ****** Created by ZOUXU ********
 */
public class ReviewFragment extends Fragment implements OnClickListener {

    // 标题栏
    TextView title;
    private View content = null;
    private LineChart lineChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_review, null);
        initView(inflater);
        return content;

    }

    private void initView(LayoutInflater inflater) {

        title = ((MainActivity) getActivity()).title;
        title.setText("复习");

        content.findViewById(R.id.wrodFirstExam).setOnClickListener(this);
        content.findViewById(R.id.wrodSecondExam).setOnClickListener(this);
        content.findViewById(R.id.wrodThirdExam).setOnClickListener(this);

        lineChart = (LineChart) content.findViewById(R.id.lineChart);
        initLineChart(lineChart);


    }

    private void initLineChart(LineChart chart) {
        chart.setDescription("");
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(5);

//        折线图 右坐标轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
//        rightAxis.setLabelCount(5);
//        rightAxis.setDrawGridLines(false);

        // set data
        chart.setData(generateDataLineData());

        // do not forget to refresh the chart
        // chart.invalidate();
        chart.animateX(750);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLineData() {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "词汇量");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(true);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

            LineDataSet d2 = new LineDataSet(e2, "熟词量");
        d2.setLineWidth(2.5f);
        d2.setCircleSize(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(true);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.wrodFirstExam:
                Toast.makeText(getContext(), "FIRST", Toast.LENGTH_SHORT).show();
                Act.toAct(getContext(), WordExamActivity.class);
                break;
            case R.id.wrodSecondExam:
                Toast.makeText(getContext(), "second", Toast.LENGTH_SHORT).show();
                Act.toAct(getContext(), WordExamActivity.class);
                break;
            case R.id.wrodThirdExam:
                Toast.makeText(getContext(), "third", Toast.LENGTH_SHORT).show();
                Act.toAct(getContext(), WordExamActivity.class);
                break;
        }
    }
}

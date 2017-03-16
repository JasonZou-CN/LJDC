package com.ljdc.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.j256.ormlite.dao.Dao;
import com.ljdc.R;
import com.ljdc.activitys.MainActivity;
import com.ljdc.activitys.WordExamActivity;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.*;
import com.ljdc.utils.Act;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//首页-进度
public class PlanFragment extends Fragment implements OnClickListener {

    // 标题栏
    TextView title;
    DBHelper dbHelper = null;
    private Dao dao = null;
    private View content = null;
    private TextView newWord, recoWord, knownWord, graspWord;
    private PieChart mChart;
    private Typeface tf;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_plan, null);
        initView(inflater);
        return content;

    }

    private void initView(LayoutInflater inflater) {

        title = ((MainActivity) getActivity()).title;
        title.setText("进度");

        newWord = (TextView) content.findViewById(R.id.newWord);
        recoWord = (TextView) content.findViewById(R.id.recoWord);
        knownWord = (TextView) content.findViewById(R.id.knownWord);
        graspWord = (TextView) content.findViewById(R.id.graspWord);

//        content.findViewById(R.id.wrodFirstExam).setOnClickListener(this);

        mChart = (PieChart) content.findViewById(R.id.pieChart);
        int[] data = new int[]{0, 0, 0, 0};
        try {
            String table = null;
            dbHelper = DBHelper.getHelper(getActivity());
            List<StudyPlan> studyPlen = dbHelper.getDao(StudyPlan.class).queryForAll();
            if (studyPlen != null && studyPlen.size() != 0) {
                StudyPlan studyPlan = studyPlen.get(0);
                table = studyPlan.currentLib;
                if (table != null && table.equals("lib1")) {
                    dao = dbHelper.getDao(LearnLib1Server.class);
                } else if (table != null && table.equals("lib2")) {
                    dao = dbHelper.getDao(LearnLib2Server.class);
                }
                if (dao != null) {
                    //TODO 已经学过的单词的词汇构成
                    data[0] = dao.queryBuilder().where().eq("graspLevel", "0").query().size() + studyPlan.leftNum;//生词
                    data[1] = dao.queryBuilder().where().eq("graspLevel", "1").query().size();//认识
                    data[2] = dao.queryBuilder().where().eq("graspLevel", "2").query().size();//熟悉
                    data[3] = dao.queryBuilder().where().eq("graspLevel", "3").query().size();//掌握
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        PieData mPieData = getPieData(data);
        initPieChartParam(mChart, mPieData);

        newWord.setText(data[0] + "");
        recoWord.setText(data[1] + "");
        knownWord.setText(data[2] + "");
        graspWord.setText(data[3] + "");


    }

    private void initPieChartParam(PieChart pieChart, PieData pieData) {

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(60f);  //孔洞半径

        pieChart.setDescription("词汇结构分析");

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setCenterText("四级词库");  //饼状图中间的文字

        pieChart.setRotationAngle(90); // 初始旋转角度

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置图例
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     */
    private PieData getPieData(int[] data) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        for (int i = 0; i < 4; i++) {
            xValues.add("生词");
            xValues.add("认识");
            xValues.add("熟悉");
            xValues.add("掌握");
        }

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        //TODO 传入参数 ，设置Y轴数据
        int num = data[0] + data[1] + data[2] + data[3];
        float newWord = data[0] * 1f / num;
        float recoWord = data[1] * 1f / num;
        float knownWord = data[2] * 1f / num;
        float controlWord = data[3] * 1f / num;

        yValues.add(new Entry(newWord, 0));
        yValues.add(new Entry(recoWord, 1));
        yValues.add(new Entry(knownWord, 2));
        yValues.add(new Entry(controlWord, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "词汇分类");
        pieDataSet.setSliceSpace(3f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();
        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xValues, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(10f);  //设置文字大小
        pieData.setValueTextColor(Color.WHITE);


        return pieData;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.wrodFirstExam:
                Toast.makeText(getContext(), "FIRST", Toast.LENGTH_SHORT).show();
                Act.toAct(getContext(), WordExamActivity.class);
                break;

        }
    }
}

package com.ljdc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.ljdc.R;
import com.ljdc.activitys.ChangePlanActivity;
import com.ljdc.activitys.StudyWordActivity;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.StudyPlan;
import com.ljdc.utils.Act;
import com.ljdc.utils.Utils;
import com.ljdc.views.RoundProgressBar;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/**
 * @Desc 首页-背单词
 * A simple {@link Fragment} subclass.
 */
public class HomeTabAFragment extends Fragment implements View.OnClickListener {

    StudyPlan studyPlan = null;
    //    private ViewGroup container;
    private RoundProgressBar progressRound;
    private Button changePlan;
    private TextView leftDays, planOfDay, currentLib, finishProgress;
    private DBHelper dbHelper;
    private Dao dao;
//    private Map<String, String> libs;

    public HomeTabAFragment() {
        // Required empty public constructor
        /*libs = new HashMap<>();
        libs.put("lib1", "英语四级核心词汇");
        libs.put("lib2", "中学英语核心词汇");*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.fragment_study_word, container, false);
        progressRound = (RoundProgressBar) container.findViewById(R.id.roundProgress);
        changePlan = (Button) container.findViewById(R.id.changePlan);
        leftDays = (TextView) container.findViewById(R.id.leftDays);
        finishProgress = (TextView) container.findViewById(R.id.finishProgress);
        planOfDay = (TextView) container.findViewById(R.id.planOfDay);
        currentLib = (TextView) container.findViewById(R.id.currentLib);

        changePlan.setOnClickListener(this);
        container.findViewById(R.id.study).setOnClickListener(this);
//        initData();
        return container;

    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart : " + "锚点");
        initData();

    }

    private void initData() {
        try {
            dbHelper = DBHelper.getHelper(getActivity());
            dao = dbHelper.getDao(StudyPlan.class);
            List<StudyPlan> studyPlen = dao.queryForAll();
            if (studyPlen.size() != 0) {
                studyPlan = studyPlen.get(0);
            }

            progressRound.setMax(studyPlan == null ? 100 : studyPlan.planOfDay);
            progressRound.setValue(studyPlan == null ? 0 : studyPlan.doOfDay);
            finishProgress.setText(studyPlan == null ? "无" : studyPlan.doOfDay + "/" + studyPlan.planOfDay);
            leftDays.setText(studyPlan == null ? "0" : Utils.getTwodateDayByDate(new Date(), studyPlan.finishDate) + "");
           /* if (studyPlan == null) {
                currentLib.setText("无");
            } else if (libs.containsKey(studyPlan.currentLib)) {
                currentLib.setText(libs.get(studyPlan.currentLib));
            } else {
                Log.d("HomeTabAFragment", "在App实例中没有匹配的词库名" + studyPlan.currentLib);
            }*/
            currentLib.setText(studyPlan == null ? "无" : studyPlan.currentLib);
            planOfDay.setText(studyPlan == null ? "0" : studyPlan.planOfDay + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.study:
                Act.toAct(getActivity(), StudyWordActivity.class);
                break;
            case R.id.changePlan:
                Bundle bundle = new Bundle();
                bundle.putSerializable("studyPlan", studyPlan);
                Act.toAct(getActivity(), ChangePlanActivity.class, bundle);
                break;
        }
    }
}

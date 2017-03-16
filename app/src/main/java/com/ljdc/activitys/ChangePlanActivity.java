package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.j256.ormlite.dao.Dao;
import com.ljdc.R;
import com.ljdc.app.App;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.*;
import com.ljdc.utils.DbUtil;
import com.ljdc.utils.Utils;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
@SuppressWarnings("ALL")
public class ChangePlanActivity extends Activity implements View.OnClickListener, NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener, NumberPicker.Formatter {
    private TextView tv_title;
    private View back;
    private NumberPicker dayPicker, wordsPicker;
    private TextView tv_totalNum, studyProgress, finishDate, planDesc, libName;
    //bundle参数
    private StudyPlan studyPlan = null, newStudyPlan = null;
    private HashMap<String, String> libs;
    private String[] daysArr;
    private String[] words;
    private TextView tv_sure;
    private View sure;
    private Libs libsInfo = null;

    private String lib;
    private int totalNum = 1000; //剩余单词
    private int leftNum;
    private Date finiDate;
    private int planOfDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_plan);
        libs = new HashMap<>();
        libs.put("lib1", "英语四级核心词汇");
        libs.put("lib2", "中学英语核心词汇");

        studyPlan = (StudyPlan) getIntent().getSerializableExtra("studyPlan");
        libsInfo = (Libs) getIntent().getSerializableExtra("libs");
//        lib = getIntent().getStringExtra("lib");
        initView();

    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.title_center_tv);
        tv_title.setText("我的计划");

        sure = findViewById(R.id.title_right_layout);
        sure.setVisibility(View.VISIBLE);
        sure.setOnClickListener(this);
        tv_sure = (TextView) findViewById(R.id.title_right_tv);
        tv_sure.setText("完成");

        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        back.setOnClickListener(this);

        tv_totalNum = (TextView) findViewById(R.id.totalNum);
        studyProgress = (TextView) findViewById(R.id.studyProgress);
        finishDate = (TextView) findViewById(R.id.finishDate);
        planDesc = (TextView) findViewById(R.id.planDesc);
        libName = (TextView) findViewById(R.id.libName);

        dayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        wordsPicker = (NumberPicker) findViewById(R.id.wordsPicker);

        initData();

    }

    private void initData() {
        words = new String[]{"10", "15", "20", "25", "30", "40", "50", "60", "70"};
        int index = 0;

        if (studyPlan != null) {
            totalNum = studyPlan.totalNum;
            tv_totalNum.setText(studyPlan.totalNum + "");

//            studyProgress.setText();
            String dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(studyPlan.finishDate);
            finishDate.setText(dateStr);
            String desc = getResources().getString(R.string.plan_desc);
            desc = String.format(desc, libs.get(studyPlan.currentLib), studyPlan.leftNum, dateStr);
            libName.setText(libs.get(studyPlan.currentLib));
            planDesc.setText(desc);

            double percent = 0;
            String table = studyPlan.currentLib;
            int totalNum = studyPlan.totalNum;
            percent = DbUtil.getPercent(table, totalNum, this);
            studyProgress.setText(new DecimalFormat("###.##%").format(percent));

            int num = 0;
            for (int i = 0; i < words.length; i++) {
                num = Integer.parseInt(words[i]);
                if (num == studyPlan.planOfDay) {
                    index = i;
                    break;
                }
            }
        } else if (libsInfo != null) {
            if (newStudyPlan == null) {
                newStudyPlan = new StudyPlan();
            }
            lib = libsInfo.tableName;
            totalNum = libsInfo.totalNum;
            tv_totalNum.setText(libsInfo.totalNum + "");
            finishDate.setText("暂无");
            String desc = getResources().getString(R.string.plan_desc);
            leftNum = DbUtil.getLeftNum(lib, totalNum, this);
            libName.setText(libsInfo.libName);
            desc = String.format(desc, libsInfo.libName, leftNum, " 暂无");
            planDesc.setText(desc);
            studyProgress.setText(new DecimalFormat("###.##%").format(DbUtil.getPercent(lib, totalNum, this)));
            index = 0;

            newStudyPlan.leftNum = leftNum;
            newStudyPlan.totalNum = totalNum;
            newStudyPlan.currentLib = lib;
        }

        wordsPicker.setDisplayedValues(words);
        wordsPicker.setMinValue(0);
        wordsPicker.setMaxValue(words.length - 1);

        wordsPicker.setValue(index);
        wordsPicker.setFormatter(this);
        wordsPicker.setOnValueChangedListener(this);
        wordsPicker.setOnScrollListener(this);

        List<String> daysList = new ArrayList<String>();
        int oneDay, days;
        for (String data : words
                ) {
            oneDay = Integer.parseInt(data);
            days = totalNum / oneDay;
            daysList.add(days + "");
        }
        daysArr = daysList.toArray(new String[daysList.size()]);

        dayPicker.setDisplayedValues(daysArr);
        dayPicker.setMinValue(0);
        dayPicker.setMaxValue(daysArr.length - 1);
        dayPicker.setValue(index);
        dayPicker.setFormatter(this);
        dayPicker.setOnValueChangedListener(this);
        dayPicker.setOnScrollListener(this);
    }

    @Override
    public void onClick(View view) {
        //TODO 设置处理逻辑
        switch (view.getId()) {
            case R.id.title_left_layout:
                finish();
                break;
            case R.id.title_right_layout:
                try {
                    Dao dao = DBHelper.getHelper(this).getDao(StudyPlan.class);
                    if (studyPlan != null) {
                        dao.update(studyPlan);
                    } else {
                        List<StudyPlan> plen = dao.queryForAll();
                        StudyPlan plan = null;
                        if (plen != null && plen.size() != 0) {
                            plan = plen.get(0);
                            plan.currentLib = libsInfo.tableName;
                            plan.totalNum = totalNum;
                            plan.leftNum = leftNum;
                            plan.finishDate = finiDate;
                            plan.planOfDay = Integer.parseInt(words[wordsPicker.getValue()]);

                            plan.status = 1;

                            dao.update(plan);

                        } else if (plen != null && plen.size() == 0) {
                            plan = new StudyPlan();
                            plan.planId = UUID.randomUUID();
                            int userId = Integer.parseInt(Utils.getPreference(this, Config.PARAM_USERID));
                            plan.user = new UserServer(userId);

                            plan.currentLib = libsInfo.tableName;
                            plan.totalNum = totalNum;
                            plan.leftNum = leftNum;
                            plan.finishDate = finiDate;
                            plan.planOfDay = Integer.parseInt(words[wordsPicker.getValue()]);

                            plan.status = 1;

                            dao.create(plan);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                break;

        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(
                this,
                "原来的值 " + oldVal + "--新值: "
                        + newVal, Toast.LENGTH_SHORT).show();
        switch (picker.getId()) {
            case R.id.dayPicker:
                wordsPicker.setValue(dayPicker.getValue());

                break;
            case R.id.wordsPicker:
                dayPicker.setValue(wordsPicker.getValue());

                break;
        }
        if (studyPlan != null && libs != null) {
            //两个选择对应ID一一对应
            int daysMill = Integer.parseInt(daysArr[newVal]);
            int daysWord = Integer.parseInt(words[newVal]);

            Date endDate = new Date(new Date().getTime() + daysMill * 1000 * 60 * 60 * 24);
            studyPlan.finishDate = endDate;
            studyPlan.planOfDay = daysWord;

            String dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(endDate);
            Log.d("ChangePlanActivity", dateStr);
            String desc = getResources().getString(R.string.plan_desc);
            desc = String.format(desc, libs.get(studyPlan.currentLib), studyPlan.leftNum, dateStr);
            planDesc.setText(desc);
        } else if (libsInfo != null) {
            //两个选择对应ID一一对应
            int daysMill = Integer.parseInt(daysArr[newVal]);
            int daysWord = Integer.parseInt(words[newVal]);

            Date endDate = new Date(new Date().getTime() + daysMill * 1000 * 60 * 60 * 24);
            finiDate = endDate;
            planOfDay = daysWord;

            String dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(endDate);
            Log.d("ChangePlanActivity", dateStr);
            String desc = getResources().getString(R.string.plan_desc);
            desc = String.format(desc, libsInfo.libName, leftNum, dateStr);
            planDesc.setText(desc);

        }

    }

    @Override
    public String format(int i) {
        return null;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
//                Toast.makeText(this, "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG)
//                        .show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
//                Toast.makeText(this, "不滑动", Toast.LENGTH_LONG).show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                Toast.makeText(this, "滑动中", Toast.LENGTH_LONG)
//                        .show();
                break;
        }
    }
}

package com.ljdc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.ljdc.R;
import com.ljdc.activitys.StudyWordActivity;
import com.ljdc.adapters.LibrarysAdapterLV;
import com.ljdc.database.DBHelper;
import com.ljdc.model.LibraryInfo;
import com.ljdc.pojo.LearnLib1Server;
import com.ljdc.pojo.LearnLib2Server;
import com.ljdc.pojo.Libs;
import com.ljdc.pojo.StudyPlan;
import com.ljdc.utils.Act;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc 首页-背单词
 * A simple {@link Fragment} subclass.
 */
public class HomeTabCFragment extends Fragment implements View.OnClickListener {

    private ViewGroup content;
    private ListView lv_librarys;
    private LayoutInflater inflater;
    private StudyPlan studyPlan;
    private List<Libs> libses;

    private ImageView libsIco;
    private TextView libsName, totalNum, studyProgress, finishDate;


    public HomeTabCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        content = (ViewGroup) inflater.inflate(R.layout.frag_home_sub_library, null);
        initView(content);
        initData();
        return content;

    }

    void initView(ViewGroup content) {
        lv_librarys = (ListView) content.findViewById(R.id.lv_librarys);

        libsIco = (ImageView) content.findViewById(R.id.libsIco);
        libsName = (TextView) content.findViewById(R.id.libsName);
        totalNum = (TextView) content.findViewById(R.id.totalNum);
        studyProgress = (TextView) content.findViewById(R.id.studyProgress);
        finishDate = (TextView) content.findViewById(R.id.finishDate);

    }

    private void initData() {
        DBHelper dbHelper;
        String libName = "";
        try {
            dbHelper = DBHelper.getHelper(getActivity());
            List<StudyPlan> studyPlen = dbHelper.getDao(StudyPlan.class).queryForAll();
            if (studyPlen == null || studyPlen.size() == 0) {
                studyPlan = null;
            } else {
                studyPlan = studyPlen.get(0);
            }
            libses = dbHelper.getDao(Libs.class).queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (studyPlan != null && libses != null & libses.size() != 0) {

            for (int i = 0; i < libses.size(); i++) {
                Libs libs = libses.get(i);
                if (libs.tableName.equals(studyPlan.currentLib)) {
                    libName = libs.libName;
                    libses.remove(libs);//不显示在列表中
                    break;
                }
            }
            libsName.setText(libName);
            totalNum.setText(studyPlan.totalNum + "");
            double percent = getPercent(studyPlan.currentLib, studyPlan.totalNum);
            studyProgress.setText(new DecimalFormat("###.##%").format(percent));
            String dateStr = new SimpleDateFormat("yyyy.MM.dd").format(studyPlan.finishDate);
            finishDate.setText(dateStr);
        } else {
            libsName.setText("暂无");
            totalNum.setText("暂无");
            studyProgress.setText("暂无");
            finishDate.setText("暂无");
        }

        if (libses != null && libses.size() != 0) {
            List<Libs> data = new ArrayList<Libs>();
            for (Libs libs : libses) {
                data.add(libs);
            }
            lv_librarys.setAdapter(new LibrarysAdapterLV(getContext(), data));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.study:
                Act.toAct(getActivity(), StudyWordActivity.class);
                break;
        }
    }

    /**
     * 获取已经学习的单词占比词库总数
     *
     * @param table
     * @param totalNum
     * @return
     */
    public double getPercent(String table, int totalNum) {
        double percent = 0;
        try {
            Dao dao = null;
            if (table.equals("lib1")) {
                dao = DBHelper.getHelper(getActivity()).getDao(LearnLib1Server.class);
            } else if (table.equals("lib2")) {
                dao = DBHelper.getHelper(getActivity()).getDao(LearnLib2Server.class);
            }
            int num = dao.queryBuilder().where().gt("graspLevel", 0).query().size();//大于0 ，学过的词汇
            percent = num * 1.0 / totalNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return percent;
    }
}

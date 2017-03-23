package com.ljdc.activitys;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.j256.ormlite.dao.Dao;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.*;
import com.ljdc.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * 词汇复习
 */
@SuppressWarnings("ALL")
public class WordExamActivity extends Activity implements View.OnClickListener {

    private final int maxProgress = 10;
    private int twoDaysAgoMills = 2 * 24 * 60 * 60 * 1000;
    private int threeDaysAgoMills = 3 * 24 * 60 * 60 * 1000;
    private int oneDaysAgoMills = 1 * 24 * 60 * 60 * 1000;
    private TextView rightView, title;
    private View back;
    private ProgressBar progressBar;
    private int progressNum = maxProgress;
    private Handler handler;
    private String currentLib;
    private int code;//复习操作码:生词+熟词+词汇巩固
    private DBHelper dbHelper;
    private Dao dao;

    private TextView word;
    private ImageView play;
    private TextView sectionA, sectionB, sectionC, sectionD;
    private ImageView ivA, ivB, ivC, ivD;
    private Button pass;
    private LinearLayout layoutA, layoutB, layoutC, layoutD;

    private List<LearnLib1Server> learnLib1s = null;
    private List<LearnLib2Server> learnLib2s = null;
    private int currentIndex = 0;
    private WordLibServer currentWord;
    private List<WordLibServer> wrongWords;
    private Dao wordDao;
    private LinearLayout[] layouts;
    private TextView[] sections;
    private ImageView[] ivs;
    private int rightPos; //正确选项位置
    private Dao lib1Dao;
    private Dao lib2Dao;
    private int defaultPron;//默认发音标记 0:Uk 1:Us

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_exam);
        Intent i = getIntent();
        currentLib = i.getStringExtra("currentLib");
        code = i.getIntExtra("code", -1);
        dbHelper = DBHelper.getHelper(this);
        initView();
        initData();
        String s = Utils.getPreference(this, Config.SP_DEFAULT_PRON);
        defaultPron = Integer.parseInt(s.equals("") ? "0" : s);
    }

    private void initView() {
        findViewById(R.id.title_right_layout).setVisibility(View.VISIBLE);
        rightView = (TextView) findViewById(R.id.title_right_tv);
        title = (TextView) findViewById(R.id.title_center_tv);
        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        back.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(maxProgress);
        progressBar.setProgress(maxProgress);

        play = (ImageView) findViewById(R.id.play);
        word = (TextView) findViewById(R.id.word);

        layoutA = (LinearLayout) findViewById(R.id.layoutA);
        layoutB = (LinearLayout) findViewById(R.id.layoutB);
        layoutC = (LinearLayout) findViewById(R.id.layoutC);
        layoutD = (LinearLayout) findViewById(R.id.layoutD);
        layouts = new LinearLayout[]{layoutA, layoutB, layoutC, layoutD};

        sectionA = (TextView) findViewById(R.id.sectionA);
        sectionB = (TextView) findViewById(R.id.sectionB);
        sectionC = (TextView) findViewById(R.id.sectionC);
        sectionD = (TextView) findViewById(R.id.sectionD);
        sections = new TextView[]{sectionA, sectionB, sectionC, sectionD};

        ivA = (ImageView) findViewById(R.id.iv_A);
        ivB = (ImageView) findViewById(R.id.iv_B);
        ivC = (ImageView) findViewById(R.id.iv_C);
        ivD = (ImageView) findViewById(R.id.iv_D);
        ivs = new ImageView[]{ivA, ivB, ivC, ivD};

        pass = (Button) findViewById(R.id.pass);

        play.setOnClickListener(this);
        layoutA.setOnClickListener(this);
        layoutB.setOnClickListener(this);
        layoutC.setOnClickListener(this);
        layoutD.setOnClickListener(this);
        pass.setOnClickListener(this);

    }

    private void initData() {
        switch (code) {
            case 0:
                title.setText("生词练习");
                break;
            case 1:
                title.setText("熟词巩固");
                break;
            case 2:
                title.setText("词汇量测试");
                break;
        }

    }

    /**
     * 执行计时器
     */
    private void scheduleTimer() {
        final Timer timer = new Timer();
        progressNum = maxProgress;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (progressNum > 0)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(--progressNum);
                        }
                    });
                else
                    timer.cancel();
            }
        }, 0, 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_layout:
                finish();
                break;
            case R.id.play:
                if (currentWord != null) {
                    MediaPlayer mp = new MediaPlayer();//构建MediaPlayer对象
                    try {
                        switch (defaultPron) {
                            case 0:
                                mp.setDataSource(currentWord.pronUrlEn);//设置文件路径
                                break;
                            case 1:
                                mp.setDataSource(currentWord.pronUrlUs);//设置文件路径
                                break;
                        }
                        mp.prepare();//准备
                        mp.start();//开始播放
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            case R.id.layoutA:
                ivA.setVisibility(View.VISIBLE);
                if (layoutA.getTag() != null) {
                    layoutA.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    layoutA.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                }
                pass.setText("下一个");
                break;
            case R.id.layoutB:
                ivB.setVisibility(View.VISIBLE);
                if (layoutB.getTag() != null) {
                    layoutB.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    layoutB.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                }
                pass.setText("下一个");
                break;
            case R.id.layoutC:
                ivC.setVisibility(View.VISIBLE);
                if (layoutC.getTag() != null) {
                    layoutC.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    layoutC.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                }
                pass.setText("下一个");
                break;
            case R.id.layoutD:
                ivD.setVisibility(View.VISIBLE);
                if (layoutD.getTag() != null) {
                    layoutD.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    layoutD.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                }
                pass.setText("下一个");
                break;
            case R.id.pass:
                currentIndex++;
                initExamData();
                if (pass.getText().toString().equals("下一个")) {
                    pass.setText("不认识");
                }
                break;
        }
    }
/*
    public void changeBckOnClick(View view) {
        View v = findViewById(view.getId());
        if (v.getTag() != null) {
//                    @android:color/holo_green_light
            v.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart:-------");
        try {
            if (currentLib.equals("lib1")) {
                dao = dbHelper.getDao(LearnLib1Server.class);
//                List<LearnLib1Server> list;
                switch (code) {
                    case 0:
                        learnLib1s = dao.queryBuilder().where().eq("graspLevel", 0).query();
                        break;
                    case 1:
                        learnLib1s = dao.queryBuilder().where().eq("graspLevel", 1).and().le("updataTime", new Date(new Date().getTime() - twoDaysAgoMills)).query();
                        break;
                    case 2:
                        learnLib1s = dao.queryBuilder().where().eq("graspLevel", 2).and().le("updataTime", new Date(new Date().getTime() - threeDaysAgoMills)).query();
                        break;
                }

            } else if (currentLib.equals("lib2")) {
                dao = dbHelper.getDao(LearnLib2Server.class);
//                List<LearnLib2Server> list;
                switch (code) {
                    case 0:
                        learnLib2s = dao.queryBuilder().where().eq("graspLevel", 0).query();
                        break;
                    case 1:
                        learnLib2s = dao.queryBuilder().where().eq("graspLevel", 1).and().le("updataTime", new Date(new Date().getTime() - twoDaysAgoMills)).query();
                        break;
                    case 2:
                        learnLib2s = dao.queryBuilder().where().eq("graspLevel", 2).and().le("updataTime", new Date(new Date().getTime() - threeDaysAgoMills)).query();
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (learnLib1s != null) {
            rightView.setText("1/" + learnLib1s.size());
        } else if (learnLib2s != null) {
            rightView.setText("1/" + learnLib2s.size());
        }
        initExamData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume:-------");
        /*if (learnLib1s != null) {
            rightView.setText("1/" + learnLib1s.size());
        } else if (learnLib2s != null) {
            rightView.setText("1/" + learnLib2s.size());
        }
        initExamData();*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop:-------");
    }

    /**
     * 生成测试数据
     */
    private void initExamData() {
        rightPos = (int) Math.floor(Math.random() * 4);//向下取整函数
        if (wrongWords == null) {
            wrongWords = new ArrayList<>();
        }
       /* List<Integer> wrongPosList = new ArrayList<>();
        int wrongPos[] = {-1,-1,-1};//TODO 初始化为-1
        int j = 0;
        while (j < 3) {
            int tmpPos = (int) Math.floor(Math.random() * 4);//向下取整函数
            if (tmpPos != rightPos && !wrongPosList.contains(tmpPos)) {
                wrongPosList.add(tmpPos);
                wrongPos[j++] = tmpPos;
            }
        }*/

        List<Integer> wrongIds = new ArrayList();
        int tmpId;
        int base = 0;
        int rightId = -1;
        try {
            if (learnLib1s != null) {
                if (lib1Dao == null)
                    lib1Dao = dbHelper.getDao(Lib1EnglishGrand4CoreServer.class);
                lib1Dao.refresh(learnLib1s.get(currentIndex).lib1);
                if (wordDao == null)
                    wordDao = dbHelper.getDao(WordLibServer.class);
                wordDao.refresh(learnLib1s.get(currentIndex).lib1.wordLibServer);
                currentWord = learnLib1s.get(currentIndex).lib1.wordLibServer;
            } else if (learnLib2s != null) {
                if (lib2Dao == null)
                    lib2Dao = dbHelper.getDao(Lib2MiddleSchoolServer.class);
                lib2Dao.refresh(learnLib2s.get(currentIndex).lib2);
                if (wordDao == null)
                    wordDao = dbHelper.getDao(WordLibServer.class);
                wordDao.refresh(learnLib2s.get(currentIndex).lib2.wordLib);
                currentWord = learnLib2s.get(currentIndex).lib2.wordLib;
            }

            rightId = currentWord.wordId;

            if (wordDao == null)
                wordDao = dbHelper.getDao(WordLibServer.class);
            base = (int) wordDao.countOf();

            wrongIds.clear();
            wrongWords.clear();

            int i = 0;
            while (i < 3) {//生成随机选项的ID
                tmpId = (int) Math.floor(Math.random() * base + 1);//向下取整函数
                if (tmpId != rightId && !wrongIds.contains(tmpId)) {
                    wrongIds.add(tmpId);
                    try {
                        WordLibServer word = (WordLibServer) wordDao.queryForId(tmpId);//随机Id可能不存在，查询出异常
                        if (word != null) {
                            wrongWords.add(word);
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //step 2:设置View信息
        for (int i = 0; i < 4; i++) {
            layouts[i].setBackground(getResources().getDrawable(R.drawable.bg_phone_auth));
            layouts[i].setTag(null);
            ivs[i].setVisibility(View.INVISIBLE);
            ivs[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_wrong));
        }
        layouts[rightPos].setTag(this);//正确的选项持有Tag
        ivs[rightPos].setImageDrawable(getResources().getDrawable(R.drawable.ic_right));

        int j = 0;
        for (int i = 0; i < 4; i++) {
            if (i != rightPos) {
                WordLibServer word = wrongWords.get(j++);
                sections[i].setText(word.pos1 + word.acceptation1);
            }
        }
        sections[rightPos].setText(currentWord.pos1 + currentWord.acceptation1);

        word.setText(currentWord.word);
        scheduleTimer();

    }
}

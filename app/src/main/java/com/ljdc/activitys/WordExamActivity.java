package com.ljdc.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.*;
import com.ljdc.utils.NetWorkUtils;
import com.ljdc.utils.Utils;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 词汇复习
 */
@SuppressWarnings("ALL")
public class WordExamActivity extends Activity implements View.OnClickListener {

    private static final int SCALE = 300;//词汇评估:固定预估值
    private final int maxProgress = 5;
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
    private List<LearnLib> learnLibList = null;
    private List<LearnLib1Server> learnLib1s = null;
    private List<LearnLib2Server> learnLib2s = null;
    private List<WordEvaluation> wordEvaluations = null;
    private int currentIndex = 0;
    private WordLibServer currentWord;
    private List<WordLibServer> wrongWords;
    private Dao wordDao;
    private LinearLayout[] layouts;
    private TextView[] sections;
    private ImageView[] ivs;
    private int rightPos; //正确选项位置
    private Dao<Lib,Integer> libDao;
    private Dao lib1Dao;
    private Dao lib2Dao;
    private int defaultPron;//默认发音标记 0:Uk 1:Us
    private int size; //菜单栏右侧：单词总数
    private Handler uiHandler;
    private Timer timer;//计时器，定级单词熟练度
    private Dao<LearnLib,Integer> learnLibD;
    private Dao learnLib1Dao;
    private Dao learnLib2Dao;
    private Dao wordEvaluationDao;
    private Map<Integer, AtomicInteger> levelRightCount = null;//词汇量评估:每一级题目，正确答题的数量
    private Map<Integer, Integer> LEVEL_TO_TOTAL_COUNT = null;//保存个层级词汇的总数
    private long startQuizTime;
    private long endQuizTime;
    private StudyPlan plan;
    private Dao<StudyPlan,Integer> planDao;

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

        try {
            planDao = dbHelper.getDao(StudyPlan.class);
            List<StudyPlan> l = planDao.queryForAll();
            if(l.size()!=0)
                plan = l.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        uiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0: //计时器停止
                        timer.cancel();
                        break;
                    case 1:
                        progressBar.setProgress(progressNum--);
                        break;
                    default:
                        break;
                }
            }
        };
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
                title.setText("阶段巩固");
                break;
            case 3:
                title.setText("词汇量评估");
                break;
        }
        if (code == 3) {//初始化层级常量

            LEVEL_TO_TOTAL_COUNT = new HashMap<>();
            LEVEL_TO_TOTAL_COUNT.put(1, 5);
            LEVEL_TO_TOTAL_COUNT.put(2, 10);
            LEVEL_TO_TOTAL_COUNT.put(3, 15);
            LEVEL_TO_TOTAL_COUNT.put(4, 15);
            LEVEL_TO_TOTAL_COUNT.put(5, 15);
            LEVEL_TO_TOTAL_COUNT.put(6, 10);
            LEVEL_TO_TOTAL_COUNT.put(7, 10);
            LEVEL_TO_TOTAL_COUNT.put(8, 10);
            LEVEL_TO_TOTAL_COUNT.put(9, 10);

            levelRightCount = new HashMap<>();
            levelRightCount.put(1, new AtomicInteger(0));
            levelRightCount.put(2, new AtomicInteger(0));
            levelRightCount.put(3, new AtomicInteger(0));
            levelRightCount.put(4, new AtomicInteger(0));
            levelRightCount.put(5, new AtomicInteger(0));
            levelRightCount.put(6, new AtomicInteger(0));
            levelRightCount.put(7, new AtomicInteger(0));
            levelRightCount.put(8, new AtomicInteger(0));
            levelRightCount.put(9, new AtomicInteger(0));
        }

    }

    /**
     * 执行计时器
     */
    private void scheduleTimer() {
        if (timer != null)
            timer.cancel();//执行了cancle()的timer不能再执行(schedule)新任务
        timer = new Timer();

        progressNum = maxProgress;
        timer.schedule(new TimerTask() {
            private int pos = currentIndex;

            @Override
            public void run() {
                if (progressNum >= 0)
                    uiHandler.sendEmptyMessage(1);
                else
                    uiHandler.sendEmptyMessage(0);
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
                int netType = new NetWorkUtils(this).getNetType();
                if (currentWord != null && netType != 0) {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.layoutA:
                ivA.setVisibility(View.VISIBLE);
                if (layoutA.getTag() != null) {//当前题目的正确选项
                    layoutA.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    if (code == 3) {//词汇量评估
                        addOneToRightLevelCount();
                    } else {
                        changeGraspToOther();
                    }

                } else {
                    layoutA.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                    if (code != 3)
                        changeGraspToZero();
                }
                pass.setText("下一个");
                break;
            case R.id.layoutB:
                ivB.setVisibility(View.VISIBLE);
                if (layoutB.getTag() != null) {
                    layoutB.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    if (code == 3) {//词汇量评估
                        addOneToRightLevelCount();
                    } else {
                        changeGraspToOther();
                    }
                } else {
                    layoutB.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                    if (code != 3)
                        changeGraspToZero();
                }
                pass.setText("下一个");
                break;
            case R.id.layoutC:
                ivC.setVisibility(View.VISIBLE);
                if (layoutC.getTag() != null) {
                    layoutC.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    if (code == 3) {//词汇量评估
                        addOneToRightLevelCount();
                    } else {
                        changeGraspToOther();
                    }
                } else {
                    layoutC.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                    if (code != 3)
                        changeGraspToZero();
                }
                pass.setText("下一个");
                break;
            case R.id.layoutD:
                ivD.setVisibility(View.VISIBLE);
                if (layoutD.getTag() != null) {
                    layoutD.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    if (code == 3) {//词汇量评估
                        addOneToRightLevelCount();
                    } else {
                        changeGraspToOther();
                    }
                } else {
                    layoutD.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    layouts[rightPos].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    ivs[rightPos].setVisibility(View.VISIBLE);
                    if (code != 3)
                        changeGraspToZero();
                }
                pass.setText("下一个");
                break;
            case R.id.pass:
                currentIndex++;
                if (size != 0 && currentIndex == size) {
                    if (code == 3) {
                        // TODO: 2017/4/4 计算评估的词汇量
                        int count = getEvaluationCount();
                        System.out.println(">>>>>>>>>>>>>>>预估词汇量:" + count + "<<<<<<<<<<<<<<<<");
                        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("词汇量评估").setMessage("您的词汇量预计为: " + count).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
                    } else {
                        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(title.getText().toString()).setMessage("测试已经完成").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
                    }

                    break;
                }
//                progressBar.setProgress(0);
                initExamData();
                if (pass.getText().toString().equals("下一个")) {
                    pass.setText("不认识");
                }
                break;
        }
    }

    /**
     * (算法)计算;评估词汇量
     */
    public int getEvaluationCount() {
        //结束时间
        endQuizTime = System.currentTimeMillis();
        //预估总词数
        float count = 0;
        Set<Integer> levels = levelRightCount.keySet();
        for (int i : levels) {
            //计算每一个级别答对的比率 = 每一个级别答对的题数 / 每一个级别总的题数
            float rightRate = levelRightCount.get(i).intValue() / (float) LEVEL_TO_TOTAL_COUNT.get(i);
            //如果题目属于第一级, 则将第一级答对的比率乘以固定的预估值作为该题预估词数
            if (i > 1) {
                //如果题目不属于第一级, 则将上一级答对的比率和本级相乘
                //然后用这个比率乘以固定的预估值作为该题预估词数
                int lastLevel = i - 1;
                float lastRightRate = levelRightCount.get(lastLevel).intValue()
                        / (float) LEVEL_TO_TOTAL_COUNT.get(lastLevel);
                //如果上一级全部答错, 则将上一级的答对比率固定设置为0.1
                if (lastRightRate == 0) {
                    lastRightRate = 0.1f;
                }
                rightRate *= lastRightRate;
            }
            count += SCALE * rightRate;
        }
        int cost = (480 - (int) (endQuizTime - startQuizTime) / 1000) * 20;
        //期望答题时间是8分钟。每落后一秒钟预估词数减20, 最多减量不超过9600
        if (cost < -9600) {
            cost = -9600;
        }
        //期望答题时间是8分钟。每提前一秒钟预估词数加20，最多加量不超过3600
        if (cost > 3600) {
            cost = 3600;
        }
        //假定做题最快时间不少于4分钟，如果少于四分钟，每少N秒预估词数就减去4800+N*20
        if (cost > 4800) {
            cost = -cost;
        }
        //返回预估值
        if ((count + cost) > 0) {
            return (int) (count + cost);
        }
        //如果如上算法最后获得的预估词数是负数，则去除负号取绝对值
        return (int) -(count + cost);
    }

    public void addOneToRightLevelCount() {
        int level = wordEvaluations.get(currentIndex).level;
        if (levelRightCount.containsKey(level)) {
            levelRightCount.get(level).incrementAndGet();
        } else {
            levelRightCount.put(level, new AtomicInteger(1));
        }
    }

    /**
     * 改变单词掌握度非0；
     */
    public void changeGraspToOther() {
        LearnLib learnLib = learnLibList.get(currentIndex);
        if (code == 0) {
            int graspLevel = 0;
            int useTime = maxProgress - progressBar.getProgress();
            if (useTime >= maxProgress) {
                graspLevel = 1;//认识
            } else if (useTime >= 0 && useTime < 4) {
                graspLevel = 2;//熟练
            }

            learnLib.graspLevel = graspLevel;
            plan.doOfDay++;
        } else if (learnLib.graspLevel < 3)
            learnLib.graspLevel++;
        learnLib.updateTime = new Date();
        learnLib.statusModify = 1;
        try {
            if (learnLibD == null)
                learnLibD = dbHelper.getDao(LearnLib.class);
            learnLibD.update(learnLib);
            planDao.update(plan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*if (learnLib1s != null) {
            LearnLib1Server learnLib1 = learnLib1s.get(currentIndex);
            if (code == 0) {
                int graspLevel = 0;
                int useTime = maxProgress - progressBar.getProgress();
                if (useTime == maxProgress) {
                    graspLevel = 1;//认识
                } else if (useTime >= 0 && useTime < 4) {
                    graspLevel = 2;//熟练
                }

                learnLib1.graspLevel = graspLevel;
            } else if (learnLib1.graspLevel < 3)
                learnLib1.graspLevel++;
            learnLib1.updataTime = new Date();
            learnLib1.statusModify = 1;
            try {
                if (learnLib1Dao == null)
                    learnLib1Dao = dbHelper.getDao(LearnLib1Server.class);
                learnLib1Dao.update(learnLib1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (learnLib2s != null) {
            LearnLib2Server learnLib2 = learnLib2s.get(currentIndex);
            if (code == 0) {
                int graspLevel = 0;
                int useTime = maxProgress - progressBar.getProgress();
                if (useTime == maxProgress) {
                    graspLevel = 1;//认识
                } else if (useTime >= 0 && useTime < 4) {
                    graspLevel = 2;//熟练
                }
                learnLib2.graspLevel = graspLevel;
            } else if (learnLib2.graspLevel < 3)
                learnLib2.graspLevel++;
            learnLib2.updataTime = new Date();
            learnLib2.statusModify = 1;
            try {
                if (learnLib2Dao == null)
                    learnLib2Dao = dbHelper.getDao(LearnLib2Server.class);
                learnLib2Dao.update(learnLib2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * 将单词掌握度设为0
     */
    public void changeGraspToZero() {
        LearnLib learnLib = learnLibList.get(currentIndex);
        learnLib.graspLevel = 0;
        learnLib.updateTime = new Date();
        learnLib.statusModify = 1;
        try {
            if (learnLibD == null)
                learnLibD = dbHelper.getDao(LearnLib.class);
            learnLibD.update(learnLib);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*if (learnLib1s != null) {
            LearnLib1Server learnLib1 = learnLib1s.get(currentIndex);
            learnLib1.graspLevel = 0;
            learnLib1.updataTime = new Date();
            learnLib1.statusModify = 1;
            try {
                if (learnLib1Dao == null)
                    learnLib1Dao = dbHelper.getDao(LearnLib1Server.class);
                learnLib1Dao.update(learnLib1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (learnLib2s != null) {
            LearnLib2Server learnLib2 = learnLib2s.get(currentIndex);
            learnLib2.graspLevel = 0;
            learnLib2.updataTime = new Date();
            learnLib2.statusModify = 1;
            try {
                if (learnLib2Dao == null)
                    learnLib2Dao = dbHelper.getDao(LearnLib2Server.class);
                learnLib2Dao.update(learnLib2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/


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
            //词汇评估
            if (code == 3) {
                // 初始化词汇预估数据
                if (wordEvaluationDao == null) {
                    wordEvaluationDao = dbHelper.getDao(WordEvaluation.class);
                }
                if (wordEvaluations == null) {
                    wordEvaluations = new ArrayList<>();
                }
//              wordEvaluations = wordEvaluationDao.queryBuilder().limit(100l).orderBy("level", true).query();
                List<WordEvaluation> list;
                list = wordEvaluationDao.queryBuilder().limit(5l).where().eq("level", 1).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(10l).where().eq("level", 2).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(15l).where().eq("level", 3).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(15l).where().eq("level", 4).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(15l).where().eq("level", 5).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(10l).where().eq("level", 6).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(10l).where().eq("level", 7).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(10l).where().eq("level", 8).query();
                wordEvaluations.addAll(list);
                list = wordEvaluationDao.queryBuilder().limit(10l).where().eq("level", 9).query();
                wordEvaluations.addAll(list);
                System.out.println(">>>>>>>>>>>>>>>>>>>>" + wordEvaluations.size() + "<<<<<<<<<<<<<<<:");

                //非词汇量评估
            } /*else if (currentLib.equals("lib1")) {
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
            }*/ else {
                //查找词库，
                Dao libD = DBHelper.getHelper(this).getDao(Lib.class);
                Where libW = libD.queryBuilder().selectColumns("libId").where().eq("libName", currentLib);
                Dao learnLibD = DBHelper.getHelper(this).getDao(LearnLib.class);
                switch (code) {
                    case 0:
                        learnLibList = learnLibD.queryBuilder().where().in("libId", libW.query()).and().eq("graspLevel", 0).query();
                        break;
                    case 1:
                        learnLibList = learnLibD.queryBuilder().where().in("libId", libW.query()).and().eq("graspLevel", 1).and().le("updateTime", new Date(new Date().getTime() - twoDaysAgoMills)).query();
                        break;
                    case 2:
                        learnLibList = learnLibD.queryBuilder().where().in("libId", libW.query()).and().eq("graspLevel", 2).and().le("updateTime", new Date(new Date().getTime() - threeDaysAgoMills)).query();
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*if (learnLib1s != null) {
            size = learnLib1s.size();
            rightView.setText("1/" + size);
        } else if (learnLib2s != null) {
            size = learnLib2s.size();
            rightView.setText("1/" + size);
        } */

        if (learnLibList != null) {
            size = learnLibList.size();
            rightView.setText("1/" + size);
        } else if (wordEvaluations != null) {
            size = wordEvaluations.size();
            rightView.setText("1/" + size);
        }
        initExamData();
        //开始测试时间
        if (code == 3)
            startQuizTime = System.currentTimeMillis();
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
            /*if (learnLib1s != null) {
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
            } */
            if(learnLibList!=null){
                if (libDao == null)
                    libDao = dbHelper.getDao(Lib.class);
                libDao.refresh(learnLibList.get(currentIndex).lib);
                if (wordDao == null)
                    wordDao = dbHelper.getDao(WordLibServer.class);
                wordDao.refresh(learnLibList.get(currentIndex).lib.wordLib);
                currentWord = learnLibList.get(currentIndex).lib.wordLib;
            }else if (wordEvaluations != null) {
                if (wordDao == null)
                    wordDao = dbHelper.getDao(WordLibServer.class);
                wordDao.refresh(wordEvaluations.get(currentIndex).wordLib);
                currentWord = wordEvaluations.get(currentIndex).wordLib;
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
        rightView.setText(currentIndex + 1 + "/" + size);
        scheduleTimer();

    }
}

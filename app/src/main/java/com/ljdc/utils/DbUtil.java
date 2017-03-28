package com.ljdc.utils;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/3/15
 * Time:16:04
 * Desc:略
 */
public class DbUtil {

    /**
     * 已学单词占比各词库
     *
     * @param table
     * @param totalNum
     * @param ctx
     * @return
     */
    public static double getPercent(String table, int totalNum, Context ctx) {
        double percent = 0;
        try {
            Dao dao = null;
            if (table.equals("lib1")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib1Server.class);
            } else if (table.equals("lib2")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib2Server.class);
            }
            int num = dao.queryBuilder().where().gt("graspLevel", 0).query().size();//大于0 ，学过的词汇
            percent = num * 1.0 / totalNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return percent;
    }

    /**
     * 剩余单词数
     *
     * @param table
     * @param ctx
     * @return
     */
    public static int getLeftNum(String table, int totalNum, Context ctx) {
        int num = 0;
        try {
            Dao dao = null;
            if (table.equals("lib1")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib1Server.class);
            } else if (table.equals("lib2")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib2Server.class);
            }
            num = dao.queryBuilder().where().gt("graspLevel", 0).query().size();//大于0 ，学过的词汇
            num = totalNum - num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 生成 新生词 加入到学习计划中
     *
     * @param ctx
     * @return
     */
    public static void initNewWordToLearn(Context ctx) {
        try {
            String lastDate = Utils.getPreference(ctx, Config.SP_INITNEWWORD_LASTDATE);
            String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            // TODO: 2017/3/28 增加新单词
            DBHelper dbHelper = DBHelper.getHelper(ctx);
            Dao planDao = dbHelper.getDao(StudyPlan.class);
            if (planDao.countOf() == 0 || lastDate.equals(nowDate)) {
                return;
            } else {
                Utils.savePreference(ctx, Config.SP_INITNEWWORD_LASTDATE, nowDate);//修改日期

                List<StudyPlan> list = planDao.queryBuilder().limit(1).query();
                StudyPlan plan = list.get(0);
                int count = plan.doOfDay;//需要添加的生词数量
                if (count == 0)
                    return;
                else {
                    //更新学习计划中的每日完成情况
                    plan.doOfDay = 0;
                    planDao.update(plan);
                }
                if (plan.currentLib.equals("lib1")) {
                    //词库1生成新生词
                    Dao learnLib1Dao = dbHelper.getDao(LearnLib1Server.class);
                    Dao lib1Dao = dbHelper.getDao(Lib1EnglishGrand4CoreServer.class);
                    QueryBuilder lib1QB = lib1Dao.queryBuilder();
                    QueryBuilder learnLib1QB = learnLib1Dao.queryBuilder();
                    learnLib1QB.selectColumns("lib1Id");
                    lib1QB.where().notIn("lib1Id", learnLib1QB);//查询没有加入学习计划的单词
                    List<Lib1EnglishGrand4CoreServer> lib1NotOfLearnLib1 = lib1QB.limit(count).query();
                    for (Lib1EnglishGrand4CoreServer lib1 : lib1NotOfLearnLib1) {
                        int uid = Integer.parseInt(Utils.getPreference(ctx, Config.PARAM_USERID));
                        LearnLib1Server learnLib1 = new LearnLib1Server();
                        learnLib1.learnLib1Id = Utils.newUUID();
                        learnLib1.user = new UserServer(uid);
                        learnLib1.lib1 = lib1;

                        learnLib1.statusModify = 0;
                        learnLib1.graspLevel = 0;
                        learnLib1.updataTime = new Date();
//                              learnLib1.anchor 默认值
                        learnLib1Dao.create(learnLib1);
                    }
                } else if (plan.currentLib.equals("lib2")) {
                    //词库2生成新生词
                    Dao learnLib2Dao = dbHelper.getDao(LearnLib1Server.class);
                    Dao lib2Dao = dbHelper.getDao(Lib1EnglishGrand4CoreServer.class);
                    QueryBuilder lib2QB = lib2Dao.queryBuilder();
                    QueryBuilder learnLib2QB = learnLib2Dao.queryBuilder();
                    learnLib2QB.selectColumns("lib1Id");
                    lib2QB.where().notIn("lib1Id", learnLib2QB);//查询没有加入学习计划的单词
                    List<Lib2MiddleSchoolServer> lib2NotOfLearnLib2 = lib2QB.limit(count).query();
                    for (Lib2MiddleSchoolServer lib2 : lib2NotOfLearnLib2) {
                        int uid = Integer.parseInt(Utils.getPreference(ctx, Config.PARAM_USERID));
                        LearnLib2Server learnLib2 = new LearnLib2Server();
                        learnLib2.learnLib2Id = Utils.newUUID();
                        learnLib2.user = new UserServer(uid);
                        learnLib2.lib2 = lib2;

                        learnLib2.statusModify = 0;
                        learnLib2.graspLevel = 0;
                        learnLib2.updataTime = new Date();
//                              learnLib2.anchor 默认值
                        learnLib2Dao.create(learnLib2);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

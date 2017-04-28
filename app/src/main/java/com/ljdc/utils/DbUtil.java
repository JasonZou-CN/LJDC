package com.ljdc.utils;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
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
     * @param currentLib
     * @param totalNum
     * @param ctx
     * @return
     */
    public static double getPercent(String currentLib, int totalNum, Context ctx) {
        double percent = 0;
        try {
            Dao dao = null;
            /*if (currentLib.equals("lib1")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib1Server.class);
            } else if (currentLib.equals("lib2")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib2Server.class);
            }*/
            dao = DBHelper.getHelper(ctx).getDao(LearnLib.class);
            Dao<Lib, Integer> libD = DBHelper.getHelper(ctx).getDao(Lib.class);
            Where<Lib, Integer> libW = libD.queryBuilder().selectColumns("libId").where().eq("libName", currentLib);
            List<Lib> list = libW.query();
            int num = dao.queryBuilder().where().in("libId", list).and().gt("graspLevel", 0).query().size();//大于0 ，学过的词汇
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
    public static int getLeftNum(String currentLib, int totalNum, Context ctx) {
        int num = 0;
        try {
            Dao dao = null;
            /*if (currentLib.equals("lib1")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib1Server.class);
            } else if (currentLib.equals("lib2")) {
                dao = DBHelper.getHelper(ctx).getDao(LearnLib2Server.class);
            }*/
            dao = DBHelper.getHelper(ctx).getDao(LearnLib.class);
            Dao<Lib, Integer> libD = DBHelper.getHelper(ctx).getDao(Lib.class);
            Where<Lib, Integer> libW = libD.queryBuilder().selectColumns("libId").where().eq("libName", currentLib);
            List<Lib> list = libW.query();
            num = dao.queryBuilder().where().in("libId", list).and().gt("graspLevel", 0).query().size();//大于0 ，学过的词汇
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

                //按照词库生成新生词
                Dao<LearnLib,Integer> learnLibDao = dbHelper.getDao(LearnLib.class);
                Dao<Lib,Integer> libDao = dbHelper.getDao(Lib.class);
                QueryBuilder libQB = libDao.queryBuilder();
                QueryBuilder learnLibQB = learnLibDao.queryBuilder();
                learnLibQB.selectColumns("libId");
                libQB.where().notIn("libId", learnLibQB).and().eq("libName",plan.currentLib);//查询没有加入学习计划的单词
                List<Lib> libNotOfLearnLib = libQB.limit(count).query();
                for (Lib lib : libNotOfLearnLib) {
                    int uid = Integer.parseInt(Utils.getPreference(ctx, Config.PARAM_USERID));
                    LearnLib learnLib = new LearnLib();
                    learnLib.learnLibId = Utils.newUUID();
                    learnLib.user = new UserServer(uid);
                    learnLib.lib = lib;

                    learnLib.statusModify = 0;
                    learnLib.graspLevel = 0;
                    learnLib.updateTime = new Date();
//                              learnLib1.anchor 默认值
                    learnLibDao.create(learnLib);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

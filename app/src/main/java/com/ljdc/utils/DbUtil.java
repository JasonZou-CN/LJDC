package com.ljdc.utils;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.LearnLib1Server;
import com.ljdc.pojo.LearnLib2Server;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/3/15
 * Time:16:04
 * Desc:略
 */
public class DbUtil {

    /**已学单词占比各词库
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


}

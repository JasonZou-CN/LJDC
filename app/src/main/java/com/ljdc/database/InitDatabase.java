package com.ljdc.database;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.ljdc.app.Config;
import com.ljdc.pojo.UserServer;
import com.ljdc.pojo.WordDevelopmentServer;
import com.ljdc.utils.Utils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/25 0025
 * Time:下午 10:05
 * Desc:根据Asset文件初始化数据库
 */
public class InitDatabase {
    private static Gson gson;
    static {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public static void initUser(Context ctx) {
        List<UserServer> users;
        Dao dao;
        users = gson.fromJson(Utils.getStringFromAssets(Config.USER, ctx), new TypeToken<List<UserServer>>() {
        }.getType());
        try {
            dao = DBHelper.getHelper(ctx).getDao(UserServer.class);
            for (UserServer user : users) {
                dao.create(user);
            }
            Log.d("LoginActivity", "queryForAll().size():" + dao.queryForAll().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initWordDevelopment(Context ctx) {
        List<WordDevelopmentServer> datas;
        Dao dao;
        datas = gson.fromJson(Utils.getStringFromAssets(Config.WORD_DEVELOPMENT, ctx), new TypeToken<List<WordDevelopmentServer>>() {
        }.getType());
        try {
            dao = DBHelper.getHelper(ctx).getDao(WordDevelopmentServer.class);
            for (WordDevelopmentServer data : datas) {
                dao.create(data);
            }
            Log.d("init database :", "queryForAll().size():" + dao.queryForAll().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

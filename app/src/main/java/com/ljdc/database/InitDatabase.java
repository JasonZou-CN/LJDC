package com.ljdc.database;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.ljdc.app.Config;
import com.ljdc.pojo.*;
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
        if (users == null || users.size() == 0) {
            return;
        }
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
                System.out.println("wordDev : " + data.toString());
                UserServer user = new UserServer(data.userId);
                dao.create(data);
            }
            Log.d("init database :", "queryForAll().size():" + dao.queryForAll().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 词库初始化
     *
     * @param ctx
     */
    public static void initWordLib(Context ctx) {
        List<WordLibServer> datas;
        Dao dao;
        datas = gson.fromJson(Utils.getStringFromAssets(Config.WORD_LIB, ctx), new TypeToken<List<WordLibServer>>() {
        }.getType());
        try {
            dao = DBHelper.getHelper(ctx).getDao(WordLibServer.class);
            for (WordLibServer data : datas) {
                dao.create(data);
            }
            Log.d("init database :", "queryForAll().size():" + dao.queryForAll().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 词库1初始化
     *
     * @param ctx
     */
    public static void initLib1(Context ctx) {
        List<Lib1EnglishGrand4CoreServer> datas;
        WordLibServer word = new WordLibServer();
        Dao dao;
        datas = gson.fromJson(Utils.getStringFromAssets(Config.WORD_LIB1, ctx), new TypeToken<List<Lib1EnglishGrand4CoreServer>>() {
        }.getType());
        try {
            dao = DBHelper.getHelper(ctx).getDao(Lib1EnglishGrand4CoreServer.class);
            for (Lib1EnglishGrand4CoreServer data : datas) {
                word.wordId = data.wordId;
                data.wordLibServer = word;//建立关系
                dao.create(data);
            }
            Log.d("init database :", "queryForAll().size():" + dao.queryForAll().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 词库2初始化
     *
     * @param ctx
     */
    public static void initLib2(Context ctx) {
        List<Lib2MiddleSchoolServer> datas;
        WordLibServer word = new WordLibServer();
        Dao dao;
        datas = gson.fromJson(Utils.getStringFromAssets(Config.WORD_LIB2, ctx), new TypeToken<List<Lib2MiddleSchoolServer>>() {
        }.getType());
        try {
            dao = DBHelper.getHelper(ctx).getDao(Lib2MiddleSchoolServer.class);
            for (Lib2MiddleSchoolServer data : datas) {
                word.wordId = data.wordId;
                data.wordLib = word;//建立关系
                dao.create(data);
            }
            Log.d("init database :", "queryForAll().size():" + dao.queryForAll().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

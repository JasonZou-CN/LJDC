package com.ljdc.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import com.ljdc.app.App;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.model.Message;
import com.ljdc.pojo.*;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/3/4 0004
 * Time:下午 5:02
 * Desc: CS数据库同步操作类
 */
public class DataSyncUtil implements Response.Listener<String> {
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //java.util.Date的时间格式
                .create();
    }

    Dao dao;
    Where whereQ;
    Map<String, String> parms;
    private Context ctx;

    /**
     * 同步用户数据
     */
    public void syncUserData(Context ctx) {
        String maxAnchor = "";
        this.ctx = ctx;
        try {

            //单词学习库1
            dao = DBHelper.getHelper(ctx).getDao(LearnLib1Server.class);
            whereQ = dao.queryBuilder().where().lt("statusModify", 9);
            List<LearnLib1Server> learnLib1Servers = whereQ.query();
            maxAnchor = selectMaxAnchor("learn_lib1");
            maxAnchor = maxAnchor == null ? "" : maxAnchor;
            Log.d("DataSyncUtil maxAnchor:", maxAnchor);
            Log.d("DataSyncUtil", "learnLib1Servers.size():" + learnLib1Servers.size());
            for (LearnLib1Server data : learnLib1Servers) {//处理GSON解析过程中的异常
                data.userId = data.user.userId;
                data.lib1Id = data.lib1.lib1Id;
                data.user = null;
                data.lib1 = null;
            }
            Log.d("DataSyncUtil json:", gson.toJson(learnLib1Servers));
            parms = new HashMap<>();//参数
            parms.put(Config.PARAM_MAXANCHOR, maxAnchor);
            parms.put(Config.PARAM_SYNCJSONDATA, gson.toJson(learnLib1Servers));
            parms.put(Config.PARAM_USERID, Utils.getPreference(ctx, Config.PARAM_USERID));
            new VolleyPostRequest(ctx).postRequest(parms, Config.SYNC_LEARN_LIB1_URL, this);

            //单词学习库2
            dao = DBHelper.getHelper(ctx).getDao(LearnLib2Server.class);
            whereQ = dao.queryBuilder().where().lt("statusModify", 9);
            List<LearnLib2Server> learnLib2Servers = whereQ.query();
            maxAnchor = selectMaxAnchor("learn_lib2");
            maxAnchor = maxAnchor == null ? "" : maxAnchor;
            Log.d("DataSyncUtil maxAnchor:", maxAnchor);
            Log.d("DataSyncUtil", "learnLib2Servers.size():" + learnLib2Servers.size());
            for (LearnLib2Server data : learnLib2Servers) {//处理GSON解析过程中的异常
                data.userId = data.user.userId;
                data.lib2Id = data.lib2.lib2Id;
                data.user = null;
                data.lib2 = null;
            }
            Log.d("DataSyncUtil json:", gson.toJson(learnLib2Servers));
            parms = new HashMap<>();//参数
            parms.clear();
            parms.put(Config.PARAM_MAXANCHOR, maxAnchor);
            parms.put(Config.PARAM_SYNCJSONDATA, gson.toJson(learnLib2Servers));
            parms.put(Config.PARAM_USERID, Utils.getPreference(ctx, Config.PARAM_USERID));
            new VolleyPostRequest(ctx).postRequest(parms, Config.SYNC_LEARN_LIB2_URL, this);


            //单词进展情况表
            dao = DBHelper.getHelper(ctx).getDao(WordDevelopmentServer.class);
            whereQ = dao.queryBuilder().where().lt("statusModify", 9);
            List<WordDevelopmentServer> wordDevelopmentServers = whereQ.query();
            maxAnchor = selectMaxAnchor("word_development");
            maxAnchor = maxAnchor == null ? "" : maxAnchor;
            Log.d("DataSyncUtil maxAnchor:", maxAnchor);
            Log.d("DataSyncUtil", "wordDevelopmentServers.size():" + wordDevelopmentServers.size());
            for (WordDevelopmentServer data : wordDevelopmentServers) {
                data.userId = data.user.userId;
                data.user = null;
            }
            Log.d("DataSyncUtil json:", gson.toJson(wordDevelopmentServers));
            parms = new HashMap<>();//参数
            parms.clear();
            parms.put(Config.PARAM_MAXANCHOR, maxAnchor);//参数为null，会导致请求不能成功
            parms.put(Config.PARAM_SYNCJSONDATA, gson.toJson(wordDevelopmentServers));
            parms.put(Config.PARAM_USERID, Utils.getPreference(ctx, Config.PARAM_USERID));
            new VolleyPostRequest(ctx).postRequest(parms, Config.SYNC_WORD_DEVELOP_URL, this);

            //单词学习计划
            dao = DBHelper.getHelper(ctx).getDao(StudyPlan.class);
            whereQ = dao.queryBuilder().where().lt("status", 9);
            List<StudyPlan> studyPlen = whereQ.query();
            maxAnchor = selectMaxAnchor("study_plan");
            maxAnchor = maxAnchor == null ? "" : maxAnchor;
            Log.d("DataSyncUtil maxAnchor:", maxAnchor);
            Log.d("DataSyncUtil", "studyPlen.size():" + studyPlen.size());
            for (StudyPlan data : studyPlen) {
                data.userId = data.user.userId;
                data.user = null;
            }
            Log.d("DataSyncUtil json:", gson.toJson(studyPlen));
            parms = new HashMap<>();//参数
            parms.clear();
            parms.put(Config.PARAM_MAXANCHOR, maxAnchor);//参数为null，会导致请求不能成功
            parms.put(Config.PARAM_SYNCJSONDATA, gson.toJson(studyPlen));
            parms.put(Config.PARAM_USERID, Utils.getPreference(ctx, Config.PARAM_USERID));
            new VolleyPostRequest(ctx).postRequest(parms, Config.SYNC_STUDY_PLAN_URL, this);

            //词库信息
            maxAnchor = selectMaxAnchor("libs");
            maxAnchor = maxAnchor == null ? "" : maxAnchor;
            Log.d("DataSyncUtil maxAnchor:", maxAnchor);
            parms = new HashMap<>();//参数
            parms.put(Config.PARAM_MAXANCHOR, maxAnchor);//参数为null，会导致请求不能成功
            new VolleyPostRequest(ctx).postRequest(parms, Config.SYNC_LIBS_URL, this);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResponse(String response) {
        try {
            response = new String(response.getBytes(Config.DEFAULT_STRING_CHARSET), Config.UTF8_CHARSET);
            //TODO 解析返回值
            Message message = new Gson().fromJson(response, Message.class);
            int code = message.getCode();
            String msg = message.getMsg();
            switch (code) {
                case 201://同步学习-词库1
                    List<LearnLib1Server> learnLib1Servers = gson.fromJson(msg, new TypeToken<List<LearnLib1Server>>() {
                    }.getType());
                    updateLearnLib1FromServer(learnLib1Servers, ctx);
                    App.WORDDEV_CHANGED = true;
                    break;
                case 202://同步学习-词库2
                    List<LearnLib2Server> learnLib2Servers = gson.fromJson(msg, new TypeToken<List<LearnLib2Server>>() {
                    }.getType());
                    updateLearnLib2FromServer(learnLib2Servers, ctx);
                    App.WORDDEV_CHANGED = true;
                    break;
                case 203://同步学习-进展
                    List<WordDevelopmentServer> wordDevelopmentServers = gson.fromJson(msg, new TypeToken<List<WordDevelopmentServer>>() {
                    }.getType());
                    updateWordDevelopmentFromServer(wordDevelopmentServers, ctx);
                    App.WORDDEV_CHANGED = true;
                    break;
                case 204://学习计划
                    List<StudyPlan> studyPlen = gson.fromJson(msg, new TypeToken<List<StudyPlan>>() {
                    }.getType());
                    updateStudyPlanFromServer(studyPlen, ctx);
                    break;
                case 205://词库信息
                    List<Libs> libses = gson.fromJson(msg, new TypeToken<List<Libs>>() {
                    }.getType());
                    updateLibsFromServer(libses, ctx);
                    App.WORDDEV_CHANGED = true;
                    break;
            }
            Toast.makeText(ctx, "同步成功", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * LearnLib1
     * 更新本地数据库
     *
     * @param learnLib1s
     * @param ctx
     */
    public void updateLearnLib1FromServer(List<LearnLib1Server> learnLib1s, Context ctx) {
        try {
            if (learnLib1s.size() <= 0)
                return;
            Dao dao = DBHelper.getHelper(ctx).getDao(learnLib1s.get(0).getClass());
            for (LearnLib1Server data : learnLib1s) {
                if (data.oldId != null && !data.oldId.isEmpty()) {
                    dao.delete(new LearnLib1Server(UUID.fromString(data.oldId)));
                }
                //关系
                data.user = new UserServer(data.userId);
                data.lib1 = new Lib1EnglishGrand4CoreServer(data.lib1Id);
                dao.createOrUpdate(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * LearnLib2
     * 更新本地数据库
     *
     * @param learnLib2s
     * @param ctx
     */
    public void updateLearnLib2FromServer(List<LearnLib2Server> learnLib2s, Context ctx) {

        try {
            if (learnLib2s.size() <= 0) {
                return;
            }
            Dao dao = DBHelper.getHelper(ctx).getDao(learnLib2s.get(0).getClass());
            for (LearnLib2Server data : learnLib2s) {
                if (data.oldId != null && !data.oldId.isEmpty()) {
                    dao.delete(new LearnLib2Server(UUID.fromString(data.oldId)));
                }
                //建立关系
                data.user = new UserServer(data.userId);
                data.lib2 = new Lib2MiddleSchoolServer(data.lib2Id);
                dao.createOrUpdate(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * WordDevelopment表
     * 更新本地数据库
     *
     * @param datas
     * @param ctx
     */
    public void updateWordDevelopmentFromServer(List<WordDevelopmentServer> datas, Context ctx) {

        try {
            if (datas.size() <= 0) {
                return;
            }
            Dao dao = DBHelper.getHelper(ctx).getDao(datas.get(0).getClass());
            for (WordDevelopmentServer data : datas) {
                if (data.oldId != null && !data.oldId.isEmpty()) {
                    dao.delete(new WordDevelopmentServer(UUID.fromString(data.oldId)));
                }
                //设置主键 ，建立关系
                data.user = new UserServer(data.userId);
                dao.createOrUpdate(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * StudyPlan表
     * 更新本地数据库
     *
     * @param datas
     * @param ctx
     */
    public void updateStudyPlanFromServer(List<StudyPlan> datas, Context ctx) {
        try {
            if (datas.size() <= 0) {
                return;
            }
            Dao dao = DBHelper.getHelper(ctx).getDao(datas.get(0).getClass());
            for (StudyPlan data : datas) {
                if (data.oldId != null && !data.oldId.isEmpty()) {
                    dao.delete(new StudyPlan(UUID.fromString(data.oldId)));
                }
                //设置主键 ，建立关系
                data.user = new UserServer(data.userId);
                dao.createOrUpdate(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLibsFromServer(List<Libs> datas, Context ctx) {
        try {
            if (datas == null || datas.size() <= 0) {
                return;
            }
            Dao dao = DBHelper.getHelper(ctx).getDao(datas.get(0).getClass());
            for (Libs data : datas) {
                dao.createOrUpdate(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 调用android的SQLiteDatabase进行max(anchor)查询
     *
     * @param tbName
     * @return 表为空返回NULL
     */
    public String selectMaxAnchor(String tbName) {
        SQLiteDatabase db = DBHelper.getHelper(ctx).getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(anchor) from " + tbName, null);
        cursor.moveToNext();
        String maxAnchor = cursor.getString(0);
        cursor.close();
//            db.close();
        return maxAnchor;
    }
}

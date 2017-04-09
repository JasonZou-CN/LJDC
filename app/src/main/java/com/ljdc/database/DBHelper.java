package com.ljdc.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ljdc.pojo.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：数据库帮助类
 *
 * @author devilwwj
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    /**
     * 数据库名字
     */
    private static final String DB_NAME = "ljdc.db";
    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;
    private static DBHelper instance;
    /**
     * 用来存放Dao的地图
     */
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    /**
     * 构造方法
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 获取单例
     *
     * @param context
     * @return
     */
    public static synchronized DBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }


    /**
     * 这里创建表
     */
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        // 创建表
        try {
            TableUtils.createTable(connectionSource, UserServer.class);
            TableUtils.createTable(connectionSource, WordLibServer.class);
            TableUtils.createTable(connectionSource, WordDevelopmentServer.class);
            TableUtils.createTable(connectionSource, Lib1EnglishGrand4CoreServer.class);
            TableUtils.createTable(connectionSource, Lib2MiddleSchoolServer.class);
            TableUtils.createTable(connectionSource, LearnLib1Server.class);
            TableUtils.createTable(connectionSource, LearnLib2Server.class);
            TableUtils.createTable(connectionSource, StudyPlan.class);
            TableUtils.createTable(connectionSource, Libs.class);
            TableUtils.createTable(connectionSource, WordEvaluation.class);
            TableUtils.createTable(connectionSource, Lib.class);


            //TODO 这里拿到的SQLiteDatabase  execSQL()只能执行一条SQL语句
            /*sqliteDatabase.execSQL("INSERT INTO `user` (`user_id`, `email`, `head_image_url`, `nickname`, `password`, `phone`) VALUES (1, 'frank_zouxu@163.com', '', 'jasonzou', '123', '18380430507');\n");
            sqliteDatabase.execSQL("INSERT INTO `user` (`user_id`, `email`, `head_image_url`, `nickname`, `password`, `phone`) VALUES (2, 'sfdsff@123.com', '', 'safdf', '124', '798478');\n");
            sqliteDatabase.execSQL("INSERT INTO `user` (`user_id`, `email`, `head_image_url`, `nickname`, `password`, `phone`) VALUES (3, '1245@134.com', NULL, '萨达', '7949', '1234523');\n");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里进行更新表操作
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
                          int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, UserServer.class, true);
            TableUtils.dropTable(connectionSource, WordLibServer.class, true);
            TableUtils.dropTable(connectionSource, WordDevelopmentServer.class, true);
            TableUtils.dropTable(connectionSource, Lib1EnglishGrand4CoreServer.class, true);
            TableUtils.dropTable(connectionSource, Lib2MiddleSchoolServer.class, true);
            TableUtils.dropTable(connectionSource, LearnLib1Server.class, true);
            TableUtils.dropTable(connectionSource, LearnLib2Server.class, true);
            TableUtils.dropTable(connectionSource, StudyPlan.class, true);
            TableUtils.dropTable(connectionSource, Libs.class, true);
            TableUtils.dropTable(connectionSource, WordEvaluation.class, true);
            TableUtils.dropTable(connectionSource, Lib.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过类来获得指定的Dao
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }


    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }


}

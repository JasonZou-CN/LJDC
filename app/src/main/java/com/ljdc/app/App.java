package com.ljdc.app;

import android.app.Application;
import android.content.Context;
import cn.smssdk.SMSSDK;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/24 0024
 * Time:上午 1:46
 * Desc:略
 */
public class App extends Application {
    private static Context context;
    public static boolean WORDDEV_CHANGED = false;//数据库状态
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

    }

    public static Context getCtx(){
        return context;
    }
}

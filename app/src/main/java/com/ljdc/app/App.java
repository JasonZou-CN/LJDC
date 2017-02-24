package com.ljdc.app;

import android.app.Application;
import android.content.Context;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/24 0024
 * Time:上午 1:46
 * Desc:略
 */
public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getCtx(){
        return context;
    }
}

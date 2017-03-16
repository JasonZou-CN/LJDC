package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import com.ljdc.database.InitDatabase;

/**
 * 用来做数据库单元测试
 */
public class UnitTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 测试代码
        new Thread(new Runnable() {
            @Override
            public void run() {
//                InitDatabase.initWordDevelopment(UnitTestActivity.this);
            }
        }).start();
    }


}

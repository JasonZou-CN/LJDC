package com.ljdc.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @Describe: TODO Activity跳转工具类
 * * * *
 * ****** Created by ZK ********
 * @Date: 2014/03/24 16:03
 * @Copyright: 2014 成都呐喊信息 All rights reserved.
 * @version: 1.0
 */
public class Act {
    public static void toAct(Context mContext, Class<?> cls) {
        toAct(mContext, cls, null);
    }

    public static void toAct(Context mContext, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    public static void toActClearTop(Context mContext, Class<?> cls) {
        toActClearTop(mContext, cls, null);
    }

    public static void toActClearTop(Context mContext, Class<?> cls,
                                     Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }
}

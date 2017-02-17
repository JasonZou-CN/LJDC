package com.ljdc.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @Describe: TODO
 * * * *
 * ****** Created by ZK ********
 * @Date: 2014/10/20 18:40
 * @Copyright: 2014 成都呐喊信息 All rights reserved.
 * @version: 1.0
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        showShort(context, context.getString(message));
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        showLong(context, context.getString(message));
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration 毫秒
     */
    public static void showForTime(Context context, CharSequence message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showForTime(Context context, int message, int duration) {
        showForTime(context, context.getString(message), duration);
    }

    /**
     * Hide the toast, if any.
     */
    public static void hideToast() {
        if (null != toast) toast.cancel();
    }
}

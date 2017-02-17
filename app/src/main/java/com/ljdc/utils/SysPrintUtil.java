package com.ljdc.utils;

/**
 * Created by Administrator on 2014/7/9.
 */
public class SysPrintUtil {
    private static Boolean printBln = true;

    public static void pt(String strTag, String strPrint) {
        if (printBln) {
            System.out.println(strTag + "：" + strPrint);
        }
    }

    public static void pt(String strTag, String strPrint, String strTag2, String strPrint2) {
        if (printBln) {
            System.out.println(strTag + "：" + strPrint + "  &&  " + strTag2 + "：" + strPrint2);
        }
    }
}

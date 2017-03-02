package com.ljdc.app;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/1/10 0010
 * Time:上午 12:00
 * Desc:略
 */
public class Config {
    /*注：完整URL为（http://dict-co.iciba.com/api/dictionaryphp?w=love&key=25C40D77809C22A3977F8C4B4CF3056D）
    参数1:w;（单词）
    参数2:key;（开发者秘钥）
    */
    public static String SEARCH_WORD_API_URL = "http://dict-co.iciba.com/api/dictionary.php";
    public static String SEARCH_WORD_API_KEY = "25C40D77809C22A3977F8C4B4CF3056D";

    public static String SEARCH_WORD_API_URL_PRE = SEARCH_WORD_API_URL + "?w=";
    public static String SEARCH_WORD_API_URL_AFTER = "&key=" + SEARCH_WORD_API_KEY;
    public static String SEARCH_WORD_API_URL_AFTER_BY_JSON = "&type=json&key=" + SEARCH_WORD_API_KEY;

    //    public static String LOCAL_IP = "192.168.191.1";
    public static String LOCAL_IP = "10.18.80.163";
    public static String HOST_URL = "http://" + LOCAL_IP + ":8080/";
    public static String REGISTER_URL = HOST_URL + "register";
    public static String LOGIN_URL = HOST_URL + "login";
    public static String DEFAULT_STRING_CHARSET = "ISO-8859-1";
    public static String UTF8_CHARSET = "UTF-8";


    public static boolean DEBUG = true;//开发阶段

    //手机短信验证
    public static String SMS_APP_KEY = "1b9d2607f747c";
    public static String SMS_APP_SECRET = "b0a6cbebed923f3cd6fcdff4a856a6ad";

    //    SharedPreferences持久层
    public static String SP_LOGIN_DATA = "LOGIN_DATA";
    public static String SP_IS_DATABASE_INITED = "IS_DATABASE_INITED";
    public static String SP_IS_LOGIN = "IS_LOGIN";

    // 数据库同步 初始化
    public static String USER = "user.json";
    public static String WORD_DEVELOPMENT = "word_development.json";
    public static String WORD_LIB = "word_lib.json";
    public static String LIB1 = "lib1.json";
    public static String LIB2 = "lib2.json";
    public static String LEARN_LIB1 = "learn_lib1.json";
    public static String LEARN_LIB2 = "learn_lib2.json";


    public static String USER_ID;//TODO  注册时赋值 用户ID
}

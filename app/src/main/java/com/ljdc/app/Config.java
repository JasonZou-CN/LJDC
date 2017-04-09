package com.ljdc.app;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/1/10 0010
 * Time:上午 12:00
 * Desc:略
 */
public class Config {

    public static final String SP_DEFAULT_PRON = "default_pron";
    public static final String SP_INITNEWWORD_LASTDATE = "init_new_word_lastdate";//最近生成生词的日期
    public static final String WORD_LIB = "word_lib.json";
    public static final String WORD_LIB1 = "lib1.json";
    public static final String WORD_LIB2 = "lib2.json";
    public static final String PARAM_SYNCJSONDATA = "syncJsonData";
    public static final String PARAM_MAXANCHOR = "maxAnchor";//客户端最最近更新时间
    public static final String PARAM_USERID = "userId";
    /*注：完整URL为（http://dict-co.iciba.com/api/dictionaryphp?w=love&key=25C40D77809C22A3977F8C4B4CF3056D）
                                                参数1:w;（单词）
                                                参数2:key;（开发者秘钥）
                                                */
    public static String SEARCH_WORD_API_URL = "http://dict-co.iciba.com/api/dictionary.php";
    public static String SEARCH_WORD_API_KEY = "25C40D77809C22A3977F8C4B4CF3056D";
    public static String SEARCH_WORD_API_URL_PRE = SEARCH_WORD_API_URL + "?w=";
    public static String SEARCH_WORD_API_URL_AFTER = "&key=" + SEARCH_WORD_API_KEY;
    public static String SEARCH_WORD_API_URL_AFTER_BY_JSON = "&type=json&key=" + SEARCH_WORD_API_KEY;
    //        public static String LOCAL_IP = "192.168.191.1";
    public static String LOCAL_IP = "10.18.80.163";
    public static String HOST_URL = "http://" + LOCAL_IP + ":8080";
    public static final String ADD_WORD_URL = HOST_URL + "/sync/addOneWordToLib";
    public static final String SYNC_LEARN_LIB1_URL = HOST_URL + "/sync/syncLearnLib1";
    public static final String SYNC_LEARN_LIB2_URL = HOST_URL + "/sync/syncLearnLib2";
    public static final String SYNC_WORD_DEVELOP_URL = HOST_URL + "/sync/syncWordDevelop";
    public static final String SYNC_STUDY_PLAN_URL = HOST_URL + "/sync/syncStudyPlan";
    public static final String SYNC_LIBS_URL = HOST_URL + "/sync/syncLibs";
    public static final String SYNC_USER_URL = HOST_URL + "/sync/syncUsers";
    public static final String SYNC_WORD_EVALUATION_URL = HOST_URL + "/sync/syncWordEvaluation";
    public static final String SYNC_LIB_URL = HOST_URL + "/sync/syncLib";
    public static final String SYNC_REMOVE_LIB_URL = HOST_URL + "/sync/syncRemoveLib";
    public static String REGISTER_URL = HOST_URL + "/verify/register";
    public static String LOGIN_URL = HOST_URL + "/verify/login";
    public static String DEFAULT_STRING_CHARSET = "ISO-8859-1";
    public static String UTF8_CHARSET = "UTF-8";
    public static boolean DEBUG = true;//开发阶段
    //手机短信验证
    public static String SMS_APP_KEY = "1b9d2607f747c";
    public static String SMS_APP_SECRET = "b0a6cbebed923f3cd6fcdff4a856a6ad";
    //    SharedPreferences持久层
    public static String SP_LJDC = "SP_LJDC";
    public static String SP_IS_DATABASE_INITED = "IS_DATABASE_INITED";
    public static String SP_IS_LOGIN = "IS_LOGIN";
    // 数据库同步 初始化
    public static String USER = "user.json";
    public static String WORD_DEVELOPMENT = "word_development.json";
    public static String LEARN_LIB1 = "learn_lib1.json";
    public static String LEARN_LIB2 = "learn_lib2.json";

}

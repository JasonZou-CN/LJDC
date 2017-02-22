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
    public static String SEARCH_WORD_API_URL_AFTER = "&type=json&key=" + SEARCH_WORD_API_KEY;

    public static String LOCAL_IP = "192.168.191.1";
    public static String HOST = "http://"+LOCAL_IP+":8080/";
    public static String LOGIN = "";
    public static String REGISTER = "register";



    public static String USER_ID;//TODO  注册时赋值 用户ID
}

package com.ljdc.utils;

import android.util.Log;

import com.ljdc.app.Config;
import com.ljdc.model.Parameter;
import com.ljdc.model.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author 邹旭
 */
public class JsonUtils {


    public static SyncHttp http = new SyncHttp();
    private final static String TAG = "JsonUtils.java";

    /**
     * 用户注册
     *
     * @author ZOUXU
     * @return
     * @throws Exception
     *             请求参数设置可能不对
     */
    public static Object[] regist(List<Parameter> params) throws Exception {
        Object[] objs = new Object[3];
        String result = http.httpPost(Config.HOST, params);
        SysPrintUtil.pt(TAG, result);
        JSONObject obj = new JSONObject(result);
        int resultcode = obj.getInt("code");
        switch (resultcode) {
            case 100:
                objs[0] = true;
                break;
            case 101:
                objs[0] = false;
                objs[1] = "用户名已存在";
                break;
            case 102:
                objs[0] = false;
                objs[1] = "插入数据库失败";
                break;
        }
        return objs;
    }

    public static Object[] login(List<Parameter> params) throws Exception {
        // TODO Auto-generated method stub
        Object[] objs = new Object[3];
        String result = http.httpPost(Config.HOST, params);
        SysPrintUtil.pt(TAG, result);
        JSONObject obj = new JSONObject(result);
        int resultcode = obj.getInt("code");
        switch (resultcode) {
            case 200:
                objs[0] = true;
                break;
            case 201:
                objs[0] = false;
                objs[1] = "用户名不存在";
                break;
            case 202:
                objs[0] = false;
                objs[1] = "密码错误";
                break;
        }
        return objs;
    }

    /*命名参照API返回结果*/
    public static Word getWordInfo(String json) {
        Word word = new Word();
        try {
            JSONObject jsonObject = new JSONObject(json);
            word.setName(jsonObject.getString("word_name"));
            JSONArray symbols = jsonObject.getJSONArray("symbols");
            JSONArray parts  =  symbols.getJSONObject(0).getJSONArray("parts"); /*单词释义*/
            String means  = parts.getJSONObject(0).getString("means");
//            parts.length();
            word.setInfo(means);
        } catch (JSONException e) {

            e.printStackTrace();
            word = null;
            Log.i("调试","WORD设置为NULL");
        }
        return word;
    }
}

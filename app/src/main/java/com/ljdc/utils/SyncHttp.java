package com.ljdc.utils;


import com.ljdc.model.Parameter;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import static java.net.Proxy.Type.HTTP;


/**
 * 发送Http请求
 *
 * @author jason zou
 */
@SuppressWarnings("deprecation")
public class SyncHttp {


    /**
     * 通过POST方式发送请求
     *
     * @param url
     *            URL地址
     *
     * @param params
     *            参数 params.put("email","");
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, List<Parameter> params)
            throws Exception {

        String response = null;
        int timeoutConnection = 3000;
        int timeoutSocket = 5000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        // 构造HttpClient的实例
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpPost httpPost = new HttpPost(url);
        System.out.println("URL地址为:  " + url);
        if (params.size() >= 0) {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(
                    buildNameValuePair(params), "UTF-8"));
        }
        // 使用execute方法发送HTTP Post请求，并返回HttpResponse对象
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        System.out.println("POST状态码为" + statusCode);

        if (statusCode == HttpStatus.SC_OK) {
            // 获得返回结果
            response = EntityUtils.toString(httpResponse.getEntity());
            response = new String(response.getBytes(), "utf-8");
        } else {
            response = "返回码：" + statusCode;
        }
        return response;
    }

    /**
     * 把Parameter类型集合转换成NameValuePair类型集合
     *
     * @param params
     *            参数集合
     * @return
     */

    private static List<BasicNameValuePair> buildNameValuePair(
            List<Parameter> params) {

        List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
        for (Parameter param : params) {
            BasicNameValuePair pair = new BasicNameValuePair(param.getName(),
                    param.getValue());

            System.out.println("POST--pair" + pair);

            result.add(pair);
        }
        return result;
    }

    /**
     * 通过GET方式发送请求
     *
     * @param url    URL地址   host+resiger
     * @param params 参数      email=""&
     * @return
     * @throws Exception
     */
    public static String httpGet(String url, String params) throws Exception {

        String response = null; // 返回信息
        // 拼接请求URL
        if (null != params && !params.equals("")) {
            url += "" + params;    //?
        }

        System.out.println("URL地址为:  " + url);


        int timeoutConnection = 10000;
        int timeoutSocket = 10000;

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        // 构造HttpClient的实例
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        // 创建GET方法的实例
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            System.out.println("返回的状态码为:" + statusCode);


        /*    if (statusCode == HttpStatus.SC_OK) // SC_OK = 200
            {*/
            // 获得返回结果
            response = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("返回结果:" + response);
            /*} else {
                response = "返回码：" + statusCode;
            }*/
        } catch (Exception e) {

            System.out.println("返回的状态码为:！！" + e.toString());


            throw new Exception(e);
        }
        return response;
    }
}

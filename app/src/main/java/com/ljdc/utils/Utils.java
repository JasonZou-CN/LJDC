package com.ljdc.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.*;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ljdc.pojo.WordLibServer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Utils {

    /**
     * 用于完成录音
     */
    public static MediaRecorder mRecorder = null;

    /**
     * 保存数据到sp
     *
     * @param @param context 上下文对象
     * @param @param key 键
     * @param @param value 值
     * @return void
     * @Title: savePreference
     * @Description: TODO
     */
    public static void savePreference(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(key, value).commit();
    }

    /**
     * 从sp获取数据
     *
     * @param @param  context 上下文对象
     * @param @param  key 键
     * @param @return
     * @return String 返回的值
     * @Title: getpreference
     * @Description: TODO
     */
    public static String getpreference(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, "");
    }

    /**
     * 从sp删除数据
     *
     * @param @param  context 上下文对象
     * @param @param  key 键
     * @param @return
     * @return String 返回的值
     * @Title: getpreference
     * @Description: TODO
     */
    public static boolean deltepreference(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit()
                .remove(key).commit();
    }

    /**
     * 判断email格式是否正确
     *
     * @param @param  email
     * @param @return
     * @return boolean
     * @Title: isEmail
     * @Description: TODO
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证手机号
     *
     * @param @param  mobiles
     * @param @return
     * @return boolean
     * @Title: isMobileNO
     * @Description: TODO
     * @author 邹旭
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * @param @param  createdate
     * @param @return
     * @return String
     * @Title: getDate
     * @Description: TODO 裁剪时间字符串
     */
    public static String getDate(String createdate) {
        if (!"".equals(createdate) && createdate.length() > 11) {
            if (createdate.indexOf("T") != -1) {
                return createdate.substring(0, createdate.indexOf("T"));
            }
        }
        return "";
    }

    /**
     * @param @param  createdate
     * @param @return
     * @return String
     * @Title: getDate
     * @Description: TODO 裁剪时间字符串
     */
    public static String getallDate(String createdate) {
        if (!"".equals(createdate) && createdate.length() > 11) {
            if (createdate.indexOf("T") != -1) {
                return createdate.substring(0, createdate.indexOf("T"))
                        + " "
                        + createdate.substring(createdate.indexOf("T") + 1,
                        createdate.lastIndexOf(":"));
            }
        }
        return "";
    }

    public static int getWeek(String createdate) {
        if (!"".equals(createdate) && createdate.length() > 11) {
            if (createdate.indexOf("T") != -1) {
                String sdate = createdate.substring(0, createdate.indexOf("T"));
                Date date = Date.valueOf(sdate);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                return c.get(Calendar.DAY_OF_WEEK);
            }
        }
        return -1;
    }

    public static String getTime(String createdate) throws ParseException {
        if (!"".equals(createdate) && createdate.length() > 11) {
            if (createdate.indexOf("T") != -1) {
                String sdate = createdate.replace("T", " ");
                SimpleDateFormat dfd = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                // String d = dfd.format(sdate);
                return df.format(dfd.parse(sdate));
            }
        }
        return "";
    }

    public static boolean isMorning(String stime) {
        if (null != stime && "".equals(stime)) {
            Time time = new Time(stime);
            if (time.hour > 12) {
                return false;
            }
        }
        return true;
    }

    public static Bitmap compressImage(String picpath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picpath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 600f;// 这里设置高度为800f
        float ww = 360f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(picpath, newOpts);
        return compressImage(bitmap);
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 30) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 开始录音
     */
    public static String startVoice(Context context) {
        String path = "/wzschool/xmppaudio/" + System.currentTimeMillis()
                + ".amr";
        // 设置录音保存路径
        try {
            String mFileName = Environment.getExternalStorageDirectory() + path;
            String state = Environment.getExternalStorageState();
            if (!state.equals(Environment.MEDIA_MOUNTED)) {
                return "";
            }
            File directory = new File(mFileName).getParentFile();
            if (!directory.exists() && !directory.mkdirs()) {
                return "";
            }
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
            }
            mRecorder.start();
        } catch (Exception e) {
            return "";
        }
        return path;
    }

    /**
     * 停止录音
     */
    public static long stopVoice(long time) {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        return System.currentTimeMillis() - time;
    }

    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     * @return *
     * @throws Exception
     */

    public static String encodeBase64File(String path) throws Exception {
        try {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (OutOfMemoryError error) {
            System.out.println("==============errorerror=============="
                    + error.toString());
        }
        return "";
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        File file = new File(targetPath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 判读一个文件是否是图片
     *
     * @param @return
     * @return boolean
     * @Title: isIMG
     * @Description: TODO
     */
    public static boolean isIMG(String path) {
        if (!"".equals(path) && path.length() > 0
                && path.lastIndexOf(".") != -1) {
            String type = path.substring(path.lastIndexOf(".") + 1,
                    path.length()).toLowerCase();
            if (!"".equals(type)
                    && (type.equals("jpg") || type.equals("gif")
                    || type.equals("png") || type.equals("jpeg")
                    || type.equals("bmp") || type.equals("wbmp")
                    || type.equals("ico") || type.equals("jpe"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判读一个文件是否是音频文件
     *
     * @param @param  path
     * @param @return
     * @return boolean
     * @Title: isAUDIO
     * @Description: TODO
     */
    public static boolean isAUDIO(String path) {
        if (!"".equals(path) && path.length() > 0
                && path.lastIndexOf(".") != -1) {
            String type = path.substring(path.lastIndexOf(".") + 1,
                    path.length()).toLowerCase();
            if (!"".equals(type) && (type.equals("mp3") || type.equals("aac"))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVIDEO(String path) {
        if (!"".equals(path) && path.length() > 0
                && path.lastIndexOf(".") != -1) {
            String type = path.substring(path.lastIndexOf(".") + 1,
                    path.length()).toLowerCase();
            if (!"".equals(type)
                    && (type.equals("mp4") || type.equals("3gp")
                    || type.equals("avi") || type.equals("rmvb") || type
                    .equals("wmv"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置WebView
     *
     * @param @param context
     * @param @param webView
     * @return void
     * @Title: setWebView
     * @Description: TODO
     */
    public static void LoadWeb(Context context, WebView webView, String url) {
        setWebView(context, webView);
        webView.loadUrl(url);
    }

    /**
     * 设置WebView
     *
     * @param @param context
     * @param @param webView
     * @return void
     * @Title: setWebView
     * @Description: TODO
     */
    public static void setWebView(final Context context, final WebView webView) {
        webView.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBlockNetworkImage(true);
        webSettings.setRenderPriority(RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                view.getSettings().setBlockNetworkImage(false);

                super.onPageFinished(view, url);

            }
        });
    }

    /**
     * 加载WebView
     *
     * @param @param webView 加载内容的WebView
     * @param @param textView 提示消息的textView
     * @param @param url 需要加载的url
     * @return void
     * @Title: LoadWeb
     * @Description: TODO
     */
    public static void LoadcontentWeb(Context context, WebView webView,
                                      String content) {
        // setWebView(context, webView);
        try {
            webView.loadDataWithBaseURL(
                    "fake://not/needed",
                    "<html><head><meta http-equiv='content-type' content='text/html;charset=utf-8'><style type=\"text/css\">img{ width:95%}</style><STYLE TYPE=\"text/css\"> BODY { margin:0; padding: 5px 3px 5px 5px; background-color:#ffffff;} </STYLE><BODY TOPMARGIN=5 rightMargin=0 MARGINWIDTH=0 MARGINHEIGHT=0></head><body>"
                            + new String(content.getBytes("utf-8"))
                            + "</body></html>", "text/html", "utf-8", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取wifi下本机的ip地址
     *
     * @param @param  context
     * @param @return
     * @return String
     * @Title: getWifiIp
     * @Description: TODO
     */
    public static String getWifiIp(Context context) {
        String ip = "";
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            ip = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "."
                    + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
        }
        return ip;
    }

    /**
     * 获取当前版本号
     *
     * @param @param  context
     * @param @return
     * @param @throws Exception
     * @return String
     * @Title: getVersionName
     * @Description: TODO
     */
    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    public static int bytesToInt(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return 0;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return Integer.parseInt(stringBuilder.toString(), 16);
    }

    /**
     * 整数转成byte数组
     *
     * @param @param  value
     * @param @param  len
     * @param @return
     * @return byte[]
     * @Title: intToBytes
     * @Description: TODO
     */
    public static int[] intToBytes(int value, int len) {
        int[] b = new int[len];
        String sl = Integer.toHexString(value);
        int sur = len * 2 - sl.length();
        for (int i = 0; i < sur; i++) {
            sl = "0" + sl;
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = (Integer.parseInt(sl.substring(i * 2, (i + 1) * 2), 16));
        }
        return b;
    }

    /**
     * 判断数字
     *
     * @param @param  str
     * @param @return
     * @return boolean
     * @Title: isNumeric
     * @Description: TODO
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String compareNowdate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
        try {
            Date nowdate = new Date(System.currentTimeMillis());
            if (getTwodateDay(date, sdf.format(nowdate)) == 0) {
                return getNoDayTime(date);
            } else if (getTwodateDay(date, sdf.format(nowdate)) == 1) {
                return "昨天\t" + getNoDayTime(date);
            } else {
                return sdf1.format(sdf.parse(date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNoDayTime(String date) {
        SimpleDateFormat formatBuilder = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatBuilder.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param @param  date1
     * @param @param  date2
     * @param @return
     * @return long
     * @Title: getTwodateDay
     * @Description: TODO
     */
    public static long getTwodateDay(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return (sdf.parse(date2).getTime() - sdf.parse(date1).getTime())
                    / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 聊天的时候比较两个时间
     *
     * @param @return
     * @return String
     * @Title: compareTwoDate
     * @Description: TODO
     */
    public static boolean compareTwoDate(String predate, String nextdate) {
        if ((getLongTime(nextdate) - getLongTime(predate)) < (5 * 60 * 1000)) {
            return false;
        }
        return true;
    }

    /**
     * 把标准时间转换成时间戳
     *
     * @param @param  user_time
     * @param @return
     * @return String
     * @Title: getTime
     * @Description: TODO
     */
    public static long getLongTime(String user_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            return l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * bitmap转base64位
     *
     * @param bitmap
     * @return
     */
    public static String imgToBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        String status = Environment.getExternalStorageState();
        SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new java.util.Date(time));
        // ContentValues是我们希望这条记录被创建时包含的数据信息
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        Log.i("", "生成的照片输出路径：" + imageFilePath.toString());
        return imageFilePath;
    }

    /**
     * uri转绝对路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String uriToPath(Context context, Uri uri) {
        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(uri, proj, // Which
                // columns
                // to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return uri.getPath();// 如果游标为空说明获取的已经是绝对路径了
        }
    }

    /**
     * 截取两个标签之间字符串
     *
     * @param @param  string
     * @param @return
     * @return String
     * @Title: clipTagToTagText
     * @Description: TODO
     */
    public static String clipTagToTagText(String string) {
        String str = "";
        Pattern p = Pattern.compile("<a[^>]*>([^<]*)</a>");
        Matcher m = p.matcher(string);
        while (m.find()) {
            str = m.group(1);
        }
        return str;
    }

    /**
     * 根据手机号查找联系人
     *
     * @param @param  context
     * @param @param  phone
     * @param @return
     * @return List<String>
     * @Title: searchPhoneToName
     * @Description: TODO
     */
    public static List<String> searchPhoneToName(Context context, String phone) {
        List<String> names = new ArrayList<String>();
        Uri uri = Uri
                .parse("content://com.android.contacts/data/phones/filter/"
                        + phone);
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Phone.DISPLAY_NAME,
                Phone.NUMBER}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor
                    .getColumnIndex(Phone.DISPLAY_NAME));
            names.add(name);
        }
        cursor.close();
        return names;
    }

    /**
     * MD5加密
     *
     * @param s
     * @return
     */
    public final static String md5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @return 读取Assets文本文件信息 ; 返回字符串
     * @author jasonzou
     */
    public static String getStringFromAssets(String assets, Context context) {
        StringBuffer sb = new StringBuffer();
        //获取assets路径
        AssetManager am = context.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(am.open(assets), "UTF-8"));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }

    /**
     * 解析WORD的XML信息
     *
     * @param xmlString
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     * @author 邹旭
     */
    public static WordLibServer getWordFromXML(String xmlString) throws IOException, XmlPullParserException {
        InputStream is = null;
        String result = "";
        WordLibServer word = null;
        is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:// 文档开始事件
                    word = new WordLibServer();
                    break;
                case XmlPullParser.START_TAG:// 开始标签事件
                    String nodeName = parser.getName().trim();
                    if ("key".equals(nodeName)) { //标签名参照实时SDK
                        result = parser.nextText().trim();
                        word.word = result;
                    } else if ("ps".equals(nodeName)) {
                        result = parser.nextText().trim();
                        if (word.pronStrEn == null)
                            word.pronStrEn = result;
                        else
                            word.pronStrUs = result;
                    } else if ("pron".equals(nodeName)) {
                        result = parser.nextText().trim();
                        if (word.pronUrlEn == null)
                            word.pronUrlEn = result;
                        else
                            word.pronUrlUs = result;
                    } else if ("pos".equals(nodeName)) {
                        result = parser.nextText().trim();
                        if (word.pos1 == null)
                            word.pos1 = result;
                        else if (word.pos2 == null)
                            word.pos2 = result;
                        else if (word.pos3 == null)
                            word.pos3 = result;
                        else
                            word.pos4 = result;
                    } else if ("acceptation".equals(nodeName)) {
                        result = parser.nextText().trim();
                        if (word.acceptation1 == null)
                            word.acceptation1 = result;
                        else if (word.acceptation2 == null)
                            word.acceptation2 = result;
                        else if (word.acceptation3 == null)
                            word.acceptation3 = result;
                        else
                            word.acceptation4 = result;
                    } else if ("orig".equals(nodeName)) {
                        result = parser.nextText().trim();
                        if (word.sentEn1 == null)
                            word.sentEn1 = result;
                        else if (word.sentEn2 == null)
                            word.sentEn2 = result;
                        else if (word.sentEn3 == null)
                            word.sentEn3 = result;
                        else
                            word.sentEn4 = result;
                    } else if ("trans".equals(nodeName)) {
                        result = parser.nextText().trim();
                        if (word.sentTrans1 == null)
                            word.sentTrans1 = result;
                        else if (word.sentTrans2 == null)
                            word.sentTrans2 = result;
                        else if (word.sentTrans3 == null)
                            word.sentTrans3 = result;
                        else
                            word.sentTrans4 = result;
                    }
                    break;
                case XmlPullParser.END_TAG:// 结束标签事件
                    break;
            }
            eventType = parser.next();
        }
        return word;
    }

}

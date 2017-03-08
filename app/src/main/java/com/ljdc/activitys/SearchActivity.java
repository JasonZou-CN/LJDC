package com.ljdc.activitys;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.ljdc.R;
import com.ljdc.adapters.SearchAdapter;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.model.Bean;
import com.ljdc.model.Message;
import com.ljdc.pojo.WordLibServer;
import com.ljdc.utils.*;
import com.ljdc.views.SearchView;
import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchActivity extends Activity implements SearchView.SearchViewListener, Response.Listener<String> {

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;
    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;
    /**
     * 搜索结果列表view
     */
    private ListView lvResults;
    /**
     * 搜索view
     */
    private SearchView searchView;
    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;
    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;
    private List<Bean> dbData;
    /**
     * 热搜版数据
     */
    private List<String> hintData;
    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;
    /**
     * 搜索结果的数据
     */
    private List<Bean> resultData;


    /**
     * 单词查询所得的结果
     */
    private WordLibServer word = null;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        SearchActivity.hintSize = hintSize;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
//        initData();
        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
//        热搜
//        searchView.setTipsHintAdapter(hintAdapter);
//        自动补全
//        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(SearchActivity.this, position + "", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("searchWord", word);
                Act.toAct(SearchActivity.this, StudyWordActivity.class, bundle);
                SearchActivity.this.finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            dbData.add(new Bean(R.drawable.ic_launcher, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
        }
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("热搜版" + i + "：Android自定义View");
        }
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
/*        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
        *//*    for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }*//*
        }*/
         /*填充查询结果页*/
        new AsyReq().execute(text);



/*        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(SearchActivity.this, resultData, R.layout.item_bean_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }*/
    }

/*    *//**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     *
     * @param text
     *//*
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }*/

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
//        getResultData(text);
                 /*填充查询结果页*/
        new AsyReq().execute(text);
        lvResults.setVisibility(View.VISIBLE);
//        //第一次获取结果 还未配置适配器
//        if (lvResults.getAdapter() == null) {
//            //获取搜索数据 设置适配器
//            lvResults.setAdapter(resultAdapter);
//        } else {
//            //更新搜索数据
//            resultAdapter.notifyDataSetChanged();
//        }
        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();


    }

    /**Volley 请求成功回调
     * @param response
     */
    @Override
    public void onResponse(String response) {
        try {
            response = new String(response.getBytes(Config.DEFAULT_STRING_CHARSET), Config.UTF8_CHARSET);
            //TODO 解析返回值
            Message message = new Gson().fromJson(response, Message.class);
            if (message.getCode() == 200) {

            } else {
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private class AsyReq extends AsyncTask<String, Integer, String> {
        private String result;

        @Override
        protected void onPreExecute() {
            if (resultData == null) {
                // 初始化
                resultData = new ArrayList<>();
                resultAdapter = new SearchAdapter(SearchActivity.this, resultData, R.layout.item_bean_list);
                lvResults.setAdapter(resultAdapter);
            } else {
                resultData.clear();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                result
                        = SyncHttp.httpGet(Config.SEARCH_WORD_API_URL_PRE, params[0] + Config.SEARCH_WORD_API_URL_AFTER);

//                MyLog.info("-----------------"+word_se+"-----------"+getWord());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = new String(result.getBytes(Config.DEFAULT_STRING_CHARSET), Config.UTF8_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
//            Word word = JsonUtils.getWordInfo(result);

            try {
                Dao dao = DBHelper.getHelper(SearchActivity.this).getDao(WordLibServer.class);
                word = Utils.getWordFromXML(result);
                dao.create(word);
                List<WordLibServer> words = dao.queryBuilder().where().eq("word", word.word).query();
                WordLibServer w = words.get(0);
                Log.d("AsyReq : ", w.toString());
                Map<String, String> parms = new HashMap<String, String>();//设置POST请求参数
                parms.put(Config.PARAM_SYNCJSONDATA, new Gson().toJson(word));
                new VolleyPostRequest(SearchActivity.this).postRequest(parms, Config.ADD_WORD_URL, SearchActivity.this);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Bean bean = new Bean(R.drawable.ic_launcher, "", "", "");
            bean.setTitle(word.word);
            StringBuffer str = new StringBuffer();
            str.append(word.pos1 + " ");
            str.append(word.acceptation1);
            if (null != word.acceptation2) {
                str.append("\n");
                str.append(word.pos2 + " ");
                str.append(word.acceptation2);
            }
            if (null != word.acceptation3) {
                str.append("\n");
                str.append(word.pos3 + " ");
                str.append(word.acceptation3);
            }
            if (null != word.acceptation4) {
                str.append("\n");
                str.append(word.pos4 + " ");
                str.append(word.acceptation4);
            }
            bean.setContent(str.toString());
            resultData.add(bean);
            resultAdapter.notifyDataSetChanged();
        }
    }


}

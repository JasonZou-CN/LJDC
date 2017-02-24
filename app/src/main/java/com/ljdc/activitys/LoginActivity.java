package com.ljdc.activitys;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.model.Message;
import com.ljdc.pojo.Lib1EnglishGrand4CoreServer;
import com.ljdc.pojo.UserServer;
import com.ljdc.pojo.WordLibServer;
import com.ljdc.utils.Act;
import com.ljdc.utils.ToastUtils;
import com.ljdc.utils.VolleyPostRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class LoginActivity extends Activity implements View.OnClickListener, Response.Listener<String> {
    private TextView tv_title, tv_forgetPwd;
    private EditText et_account, et_password;
    private Button btn_login;
    private LinearLayout ll_createAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.title_center_tv);
        tv_title.setText(R.string.login);

        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);

        tv_forgetPwd = (TextView) findViewById(R.id.tv_forgetPwd);

        btn_login = (Button) findViewById(R.id.btn_login);

        ll_createAccount = (LinearLayout) findViewById(R.id.ll_createAccount);

        ll_createAccount.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_forgetPwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //TODO 设置处理逻辑
        switch (view.getId()) {
            case R.id.ll_createAccount:
                Act.toAct(this, RegisterByPhone.class);
                break;
            case R.id.tv_forgetPwd:
                ToastUtils.showShort(this, "xxx");

                break;
            case R.id.btn_login:
                try {
//                    DBHelper.getHelper(this).getDatabase().execSQL("INSERT INTO word_lib_server (word) VALUES (1000,\"446\");");
//                    Dao<UserServer,Integer> dao = DBHelper.getHelper(this).getDao(UserServer.class);
//                    UserServer userServer = (UserServer) dao.queryForId(1000);
//                    Log.d("LoginActivity", userServer.nickname);
//                    Toast.makeText(this, "userServer.wordDevelopmentServers :"+userServer.wordDevelopmentServers.size(), Toast.LENGTH_SHORT).show();

                    WordLibServer word = new WordLibServer();
                    word.word = "word:";
                    Lib1EnglishGrand4CoreServer lib1 = new Lib1EnglishGrand4CoreServer();
                    lib1.wordLibServer = word;
                    DBHelper.getHelper(this).getDao(WordLibServer.class).create(word);
                    DBHelper.getHelper(this).getDao(Lib1EnglishGrand4CoreServer.class).create(lib1);
                  /*  DBHelper.getHelper(this).getDao(Lib1EnglishGrand4CoreServer.class).create(lib1);*/

//                    DBHelper.getHelper(this).getDao(UserServer.class);
                    SQLiteDatabase db = DBHelper.getHelper(this).getReadableDatabase();

                    db.execSQL("INSERT INTO user (nickname) VALUES (\"jasonzou\");");
                    db.execSQL("INSERT INTO user (nickname) VALUES (\"安抚\");");
                    db.execSQL("INSERT INTO user (nickname) VALUES (\"阿斯蒂芬\");");
                    Log.d("LoginActivity", "DBHelper.getHelper(this).getDao(UserServer.class).queryForAll().size():" + DBHelper.getHelper(this).getDao(UserServer.class).queryForAll().size());



                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    ToastUtils.showShort(this, "信息不能为空");
                } else {
                    //TODO 发起网络请求
                    Map<String, String> parms = new HashMap<String, String>();
                    parms.put("phone", et_account.getText().toString());
                    parms.put("email", et_account.getText().toString());
                    parms.put("nickname", et_account.getText().toString());
                    parms.put("password", et_password.getText().toString());
                    new VolleyPostRequest(this).postRequest(parms, Config.LOGIN_URL, this);
                }
                */
                break;
        }
    }

    /**
     * 处理网络请求的回调
     * Called when a response is received.
     *
     * @param response
     */
    @Override
    public void onResponse(String response) {
        try {
            response = new String(response.getBytes("iso-8859-1"), "utf-8");
            //TODO 解析返回值
            Message message = new Gson().fromJson(response, Message.class);
            if (message.getCode() == 200) {
                Toast.makeText(this, "登录成功，正在跳转", Toast.LENGTH_SHORT).show();
                Act.toAct(this, MainActivity.class);
                finish();
            } else {
                Toast.makeText(this, "登录失败，请检查登录信息", Toast.LENGTH_SHORT).show();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

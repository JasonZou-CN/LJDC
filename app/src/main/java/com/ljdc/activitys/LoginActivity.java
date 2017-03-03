package com.ljdc.activitys;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.database.InitDatabase;
import com.ljdc.model.Message;
import com.ljdc.pojo.UserServer;
import com.ljdc.utils.Act;
import com.ljdc.utils.ToastUtils;
import com.ljdc.utils.VolleyPostRequest;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(Config.SP_LOGIN_DATA, Activity.MODE_PRIVATE);
        if (sp.getBoolean(Config.SP_IS_LOGIN, false)) {
            System.out.println("忽略。。。");
            Act.toAct(this, MainActivity.class);
            this.finish();
        }
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
                this.finish();
                break;
            case R.id.tv_forgetPwd:
                ToastUtils.showShort(this, "xxx");

                break;
            case R.id.btn_login:
//                if (true) {
//                    Act.toAct(this, MainActivity.class);
//                    break;
//                }

                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    ToastUtils.showShort(this, "信息不能为空");
                } else {
                    //TODO 发起网络请求
                    Map<String, String> parms = new HashMap<String, String>();//设置POST请求参数
                    parms.put("phone", et_account.getText().toString());
                    parms.put("email", et_account.getText().toString());
                    parms.put("password", et_password.getText().toString());
                    new VolleyPostRequest(this).postRequest(parms, Config.LOGIN_URL, this);
                }

                break;
        }
    }

    /**
     * 处理网络请求的回调 登录成功则 进入应用
     * Called when a response is received.
     *
     * @param response
     */
    @Override
    public void onResponse(String response) {
        try {
            response = new String(response.getBytes(Config.DEFAULT_STRING_CHARSET), Config.UTF8_CHARSET);
            //TODO 解析返回值
            Message message = new Gson().fromJson(response, Message.class);
            if (message.getCode() == 200) {
                final SharedPreferences.Editor edit = sp.edit();
                UserServer user = new Gson().fromJson(message.getMsg(), UserServer.class);
                DBHelper.getHelper(this).getDao(UserServer.class).create(user);//保存登录的用户信息
                //TODO 登录成功后，数据库初始化
                if (!sp.getBoolean(Config.SP_IS_DATABASE_INITED,false)) {//数据库初始化
                    Toast.makeText(this, "登录成功，正在初始化数据库", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            edit.putBoolean(Config.SP_IS_DATABASE_INITED, true);
                            edit.commit();

                            InitDatabase.initWordLib(LoginActivity.this);
                            InitDatabase.initLib1(LoginActivity.this);
                            InitDatabase.initLib2(LoginActivity.this);
                            InitDatabase.initWordDevelopment(LoginActivity.this);
                            InitDatabase.initUser(LoginActivity.this);
                        }
                    }).start();
                }
                edit.putBoolean(Config.SP_IS_LOGIN, true);
                edit.commit();
                Act.toAct(this, MainActivity.class);
                finish();
            } else {
                Toast.makeText(this, "登录失败，请检查登录信息", Toast.LENGTH_SHORT).show();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

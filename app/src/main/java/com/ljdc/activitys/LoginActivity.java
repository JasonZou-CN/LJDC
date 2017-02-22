package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.utils.Act;
import com.ljdc.utils.ToastUtils;

/**
 *
 */
public class LoginActivity extends Activity implements View.OnClickListener {
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
//                ToastUtils.showShort(this,"xxx");
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    ToastUtils.showShort(this, "请检查登录信息");
                } else {
                    //TODO 发起网络请求
                    RequestQueue mQueue = Volley.newRequestQueue(this);
                    StringRequest request = new StringRequest(Config.HOST+Config.REGISTER+"?email=frank_zouxu@163.com&password=123&nickname=jasonzou", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("LoginActivity", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LoginActivity",error.getMessage(),error);
                        }
                    });
                    mQueue.add(request);
                }
                Act.toAct(this,MainActivity.class);
                break;
        }
    }
}

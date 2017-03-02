package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.model.Message;
import com.ljdc.utils.Act;
import com.ljdc.utils.ToastUtils;
import com.ljdc.utils.VolleyPostRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class RegisterConfirmPassword extends Activity implements View.OnClickListener, Response.Listener<String> {


    private String phone;
    private View back;
    private TextView title;
    private EditText et_password;
    private EditText et_password_confirm;
    private Button btn_registe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm_password);
        initView();

        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");

    }

    private void initView() {


        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        title = (TextView) findViewById(R.id.title_center_tv);
        title.setText("手机注册");

        et_password = (EditText) findViewById(R.id.et_password);
        et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
        btn_registe = (Button) findViewById(R.id.btn_registe);

        btn_registe.setOnClickListener(this);
//        et_password_confirm.setOnClickListener(this);
//        et_password.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //TODO 设置处理逻辑
        String password = et_password.getText().toString().trim();
        String password_confirm = et_password_confirm.getText().toString().trim();
        switch (view.getId()) {
            case R.id.ll_createByEmail:
                Act.toAct(this, RegisterByEmail.class);
                this.finish();
                break;
            case R.id.btn_registe:
                if (TextUtils.isEmpty(password_confirm) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "信息不完整", Toast.LENGTH_SHORT).show();
                    break;
                }else if (!password.equals(password_confirm)) {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    break;
                }
                Map<String, String> parms = new HashMap<String, String>();
                parms.put("password", password);
                parms.put("phone", phone);

                //TODO 发起网络请求
                new VolleyPostRequest(this).postRequest(parms, Config.REGISTER_URL,this);

                this.finish();
                break;
        }
    }

    @Override
    public void onResponse(String response) {
        try {
            response = new String(response.getBytes(Config.DEFAULT_STRING_CHARSET), Config.UTF8_CHARSET);
            //TODO 解析返回值
            Message message = new Gson().fromJson(response, Message.class);
            if (message.getCode() == 200) {
                Toast.makeText(this, message.getMsg(), Toast.LENGTH_SHORT).show();
                Act.toAct(this, LoginActivity.class);
                this.finish();
            } else {
                Toast.makeText(this, message.getMsg(), Toast.LENGTH_SHORT).show();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

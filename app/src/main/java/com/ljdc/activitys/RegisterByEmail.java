package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.model.Message;
import com.ljdc.utils.Act;
import com.ljdc.utils.VolleyPostRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class RegisterByEmail extends Activity implements View.OnClickListener, Response.Listener<String> {


    private android.widget.EditText et_account;
    private android.widget.EditText et_email;
    private android.widget.EditText et_password;
    private android.widget.EditText et_password_confirm;
    private android.widget.Button btn_registe;
    private android.widget.LinearLayout ll_createByPhone;

    private TextView title;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_email);
        initView();

    }

    private void initView() {

        TextView title = (TextView) findViewById(R.id.title_center_tv);
        title.setText(R.string.registe_by_email);
        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));


        this.ll_createByPhone = (LinearLayout) findViewById(R.id.ll_createByPhone);
        this.btn_registe = (Button) findViewById(R.id.btn_registe);
        this.et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
        this.et_password = (EditText) findViewById(R.id.et_password);
        this.et_email = (EditText) findViewById(R.id.et_email);
        this.et_account = (EditText) findViewById(R.id.et_account);

        ll_createByPhone.setOnClickListener(this);
        btn_registe.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String nickname = et_account.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();
        String pwd_verify = et_password_confirm.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_registe:
                if (TextUtils.isEmpty(nickname) | TextUtils.isEmpty(email) | TextUtils.isEmpty(pwd) | TextUtils.isEmpty(pwd_verify))
                    break;
                else if (!pwd.contentEquals(pwd_verify)) {
                    Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
                    break;
                }
                Map<String, String> parms = new HashMap<String, String>();
                parms.put("nickname", nickname);
                parms.put("email", email);
                parms.put("password", pwd);

                //TODO 发起网络请求
                new VolleyPostRequest(this).postRequest(parms, Config.REGISTER_URL, this);

                break;
            case R.id.ll_createByPhone:
                Act.toAct(this, RegisterByPhone.class);
                this.finish();
                break;
            case R.id.title_left_layout:
                this.finish();
                break;

        }
    }

    /**
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

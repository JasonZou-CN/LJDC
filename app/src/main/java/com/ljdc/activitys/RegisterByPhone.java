package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ljdc.R;
import com.ljdc.utils.Act;
import com.ljdc.utils.ToastUtils;

@SuppressWarnings("ALL")
public class RegisterByPhone extends Activity implements View.OnClickListener {
    private TextView title;
    private EditText et_phone, et_authCode;
    private Button btn_auth, btn_next;
    private LinearLayout ll_createByEmail;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_phone);
        initView();
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_authCode = (EditText) findViewById(R.id.et_authCode);
        btn_auth = (Button) findViewById(R.id.btn_auth);
        btn_next = (Button) findViewById(R.id.btn_next);
        ll_createByEmail = (LinearLayout) findViewById(R.id.ll_createByEmail);

        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        title = (TextView) findViewById(R.id.title_center_tv);
        title.setText("手机注册");

        btn_auth.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        ll_createByEmail.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //TODO 设置处理逻辑
        switch (view.getId()) {
            case R.id.ll_createByEmail:
                Act.toAct(this, RegisterByEmail.class);
                break;
            case R.id.btn_auth:
                ToastUtils.showShort(this, "xxx");
                break;
            case R.id.btn_next:
                Act.toAct(this, RegisterConfirmPassword.class);
                break;
            case R.id.title_left_layout:
                this.finish();
                break;
        }
    }
}

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

@SuppressWarnings("deprecation")
public class RegisterByEmail extends Activity implements View.OnClickListener {


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
        switch (view.getId()) {
            case R.id.btn_registe:
                break;
            case R.id.ll_createByPhone:
                Act.toAct(this,RegisterByPhone.class);
                break;
            case R.id.title_left_layout:
                this.finish();
                break;

        }
    }
}

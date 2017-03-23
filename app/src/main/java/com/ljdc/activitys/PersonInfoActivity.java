package com.ljdc.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.database.DBHelper;
import com.ljdc.pojo.UserServer;
import com.ljdc.utils.Act;

import java.sql.SQLException;
import java.util.List;

public class PersonInfoActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sp;
    private TextView title;
    private TextView submit;
    private DBHelper dbHelper = null;
    private Dao dao = null;
    private UserServer user = null;
    private EditText nickname, phone, email;
    private TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initView();
        initData();

    }

    private void initView() {
        findViewById(R.id.logout).setOnClickListener(this);

        title = (TextView) findViewById(R.id.title_center_tv);
        title.setText("个人信息");

        View v = findViewById(R.id.title_right_layout);
        v.setVisibility(View.VISIBLE);
        submit = (TextView) v.findViewById(R.id.title_right_tv);
        submit.setText("完成");
        submit.setOnClickListener(this);

        nickname = (EditText) findViewById(R.id.nickname);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);

        id = (TextView) findViewById(R.id.id);

    }

    private void initData() {
        dbHelper = DBHelper.getHelper(this);
        try {
            dao = dbHelper.getDao(UserServer.class);
            List list = dao.queryForAll();
            if (list != null && list.size() != 0) {
                user = (UserServer) list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null) {
            nickname.setText(user.nickname == null ? "" : user.nickname);
            phone.setText(user.phone == null ? "" : user.phone);
            email.setText(user.email == null ? "" : user.email);
            id.setText(user.userId + "");
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                // 设置数据库为未初始化状态，再次登录需要重新初始化
                sp = getSharedPreferences(Config.SP_LJDC, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(Config.SP_IS_LOGIN, false);
                editor.putBoolean(Config.SP_IS_DATABASE_INITED, false);
                editor.putString(Config.PARAM_USERID, "");
                editor.commit();
                Act.toAct(this, LoginActivity.class);
                this.finish();
                break;
            case R.id.title_right_tv:
                user.nickname = nickname.getText().toString().trim();
                user.phone = phone.getText().toString().trim();
                user.email = email.getText().toString().trim();
                user.status = 1;
                try {
                    dao.update(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                break;
        }
    }
}

package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import com.ljdc.R;
import com.ljdc.app.Config;
import com.ljdc.utils.Act;
import com.ljdc.utils.Utils;

@SuppressWarnings("ALL")
public class RegisterByPhone extends Activity implements View.OnClickListener {
    Handler handler;
    int count = 30;
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

        initSmsSDK();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == -9) {
                    btn_auth.setText("重新发送(" + count-- + ")");
                } else if (msg.what == -8) {
                    btn_auth.setText("获取验证码");
                    btn_auth.setClickable(true);
                    count = 30;
                } else {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e("event", "event=" + event);
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 短信注册成功后，返回MainActivity,然后提示新好友
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 验证码验证成功
                            Toast.makeText(getApplicationContext(), "验证码验证成功",
                                    Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", et_phone.getText().toString().trim());
                            Act.toAct(RegisterByPhone.this, RegisterConfirmPassword.class, bundle);
                            RegisterByPhone.this.finish();
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            Toast.makeText(getApplicationContext(), "验证码请求已经发送",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            ((Throwable) data).printStackTrace();
                        }
                    }
                }
            }
        };
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

    /**
     * 初始化短信SDK
     */
    private void initSmsSDK() {
        //收不到短信 换一组App_KEY
        SMSSDK.initSDK(this, Config.SMS_APP_KEY, Config.SMS_APP_SECRET);
        EventHandler eventHandler = new EventHandler() {
            /**
             * 在操作之后被触发
             *
             * @param event
             *            参数1
             * @param result
             *            参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.
             *            RESULT_ERROR表示操作失败
             * @param data
             *            事件操作的结果
             */
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
//        this.unregisterReceiver(smsBroadcastReceiver);
        SMSSDK.unregisterAllEventHandler();
    }


    @Override
    public void onClick(View view) {
        String phone = et_phone.getText().toString().trim();
        String auth_coe = et_authCode.getText().toString().trim();
        //TODO 设置处理逻辑
        switch (view.getId()) {
            case R.id.ll_createByEmail:
                Act.toAct(this, RegisterByEmail.class);
                break;
            case R.id.btn_auth:
                if (!Utils.isMobileNO(phone))
                    break;
                SMSSDK.getVerificationCode("86", phone);
                btn_auth.setClickable(false);
                btn_auth.setText("重新发送(" + count-- + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 30; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            case R.id.btn_next:
                if (phone.isEmpty()) {
                    Toast.makeText(this, "请先验证手机号", Toast.LENGTH_SHORT).show();
                    break;
                }
                SMSSDK.submitVerificationCode("86",phone, auth_coe);
                break;
            case R.id.title_left_layout:
                this.finish();
                break;
        }
    }
}

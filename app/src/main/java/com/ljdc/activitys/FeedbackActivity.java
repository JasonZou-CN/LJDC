package com.ljdc.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ljdc.R;

public class FeedbackActivity extends Activity implements View.OnClickListener {
    private TextView title;
    private View back;
    private Button submit;
    private EditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_center_tv);
        title.setText("意见反馈");

        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        feedback = (EditText) findViewById(R.id.feedback);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_layout:
                finish();
                break;
            case R.id.submit:
                Intent i = new Intent(Intent.ACTION_SEND);
                // i.setType("text/plain"); //模拟器请使用这行
                i.setType("message/rfc822"); // 真机上使用这行
                i.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"V587jasonzou@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "用户反馈");
                i.putExtra(Intent.EXTRA_TEXT, feedback.getText().toString().trim());
                startActivity(Intent.createChooser(i,
                        "选择邮箱软件"));
                break;

        }
    }
}

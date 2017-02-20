package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ljdc.R;

public class FeedbackActivity extends Activity implements View.OnClickListener {
    private TextView title;
    private View back;

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
        }
    }
}

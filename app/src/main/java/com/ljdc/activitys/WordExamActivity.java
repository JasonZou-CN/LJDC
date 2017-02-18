package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.ljdc.R;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("ALL")
public class WordExamActivity extends Activity implements View.OnClickListener {

    private TextView rightView, title;
    private View back;
    private ProgressBar progressBar;
    private int progressNum = 10;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_exam);

        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.title_right_layout).setVisibility(View.VISIBLE);
        rightView = (TextView) findViewById(R.id.title_right_tv);
        title = (TextView) findViewById(R.id.title_center_tv);
        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        back.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(progressNum);
        progressBar.setProgress(progressNum);
    }

    private void initData() {
        title.setText("生词练习");
        rightView.setText("5/12");

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (progressNum > 0)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(--progressNum);
                        }
                    });
                else
                    timer.cancel();
            }
        }, 0, 1000);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_layout:
                finish();
                break;
        }
    }

}

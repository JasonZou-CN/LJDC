package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.ljdc.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SuppressWarnings("ALL")
public class ChangePlanActivity extends Activity implements View.OnClickListener,NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter {
    private TextView tv_title;
    private View back;
    private NumberPicker dayPicker, wordsPicker;
    private int totalNum = 1000; //剩余单词


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_plan);
        initView();

    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.title_center_tv);
        tv_title.setText("我的计划");

        back = findViewById(R.id.title_left_layout);
        back.setVisibility(View.VISIBLE);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        back.setOnClickListener(this);

        dayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        wordsPicker = (NumberPicker) findViewById(R.id.wordsPicker);

       initData();

    }

    private void initData(){



        String[] words = {"10","15","20","25","30","40","50","60","70"};
        wordsPicker.setDisplayedValues(words);
        wordsPicker.setMinValue(0);
        wordsPicker.setMaxValue(words.length - 1);
        wordsPicker.setValue(4);
        wordsPicker.setFormatter(this);
        wordsPicker.setOnValueChangedListener(this);
        wordsPicker.setOnScrollListener(this);

        List<String> daysList = new ArrayList<String>();
        int oneDay,days;
        for (String num : words
                ) {
            oneDay = Integer.parseInt(num);
            days = totalNum/oneDay;
            daysList.add(days+"");
        }
        String[] daysArr = daysList.toArray(new String[daysList.size()]);
        for (String s:daysList
             ) {

        }

        dayPicker.setDisplayedValues(daysArr);
        dayPicker.setMinValue(0);
        dayPicker.setMaxValue(daysArr.length - 1);
        dayPicker.setValue(4);
        dayPicker.setFormatter(this);
        dayPicker.setOnValueChangedListener(this);
        dayPicker.setOnScrollListener(this);
    }

    @Override
    public void onClick(View view) {
        //TODO 设置处理逻辑
        switch (view.getId()) {
            case R.id.title_left_layout:
                finish();
                break;

        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(
                this,
                "原来的值 " + oldVal + "--新值: "
                        + newVal, Toast.LENGTH_SHORT).show();
        switch (picker.getId()) {
            case R.id.dayPicker:
                wordsPicker.setValue(dayPicker.getValue());
                break;
            case R.id.wordsPicker:
                dayPicker.setValue(wordsPicker.getValue());

                break;
        }

    }

    @Override
    public String format(int i) {
        return null;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
//                Toast.makeText(this, "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG)
//                        .show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
//                Toast.makeText(this, "不滑动", Toast.LENGTH_LONG).show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                Toast.makeText(this, "滑动中", Toast.LENGTH_LONG)
//                        .show();
                break;
        }
    }
}

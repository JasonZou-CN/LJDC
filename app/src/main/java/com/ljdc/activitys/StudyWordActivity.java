package com.ljdc.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ljdc.R;
import com.ljdc.model.Word;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class StudyWordActivity extends Activity implements View.OnClickListener {
    TextView title;
    private View back;
    private ViewPager vp_container;
    private QuickPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_word);


        initData();
        initView();
    }

    private void initView() {
        back = findViewById(R.id.title_left_layout);
        back.setBackground(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        title = (TextView) findViewById(R.id.title_center_tv);
        title.setText("学习 ：1/5");

        vp_container = (ViewPager) findViewById(R.id.word_item_container);
        vp_container.setAdapter(pageAdapter);
        vp_container.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText("学习 ："+(position+1)+"/5");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private void initData() {
        List<Word> data = new ArrayList<Word>(4);
        data.add(new Word());
        data.add(new Word());
        data.add(new Word());
        data.add(new Word());
        data.add(new Word());
        pageAdapter = new QuickPageAdapter(data, getLayoutInflater());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_layout:
                finish();
                break;
            case R.id.pron:
                Toast.makeText(this, "发音", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class QuickPageAdapter extends PagerAdapter {
        private List<Word> mDataList;
        private LayoutInflater inflater;
        private List<View> viewList;
        private View view;

        public QuickPageAdapter(List<Word> mDataList, LayoutInflater inflater) {
            this.mDataList = mDataList;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return mDataList.size();
//            return 1;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if (viewList == null) {
//                设定初始容量
                viewList = new ArrayList<View>();
                //TODO 数据初始化
            }
            if(viewList.size()<mDataList.size()){

                view = inflater.inflate(R.layout.word_info_item, vp_container, false);
                view.findViewById(R.id.pron).setOnClickListener(StudyWordActivity.this);

                {

                    LinearLayout sent_container = (LinearLayout) view.findViewById(R.id.sent_container);
                    LinearLayout desc_container = (LinearLayout) view.findViewById(R.id.desc_container);
                    //TODO 动态添加组件
                    View sentItem = inflater.inflate(R.layout.sent_item,sent_container,false);
                    sent_container.addView(sentItem);

                    ((TextView)sentItem.findViewById(R.id.en_sent)).setText("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

                    ViewGroup.LayoutParams layoutParams = ((TextView) view.findViewById(R.id.desc_item)).getLayoutParams();
                    TextView textView = new TextView(StudyWordActivity.this);
                    textView.setText("XXXXXXXXXXX");
                    textView.setLayoutParams(layoutParams);
                    desc_container.addView(textView);

                }
                viewList.add(position, view);
            }
            container.addView(viewList.get(position));
            Log.i("QuickPageAdapter", "实例化position:" + position);
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("QuickPageAdapter", "销毁位置" + position);
            container.removeView(viewList.get(position));
        }
    }
}

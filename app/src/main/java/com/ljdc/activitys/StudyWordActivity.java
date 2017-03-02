package com.ljdc.activitys;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
import com.ljdc.pojo.WordLibServer;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class StudyWordActivity extends Activity implements View.OnClickListener {
    TextView title;
    private View back;
    private ViewPager vp_container;
    private QuickPageAdapter pageAdapter;
    private WordLibServer word; //暂存当前显示的单词信息


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
        if (word != null) {
            title.setText("单词详情");
        } else {
            //单词计划进来的
            title.setText("学习 ：1/5");
        }


        vp_container = (ViewPager) findViewById(R.id.word_item_container);
        vp_container.setAdapter(pageAdapter);
        vp_container.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText("学习 ：" + (position + 1) + "/5");//position 从0 开始

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initData() {
        Intent i = getIntent();
        word = (WordLibServer) i.getSerializableExtra("searchWord");
        if (word == null) {
            //TODO 不是查词过来的，在此处产生数据
/*            List<Word> data = new ArrayList<Word>(4);
            data.add(new Word());
            data.add(new Word());
            data.add(new Word());
            data.add(new Word());
            data.add(new Word());
            pageAdapter = new QuickPageAdapter(data, getLayoutInflater());*/
        } else {
            List<WordLibServer> data = new ArrayList<WordLibServer>();
            data.add(word);
            pageAdapter = new QuickPageAdapter(data, getLayoutInflater());
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_layout:
                finish();
                break;
            case R.id.pron:
                Toast.makeText(this, "发音", Toast.LENGTH_SHORT).show();

                if (word != null) {
                    MediaPlayer mp = new MediaPlayer();//构建MediaPlayer对象
                    try {
                        mp.setDataSource(word.pronUrlEn);//设置文件路径
                        mp.prepare();//准备
                        mp.start();//开始播放
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }

    public class QuickPageAdapter extends PagerAdapter {
        private List<WordLibServer> mDataList;
        private LayoutInflater inflater;
        private List<View> viewList;
        private View view;  //资源容器
        private TextView wordDesc;//释义
        private TextView pron_en_us; //发音 字符串
        private WordLibServer word;
        private TextView tv_word;


        public QuickPageAdapter(List<WordLibServer> mDataList, LayoutInflater inflater) {
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
            if (viewList.size() < mDataList.size()) {
                word = mDataList.get(position);

                view = inflater.inflate(R.layout.word_info_item, vp_container, false);
                view.findViewById(R.id.pron).setOnClickListener(StudyWordActivity.this);
                pron_en_us = (TextView) view.findViewById(R.id.pron_en_us);
                wordDesc = (TextView) view.findViewById(R.id.wordDesc);
                tv_word = (TextView) view.findViewById(R.id.word_item);

                StringBuffer str = new StringBuffer();
                str.append(word.pos1 + " ");
                str.append(word.acceptation1);
                if (null != word.acceptation2) {
                    str.append("\n");
                    str.append(word.pos2 + " ");
                    str.append(word.acceptation2);
                }
                if (null != word.acceptation3) {
                    str.append("\n");
                    str.append(word.pos3 + " ");
                    str.append(word.acceptation3);
                }
                if (null != word.acceptation4) {
                    str.append("\n");
                    str.append(word.pos4 + " ");
                    str.append(word.acceptation4);
                }
                wordDesc.setText(str.toString());
                pron_en_us.setText("[" + word.pronStrEn + "]");
                tv_word.setText(word.word);


                //TODO 动态添加组件 遍历List  【例句】
                LinearLayout sent_container = (LinearLayout) view.findViewById(R.id.sent_container);
                View sentItem = inflater.inflate(R.layout.sent_item, sent_container, false);
                sent_container.addView(sentItem);
                ((TextView) sentItem.findViewById(R.id.en_sent)).setText(word.sentEn1);
                ((TextView) sentItem.findViewById(R.id.ch_sent)).setText(word.sentTrans1);
                if (null != word.sentEn2) {
                    View sentItem2 = inflater.inflate(R.layout.sent_item, sent_container, false);
                    sent_container.addView(sentItem2);
                    ((TextView) sentItem2.findViewById(R.id.en_sent)).setText(word.sentEn2);
                    ((TextView) sentItem2.findViewById(R.id.ch_sent)).setText(word.sentTrans2);
                }
                if (null != word.sentEn3) {
                    View sentItem3 = inflater.inflate(R.layout.sent_item, sent_container, false);
                    sent_container.addView(sentItem3);
                    ((TextView) sentItem3.findViewById(R.id.en_sent)).setText(word.sentEn3);
                    ((TextView) sentItem3.findViewById(R.id.ch_sent)).setText(word.sentTrans3);
                }
                if (null != word.sentEn4) {
                    View sentItem4 = inflater.inflate(R.layout.sent_item, sent_container, false);
                    sent_container.addView(sentItem4);
                    ((TextView) sentItem4.findViewById(R.id.en_sent)).setText(word.sentEn4);
                    ((TextView) sentItem4.findViewById(R.id.ch_sent)).setText(word.sentTrans4);
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

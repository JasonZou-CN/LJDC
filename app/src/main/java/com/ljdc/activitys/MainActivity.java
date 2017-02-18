package com.ljdc.activitys;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.ljdc.R;
import com.ljdc.fragments.BlankFragment;
import com.ljdc.fragments.HomeFragment;
import com.ljdc.fragments.MineFragment;
import com.ljdc.fragments.ReviewFragment;

import static com.ljdc.R.id.title_left_layout;

@SuppressWarnings("ALL")
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    public TextView title;
    private View back;
    private FragmentManager fm = null;
    private Fragment[] fragments = {null, null, null};
    private RadioGroup rg_nav_tab;
    private RadioButton[] rbs = new RadioButton[3];
    private ViewPager vp_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        initView();

    }

    private void initView() {

        title = (TextView) findViewById(R.id.title_center_tv);

        rbs[0] = (RadioButton) findViewById(R.id.rb_home);
        rbs[1] = (RadioButton) findViewById(R.id.rb_review);
        rbs[2] = (RadioButton) findViewById(R.id.rb_me);
        rg_nav_tab = (RadioGroup) findViewById(R.id.rg_nav);
        rg_nav_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {

                    case R.id.rb_home:
                        changeRadioButtonState(i);
                        vp_content.setCurrentItem(0);
                        title.setText(((HomeFragment) fragments[0]).titleStr);
                        break;
                    case R.id.rb_review:
                        changeRadioButtonState(i);
                        vp_content.setCurrentItem(1);
                        title.setText("复习");
                        break;

                    case R.id.rb_me:
                        changeRadioButtonState(i);
                        vp_content.setCurrentItem(2);
                        title.setText("我的");
                        break;
                }
            }
        });


        vp_content = (ViewPager) findViewById(R.id.frag_container);
        vp_content.setOffscreenPageLimit(3);
        vp_content.setAdapter(new MyPagerAdapter(fm));
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                ((RadioButton)rg_nav_tab.getChildAt(arg0)).setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private void changeRadioButtonState(int resId) {
        for (int j = 0; j < rg_nav_tab.getChildCount(); j++) {
            rbs[j].setTextColor(getResources().getColor(R.color.white));
        }
        ((RadioButton) findViewById(resId)).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case title_left_layout:
                break;
            default:
                break;
        }
    }

    /**
     * ViewPager滑动时间监听
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (null == fragments[0]) {
                        fragments[0] = new HomeFragment();
                    }

                    break;
                case 1:
                    if (null == fragments[1]) {
                        fragments[1] = new ReviewFragment();
                    }
                    break;
                case 2:
                    if (null == fragments[2]) {
                        fragments[2] = new MineFragment();
                    }
                    break;
            }
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}

package com.ljdc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.ljdc.R;
import com.ljdc.activitys.MainActivity;

/**
 * @Describe: TODO 首页
 * * * * ****** Created by ZOUXU ********
 */
public class HomeFragment extends Fragment implements OnClickListener {

    // 标题栏
    TextView title;
    public String titleStr;
    private FragmentManager fm = null;
    private FragmentTransaction ft = null;
    private Fragment[] contacts = {null, null, null};
    private RadioGroup rg_tab;
    private View content = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.frag_tab_home, null);
        initComponet(inflater);
        loadFragment(0);
        return content;

    }

    private void initComponet(LayoutInflater inflater) {

        title = ((MainActivity) getActivity()).title;

        rg_tab = (RadioGroup) content.findViewById(R.id.rg_opt);
        rg_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // 设置不同的圆角背景
                switch (checkedId) {
                    case R.id.radio_left:
                        // 加载Fragment
                        loadFragment(0);
                        titleStr = "背单词";
                        break;
                    case R.id.radio_middle:
                        // 加载Fragment
                        loadFragment(1);
                        titleStr = "进度";

                        break;

                    case R.id.radio_right:
                        titleStr = "词库";
                        // 加载Fragment
                        loadFragment(2);
                        break;
                }


            }
        });
        ((RadioButton) rg_tab.getChildAt(0)).setChecked(true);
    }

    private void loadFragment(int position) {

        ft = fm.beginTransaction();
        int length = contacts.length;
        for (int i = 0; i < length; i++) {
            if (contacts[i] != null) {
                ft.hide(contacts[i]);
            }
        }
        switch (position) {
            case 0:
                if (null == contacts[0]) {
                    contacts[0] = new HomeTabAFragment();
                    ft.add(R.id.frag_my_contacts_container, contacts[0],
                            HomeTabAFragment.class.getSimpleName());
                } else {
                    ft.show(contacts[0]);
                }

                title.setText("背单词");
                break;
            case 1:
                if (null == contacts[1]) {
                    contacts[1] = new HomeTabBFragment();
                    ft.add(R.id.frag_my_contacts_container, contacts[1],
                            BlankFragment.class.getSimpleName());
                } else {
                    ft.show(contacts[1]);
                }
                title.setText("进度");
                break;
            case 2:
                if (null == contacts[2]) {
                    contacts[2] = new HomeTabCFragment();
                    ft.add(R.id.frag_my_contacts_container, contacts[2],
                            BlankFragment.class.getSimpleName());
                } else {
                    ft.show(contacts[2]);
                }
                title.setText("词库");
                break;
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.title_right_tv:
                break;
        }
    }
}

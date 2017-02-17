package com.ljdc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljdc.R;
import com.ljdc.activitys.StudyWordActivity;
import com.ljdc.utils.Act;
import com.ljdc.views.RoundProgressBar;

/**@Desc 首页-背单词
 * A simple {@link Fragment} subclass.
 */
public class HomeTabAFragment extends Fragment implements View.OnClickListener{

    private ViewGroup container;
    private RoundProgressBar progressRound;


    public HomeTabAFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.fragment_study_word, container, false);
        container.findViewById(R.id.study).setOnClickListener(this);

        progressRound = (RoundProgressBar) container.findViewById(R.id.roundProgress);
        progressRound.setMax(100);
        progressRound.setValue(80);

        return container;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.study:
                Act.toAct(getActivity(), StudyWordActivity.class);
                break;
        }
    }
}

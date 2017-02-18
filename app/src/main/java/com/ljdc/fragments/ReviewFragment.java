package com.ljdc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ljdc.R;
import com.ljdc.activitys.MainActivity;
import com.ljdc.activitys.WordExamActivity;
import com.ljdc.utils.Act;

/**
 * @Describe: TODO 首页
 * * * * ****** Created by ZOUXU ********
 */
public class ReviewFragment extends Fragment implements OnClickListener {

    // 标题栏
    TextView title;
    private View content = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_review, null);
        initView(inflater);
        return content;

    }

    private void initView(LayoutInflater inflater) {

        title = ((MainActivity) getActivity()).title;
        title.setText("复习");

        content.findViewById(R.id.wrodFirstExam).setOnClickListener(this);
        content.findViewById(R.id.wrodSecondExam).setOnClickListener(this);
        content.findViewById(R.id.wrodThirdExam).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.wrodFirstExam:
                Toast.makeText(getContext(), "FIRST", Toast.LENGTH_SHORT).show();
                Act.toAct(getContext(), WordExamActivity.class);
                break;
            case R.id.wrodSecondExam:
                Toast.makeText(getContext(), "second", Toast.LENGTH_SHORT).show();
                Act.toAct(getContext(), WordExamActivity.class);
                break;
            case R.id.wrodThirdExam:
                Toast.makeText(getContext(), "third", Toast.LENGTH_SHORT).show();
                Act.toAct(getContext(), WordExamActivity.class);
                break;
        }
    }
}

package com.ljdc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ljdc.R;
import com.ljdc.activitys.AboutAppActivity;
import com.ljdc.activitys.FeedbackActivity;
import com.ljdc.activitys.PersonInfoActivity;
import com.ljdc.utils.Act;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private CircleImageView circleImageView;
    private ViewGroup contView;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.contView = (ViewGroup) inflater.inflate(R.layout.fragment_mine, null);
        initView(inflater);
        return this.contView;
    }

    private void initView(LayoutInflater inflater) {
        circleImageView = (CircleImageView) this.contView.findViewById(R.id.circleHeadView);
        circleImageView.setOnClickListener(this);

        contView.findViewById(R.id.aboutApp).setOnClickListener(this);
        contView.findViewById(R.id.feedback).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circleHeadView:
                Act.toAct(getContext(), PersonInfoActivity.class);
                break;
            case R.id.feedback:
                Act.toAct(getContext(), FeedbackActivity.class);

                break;
            case R.id.aboutApp:
                Act.toAct(getContext(), AboutAppActivity.class);

                break;
        }
    }
}

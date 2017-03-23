package com.ljdc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.ljdc.R;
import com.ljdc.activitys.AboutAppActivity;
import com.ljdc.activitys.FeedbackActivity;
import com.ljdc.activitys.PersonInfoActivity;
import com.ljdc.app.Config;
import com.ljdc.utils.Act;
import com.ljdc.utils.DataSyncUtil;
import com.ljdc.utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class MineFragment extends Fragment implements View.OnClickListener {

    private CircleImageView circleImageView;
    private ViewGroup contView;
    private ImageView defaultPron;

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
        contView.findViewById(R.id.sync).setOnClickListener(this);
        contView.findViewById(R.id.default_pron).setOnClickListener(this);
        defaultPron = (ImageView) contView.findViewById(R.id.iv_default_pron);

        String s = Utils.getPreference(getActivity(), Config.SP_DEFAULT_PRON).trim();
        int pron = 0;
        if (!s.equals(""))
            pron = Integer.parseInt(s);
        switch (pron) {
            case 0://英语发音
                defaultPron.setImageDrawable(getResources().getDrawable(R.drawable.ic_uk_flag));
                break;
            case 1://美语发音
                defaultPron.setImageDrawable(getResources().getDrawable(R.drawable.ic_us_flag));
                break;
        }
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
            case R.id.default_pron://外围RelativeLayout
                String s = Utils.getPreference(getActivity(), Config.SP_DEFAULT_PRON).trim();
                int pron = 0;
                if (!s.equals(""))
                    pron = Integer.parseInt(s);

                switch (pron) {
                    case 0://英语发音
                        defaultPron.setImageDrawable(getResources().getDrawable(R.drawable.ic_us_flag));
                        Utils.savePreference(getActivity(), Config.SP_DEFAULT_PRON, 1 + "");
                        break;
                    case 1://美语发音
                        defaultPron.setImageDrawable(getResources().getDrawable(R.drawable.ic_uk_flag));
                        Utils.savePreference(getActivity(), Config.SP_DEFAULT_PRON, 0 + "");
                        break;
                }

                break;
            case R.id.sync://数据同步
                Toast.makeText(getActivity(), "正在同步数据", Toast.LENGTH_SHORT).show();
                new DataSyncUtil().syncUserData(getContext());

                break;
        }
    }
}

package com.ljdc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.ljdc.R;
import com.ljdc.activitys.StudyWordActivity;
import com.ljdc.adapters.LibrarysAdapterLV;
import com.ljdc.model.LibraryInfo;
import com.ljdc.utils.Act;

import java.util.ArrayList;
import java.util.List;

/**@Desc 首页-背单词
 * A simple {@link Fragment} subclass.
 */
public class HomeTabCFragment extends Fragment implements View.OnClickListener{

    private ViewGroup container;
    private ListView lv_librarys;
    private  LayoutInflater inflater;


    public HomeTabCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater  = inflater;
        container = (ViewGroup) inflater.inflate(R.layout.frag_home_sub_library,container, false);
        lv_librarys = (ListView) container.findViewById(R.id.lv_librarys);
        List<LibraryInfo> data = new ArrayList<LibraryInfo>();
        data.add(new LibraryInfo());
        data.add(new LibraryInfo());
        data.add(new LibraryInfo());
        data.add(new LibraryInfo());
        lv_librarys.setAdapter(new LibrarysAdapterLV(getContext(),data));

        return container;


    }

   void initView(){


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

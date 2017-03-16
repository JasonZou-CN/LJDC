package com.ljdc.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.ljdc.R;
import com.ljdc.activitys.ChangePlanActivity;
import com.ljdc.pojo.Libs;
import com.ljdc.utils.Act;

import java.util.List;

public class LibrarysAdapterLV extends BaseAdapter implements OnClickListener{
    List<Libs> mData = null;
    private LayoutInflater mInflater;
    private Context context;

    public LibrarysAdapterLV(Context context, List<Libs> mData) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.library_item, null);
            holder.bookImg = (ImageView) convertView
                    .findViewById(R.id.bookImg);
            holder.bookName = (TextView) convertView.findViewById(R.id.bookName);
            holder.wordsNum = (TextView) convertView.findViewById(R.id.wordsNum);
            holder.changePlan = (Button) convertView
                    .findViewById(R.id.changePlan);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //TODO 加载网络图片  holder.bookImg

		holder.bookName.setText(mData.get(position).libName+"");
		holder.wordsNum.setText(mData.get(position).totalNum+"");
        holder.changePlan.setTag(position);
        holder.changePlan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int pos = Integer.valueOf((Integer) v.getTag());
                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("libs",mData.get(pos));
                Act.toAct(context, ChangePlanActivity.class,bundle);

            }
        });


        return convertView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    private class ViewHolder {
        ImageView bookImg;
        TextView bookName;
        TextView wordsNum;
        Button changePlan;
    }
}
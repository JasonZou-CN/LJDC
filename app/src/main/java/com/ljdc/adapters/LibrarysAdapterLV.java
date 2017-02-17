package com.ljdc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.ljdc.R;
import com.ljdc.model.LibraryInfo;

import java.util.List;

public class LibrarysAdapterLV extends BaseAdapter {
	private LayoutInflater mInflater;
	List<LibraryInfo> mData = null;
	private Context context;

	private class ViewHolder {
		ImageView bookImg;
		TextView bookName;
		TextView wordsNum;
		Button changePlan;
	}

	public LibrarysAdapterLV(Context context, List<LibraryInfo> mData) {
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

//		holder.bookName.setText(mData.get(position).getBookName());
//		holder.wordsNum.setText(mData.get(position).getWordsNum());
		holder.changePlan.setTag(position);
		holder.changePlan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pos = Integer.valueOf((Integer) v.getTag());
				Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();

			}
		});


		return convertView;
	}
}
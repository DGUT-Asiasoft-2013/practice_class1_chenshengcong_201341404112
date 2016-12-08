package com.example.activities.pages;

import java.util.Random;

import com.example.activities.FeedContextActivity;
import com.example.login.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FeedListFragment extends Fragment {

	View view;
	ListView feedsList;
	String data[];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);
			ListView feedsList = (ListView) view.findViewById(R.id.list_feeds);// 绑定feeds页面中的ListView
			feedsList.setAdapter(listAdapter);

			Random rand = new Random();
			data = new String[10 + rand.nextInt(10) % 20];
			for (int i = 0; i < data.length; i++) {
				data[i] = "This row id" + rand.nextInt() % 20;
			}
			feedsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onItemClicked(position);

				}
			});
		}

		return view;
	}

	BaseAdapter listAdapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());// 当converView为空时,获取父容器的布局
				view = inflater.inflate(R.layout.fragment_single_list, null);// 将当前的view设为系统自带的simple_list_item_1;
			} else {
				view = convertView;
			}
			TextView textView1 = (TextView) view.findViewById(R.id.text_list_message);// 安卓自带的textView
																					// id
			textView1.setText("This ROW is " + data[position]);

			return view;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public Object getItem(int position) {

			return data[position];
		}

		@Override
		public int getCount() {

			if (data == null)
				return 0;
			else
				return data.length;
		}
	};

	public void onItemClicked(int position) {
		String text = data[position];
		Intent itnt = new Intent(getActivity(), FeedContextActivity.class);
		itnt.putExtra("text", text);
		startActivity(itnt);
	}
}

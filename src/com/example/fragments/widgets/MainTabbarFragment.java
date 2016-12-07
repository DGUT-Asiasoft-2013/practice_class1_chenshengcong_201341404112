package com.example.fragments.widgets;

import com.example.activities.NewTextActivity;
import com.example.login.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainTabbarFragment extends Fragment {
	View btnNew, tabFeeds, tabNotes, tabSearch, tabMe;
	View[] tabs;
	Button btn_new;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_widget_main_tabbar, null);
		btnNew = view.findViewById(R.id.btn_new);
		tabFeeds = view.findViewById(R.id.tab_feeds);
		tabNotes = view.findViewById(R.id.tab_notes);
		tabSearch = view.findViewById(R.id.tab_search);
		tabMe = view.findViewById(R.id.tab_me);
		btn_new = (Button) view.findViewById(R.id.btn_new);
		tabs = new View[] { tabFeeds, tabNotes, tabSearch, tabMe };
//为每个tabbar条件监听接口
		for (final View tab : tabs) {
			tab.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					onTabClicked(tab);

				}
			});
		}
		
		btn_new.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BeginNewText();
				
			}

			private void BeginNewText() {
				Intent itnt=new Intent(getActivity(), NewTextActivity.class);
				startActivity(itnt);
				
			}
		});

		return view;
	}

	
	public static interface OnTabSelectedListener {
		void onTabSelected(int index);
	}

	OnTabSelectedListener onTabSelectedListener;

	public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
		this.onTabSelectedListener = onTabSelectedListener;
	}

	public void setSelectedItem(int index) {
		if (index >= 0 && index < tabs.length) {
			onTabClicked(tabs[index]);
		}
	}

	void onTabClicked(View tab) {
		int selectedIndex = -1;

		for (int i = 0; i < tabs.length; i++) {
			View otherTab = tabs[i];
			if (otherTab == tab) {
				otherTab.setSelected(true);
				selectedIndex = i;
			} else {
				otherTab.setSelected(false);
			}
		}

		if (onTabSelectedListener != null && selectedIndex >= 0) {
			onTabSelectedListener.onTabSelected(selectedIndex);
		}
	}
}
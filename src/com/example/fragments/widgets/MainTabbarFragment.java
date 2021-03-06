package com.example.fragments.widgets;

import com.example.login.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
		
	

		return view;
	}

	
	public static interface OnTabSelectedListener {
		void onTabSelected(int index);
	}

	OnTabSelectedListener onTabSelectedListener;
	public int getSelectedIndex;

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
	
	//获取选中的Tabbar的index
	public int getSelectedIndex(){
		 		for(int i=0; i<tabs.length; i++){
		 			if(tabs[i].isSelected()) return i;
		 		}
		 		
		 		return -1;
		 	}
}

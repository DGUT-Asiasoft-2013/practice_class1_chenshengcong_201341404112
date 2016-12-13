package com.example.activities;

import com.example.login.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.activities.pages.FeedListFragment;
import com.example.activities.pages.MyProfileFragment;
import com.example.activities.pages.NoteListFragment;
import com.example.activities.pages.SearchPageFragment;
import com.example.fragments.widgets.*;
import com.example.fragments.widgets.MainTabbarFragment.OnTabSelectedListener;

public class HelloWorldActivity extends Activity {
	FeedListFragment contentFeedList = new FeedListFragment();
	NoteListFragment contentNoteList = new NoteListFragment();
	SearchPageFragment contentSearchPage = new SearchPageFragment();
	MyProfileFragment contentMyProfile = new MyProfileFragment();
	MainTabbarFragment tabbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helloworld);
		Button btn_new = (Button) findViewById(R.id.btn_new);

		tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
		tabbar.setOnTabSelectedListener(new OnTabSelectedListener() {

			@Override
			public void onTabSelected(int index) {
				
				changeContentFragment(index);
			}
		});

		//为"+"按钮添加监听接口
		btn_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BeginNewText();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		//防止点击发送文章后返回为空导致程序奔溃,当已点的index<0;将它默认设为0
		if (tabbar.getSelectedIndex()< 0) {
			tabbar.setSelectedItem(0);
		}
	}

	void changeContentFragment(int index) {
		Fragment newFrag = null;

		switch (index) {
		case 0:
			newFrag = contentFeedList;
			break;
		case 1:
			newFrag = contentNoteList;
			break;
		case 2:
			newFrag = contentSearchPage;
			break;
		case 3:
			newFrag = contentMyProfile;
			break;

		default:
			break;
		}

		if (newFrag == null)
			return;

		getFragmentManager().beginTransaction().replace(R.id.content, newFrag).commit();
	}

	void BeginNewText() {
		Intent itnt = new Intent(this, NewTextActivity.class);
		startActivity(itnt);
		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);

	}
	
	
}

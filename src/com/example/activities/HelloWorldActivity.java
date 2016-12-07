package com.example.activities;

import com.example.login.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helloworld);
		
		tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
		tabbar.setOnTabSelectedListener(new OnTabSelectedListener() {
			
			@Override
			public void onTabSelected(int index) {
				// TODO Auto-generated method stub
				changeContentFragment(index);
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();

		tabbar.setSelectedItem(0);
	}

	void changeContentFragment(int index){
		Fragment newFrag = null;

		switch (index) {
		case 0: newFrag = contentFeedList; break;
		case 1: newFrag = contentNoteList; break;
		case 2: newFrag = contentSearchPage; break;
		case 3: newFrag = contentMyProfile; break;

		default:break;
		}

		if(newFrag==null) return;

		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content, newFrag)
		.commit();
	}
}

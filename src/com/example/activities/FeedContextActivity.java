package com.example.activities;

import com.example.login.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class FeedContextActivity extends Activity {
	
	Button btnZan;
	BUtton addComment;
	Button collect;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedcontext);
		ListView  listComment = (ListView) findViewById(R.id.list_feed_comment);
		TextView textFeedMessage = (TextView) findViewById(R.id.text_feed_list_message);
		TextView textFeedAuthorName = (TextView) findViewById(R.id.text_feed_author_name);
		TextView textFeedEditDate = (TextView) findViewById(R.id.text_feed_edit_time);
		textFeedMessage.setText(getIntent().getStringExtra("text"));//获取来自feed页传来的text参数
		textFeedAuthorName.setText(getIntent().getStringExtra("authorName"));
		textFeedEditDate.setText(getIntent().getStringExtra("editDate"));
		
	}

}

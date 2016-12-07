package com.example.activities;

import com.example.login.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FeedContextActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedcontext);
		TextView textView = (TextView) findViewById(R.id.text_feedcontext);
		textView.setText(getIntent().getStringExtra("text"));//获取来自feed页传来的text参数
		
	}

}

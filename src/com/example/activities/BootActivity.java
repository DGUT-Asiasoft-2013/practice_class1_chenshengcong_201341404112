package com.example.activities;

import com.example.login.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.sax.StartElementListener;

public class BootActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boot);
	}

	@Override
	protected void onResume() {

		super.onResume();
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			private int abcd = 0;
			@Override
			public void run() {
				StartLoginActivity();
			}
		}, 1000);
	}

	protected void StartLoginActivity() {
			Intent itnt = new Intent(this, LoginActivity.class);
			startActivity(itnt);
			finish();
	}
	
	
	

}

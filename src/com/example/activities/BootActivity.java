package com.example.activities;

import java.io.IOException;

import com.example.login.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
		/*
		 * Handler handler = new Handler(); handler.postDelayed(new Runnable() {
		 * private int abcd = 0;
		 * 
		 * @Override public void run() { StartLoginActivity(); } },
		 * 1000);//等待一秒后进入LoginActivity
		 * 
		 */
		StartLoginActivity();

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("http://172.27.0.15:8080/membercenter/api/hello")
				.method("GET", null).build();

		client.newCall(request).enqueue(new Callback() {
			

			//请求成功,提示请的内容并跳转到LoginActivity
			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				BootActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Toast.makeText(getApplicationContext(), arg1.body().string(), Toast.LENGTH_LONG).show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});

				Log.d("haha", arg1.toString());

			}

			
			//请求失败后的处理
			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
					
							Toast.makeText(getApplicationContext(), arg1.toString(), Toast.LENGTH_LONG).show();
						
						
					}
				});
				Log.d("eee", arg1.getMessage());
			}
		});

	}

	public void StartLoginActivity() {
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
		//finish();
	}

}

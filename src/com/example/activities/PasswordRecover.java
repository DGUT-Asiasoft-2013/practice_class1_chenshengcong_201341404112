package com.example.activities;

import java.io.IOException;

import org.json.JSONObject;

import com.example.fragments.inputcells.FragmentPasswordRecoverStep1;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep2;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep2.OnSubmitClickedListener;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep1.OnGoNextLister;
import com.example.login.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.widget.Toast;
import model.HttpServer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PasswordRecover extends Activity {
	FragmentPasswordRecoverStep1 step1 = new FragmentPasswordRecoverStep1();
	FragmentPasswordRecoverStep2 step2 = new FragmentPasswordRecoverStep2();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_recover);
//为"下一步"按钮添加监听接口实现
		step1.setOnGoNextLister(new OnGoNextLister() {

			
		
			@Override
			public void onGoNext() {
				goStep2();//跳转到重置密码Fragment

			}
		});
		
		step2.setOnSubmitClickedListener(new OnSubmitClickedListener() {
			
			@Override
			public void onSubmitClicked() {
				// TODO Auto-generated method stub
				goSubmit();
				
			}
		} );

		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();//事务必须commit才能生效
		
		
		
	}

	void goStep2() {
		getFragmentManager()
				.beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,
						R.animator.slide_in_right, R.animator.slide_out_right)//设置动画效果
				.replace(R.id.container, step2).addToBackStack(null).commit();
	
			
		
	}
	void goSubmit(){
		OkHttpClient client = HttpServer.getSharedClient();
		MultipartBody body = new MultipartBody.Builder()
				.addFormDataPart("email", step1.getEmail())
				.addFormDataPart("passwordHash",step2.getNewPassword())
				.build();
		Request request = HttpServer.requestBuilderWithApi("passwordrecover").post(body).build();
				
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub

				final Boolean b ;
				ObjectMapper oMapper = new ObjectMapper();
				b=oMapper.readValue(arg1.body().string(), Boolean.class);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(b==true)
						{
							ResponeSucceed();
						}
						else{
							ResponeFailure();
						}
					}
				});
			
				
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
					
						ResponeFailure();
					}
				});
		

			}
		});
	}
	
	public void ResponeSucceed(){

		Toast.makeText(this, "重置密码成功", Toast.LENGTH_SHORT).show();
		Log.d("mimac", "修改密码成功啦");
		finish();
	}
	public void ResponeFailure(){
		Toast.makeText(this, "重置密码失败", Toast.LENGTH_SHORT).show();
		

	}
	
	//函数功能:使用Intent跳转到LoginActivity
	void goLogin() {
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
		
	}
}

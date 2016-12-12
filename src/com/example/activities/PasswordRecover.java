package com.example.activities;

import java.io.IOException;

import org.json.JSONObject;

import com.example.fragments.inputcells.FragmentPasswordRecoverStep1;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep2;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep2.OnGoSubmitLister;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep1.OnGoNextLister;
import com.example.login.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.style.IconMarginSpan;
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
		
		

		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();//事务必须commit才能生效
		
		
		
	}

	void goStep2() {
		getFragmentManager()
				.beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,
						R.animator.slide_in_right, R.animator.slide_out_right)//设置动画效果
				.replace(R.id.container, step2).addToBackStack(null).commit();
	
			
		
	}
	
	
	
}

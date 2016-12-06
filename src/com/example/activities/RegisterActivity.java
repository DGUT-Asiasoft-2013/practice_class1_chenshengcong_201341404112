package com.example.activities;

import com.example.fragments.SimpleTextInputCellFragment;
import com.example.login.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterActivity extends Activity {
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordRepeat;
	SimpleTextInputCellFragment fragInputEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Button btn_submit = (Button) findViewById(R.id.btn_submit);
		
		//将绑定各个界面的?
		fragInputCellAccount = (SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_accout);
		fragInputCellPassword = (SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password_repeat);
		fragInputEmail=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_email);
		//为"提交"按钮添加监听接口
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goLogin();//跳转到登陆界面
				
			}
		});
	}
	void goLogin() {
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
	}
	@Override
	protected void onResume() {
		super.onResume();

		fragInputCellAccount.setLabelText("帐户名");
		{
			fragInputCellAccount.setHintText("请输入账户名");
		}
		fragInputCellPassword.setLabelText("密码");
		{
			fragInputCellPassword.setHintText("请输入密码");
			fragInputCellPassword.setIsPassword(true);
		}
		fragInputCellPasswordRepeat.setLabelText("重复密码");
		{
			fragInputCellPasswordRepeat.setHintText("请重复输入密码");
			fragInputCellPasswordRepeat.setIsPassword(true);
		}
		fragInputEmail.setLabelText("电子邮箱");
		{
			fragInputEmail.setHintText("请输入电子邮箱");
		}

	}
}

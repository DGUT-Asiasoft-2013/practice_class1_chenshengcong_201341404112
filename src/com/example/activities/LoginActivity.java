package com.example.activities;

import com.example.fragments.inputcells.SimpleTextInputCellFragment;
import com.example.login.R;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity {

	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button btn_login = (Button) findViewById(R.id.btn_login);
		Button btn_register = (Button) findViewById(R.id.btn_register);
		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_username);
		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager()
				.findFragmentById(R.id.input_password);
		// 为"LOGIN"按钮添加监听接口,跳转到主界面(Hello World)
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goWelcome();

			}
		});

		// 为"REGISTER"按钮调加监听接口,跳转到注册界面(Register)
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goRegister();

			}
		});

		// 绑定"忘记密码?"TextView,并为它设置可点击,添加监听接口
		findViewById(R.id.btn_forget_password).setClickable(true);
		findViewById(R.id.btn_forget_password).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goRecoverPassword();

			}
		});

	}

	void goWelcome() {
		Intent itnt = new Intent(this, HelloWorldActivity.class);
		startActivity(itnt);
	}

	void goRegister() {
		Intent itnt = new Intent(this, RegisterActivity.class);
		startActivity(itnt);
	}

	void goRecoverPassword() {
		Intent itnt = new Intent(this, PasswordRecover.class);
		startActivity(itnt);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fragInputCellAccount.setLabelText("用户名");
		{
			fragInputCellAccount.setHintText("请输入用户名");
		}
		fragInputCellPassword.setLabelText("密码");
		{
			fragInputCellPassword.setHintText("请输入密码");
			fragInputCellPassword.setIsPassword(true);
		}
	}

}

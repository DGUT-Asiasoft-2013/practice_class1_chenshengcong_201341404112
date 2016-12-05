package com.example.activities;

import com.example.fragments.SimpleTextInputCellFragment;
import com.example.login.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	SimpleTextInputCellFragment fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager()
			.findFragmentById(R.id.input_username);
	SimpleTextInputCellFragment fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager()
			.findFragmentById(R.id.input_password);
	Button btn_login = (Button) findViewById(R.id.btn_login);
	Button btn_register = (Button) findViewById(R.id.btn_register);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		// 为LOGIN 按钮添加监听接口,按下跳转到欢迎界面(Hello World)
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// setContentView(R.layout.activity_welcome);

				Toast toast = Toast.makeText(LoginActivity.this, "hello", Toast.LENGTH_SHORT);
				toast.show();

			}
		});

		// 为REGISTER按钮添加监听接口,跳转到注册页面(Register)
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_register);

			}
		});

	}

	@Override
	protected void onResume() {
		fragInputCellAccount.setLabelText("帐户名");
		fragInputCellAccount.setHintText("请输入账户名");
		fragInputCellPassword.setLabelText("密码");
		fragInputCellPassword.setHintText("请输入密码");
		fragInputCellPassword.setIsPassword(true);
	}

}

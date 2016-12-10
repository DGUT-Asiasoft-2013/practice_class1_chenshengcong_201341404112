package com.example.activities;

import java.io.IOException;

import com.example.fragments.inputcells.SimpleTextInputCellFragment;
import com.example.login.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import model.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

				// goWelcome();
				onLogin();

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

	void onLogin() {
		String inputAccount = fragInputCellAccount.getText();
		String inputPassword = fragInputCellPassword.getText();
		final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
		progressDialog.setMessage("登陆中!!");// 设置对话的信息
		progressDialog.setCancelable(false);// 设置为不可以取消
		progressDialog.setCanceledOnTouchOutside(false);// 设置为不能因为点击外部而取消
		// ---------------------------------------

		// 新建请求内容
		OkHttpClient client = new OkHttpClient();

		MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("account", inputAccount).addFormDataPart("passwordHash", inputPassword);
		
		
		Request requst = new Request.Builder().url("http://172.27.0.15:8080/membercenter/api/login")
				.method("post", null)
				.post(requestBody.build()).build();
		
		client.newCall(requst).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				LoginActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						
						
						
						try {
							LoginActivity.this.onResponse(arg0, arg1.body().string());
						} catch (IOException e) {
						
							e.printStackTrace();
							LoginActivity.this.onFailure(arg0, e);
						}
					}
				});
			}
			
			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				LoginActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						onFailure(arg0, arg1);
						
					}
				});
			}
		});
	}	
		
		

	// 成功响应,显示注册成功对话窗口,并退出注册界面
	void onResponse(Call arg0, String response) {
	//	Toast.makeText(this, "登录成功"+response, Toast.LENGTH_SHORT).show();;
	/*	new AlertDialog.Builder(this).setTitle("登录成功").setMessage(response)
		.setPositiveButton("好", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				
			}
		}).show();*/
		ObjectMapper oMapper = new ObjectMapper();
		User user = null;
		try {
			user = oMapper.readValue(response, User.class);
		/*	String hello = "Hello" +user.getAccount();
			Log.d("hehe", hello);*/
			if(user.equals(null))
			{
				new AlertDialog.Builder(this).setTitle("登录失败").setMessage("用户名或密码错误").setNegativeButton("重试", null)
				.show();
				Log.d("shibai", "失败了");
				return ;
				
			}
			else{
				goWelcome();
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			new AlertDialog.Builder(this).setTitle("登录失败").setMessage("用户名或密码错误").setNegativeButton("重试", null)
			.show();
			Log.d("shibai", "JsonParse失败了");
			return ;
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			new AlertDialog.Builder(this).setTitle("登录失败").setMessage("用户名或密码错误").setNegativeButton("重试", null)
			.show();
			Log.d("shibai", "Mapping失败了");
			return ;
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			new AlertDialog.Builder(this).setTitle("登录失败").setMessage("用户名或密码错误").setNegativeButton("重试", null)
			.show();
			Log.d("shibai", "IO失败了");
			return ;
		}
	
	}

	// 请求失败,显示注册失败窗口,
	void onFailure(Call arg0, Exception e) {
		new AlertDialog.Builder(this).setTitle("登录失败").setMessage(e.getLocalizedMessage()).setNegativeButton("重试", null)
				.show();
		

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

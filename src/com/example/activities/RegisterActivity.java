package com.example.activities;

import java.io.IOException;

import com.example.fragments.inputcells.PictureInputCellFragment;
import com.example.fragments.inputcells.SimpleTextInputCellFragment;
import com.example.login.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class RegisterActivity extends Activity {
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordRepeat;
	SimpleTextInputCellFragment fragInputEmail;
	SimpleTextInputCellFragment fragInputUsername;
	PictureInputCellFragment fragPicture;
	Button btn_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btn_submit = (Button) findViewById(R.id.btn_submit);

		// 绑定各个控件
		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_accout);
		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager()
				.findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat = (SimpleTextInputCellFragment) getFragmentManager()
				.findFragmentById(R.id.input_password_repeat);
		fragInputEmail = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_email);
		fragInputUsername = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_username);
		fragPicture = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.picture_choose);

		// 为"提交"按钮添加监听接口实现
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * goLogin();//跳转到登陆界面
				 */
				onRegister();
			}
		});
	}

	/*
	 * void goLogin() { Intent itnt = new Intent(this, LoginActivity.class);
	 * startActivity(itnt); }
	 */
	// 在onResume()中为输入框设定内容
	@Override
	protected void onResume() {
		super.onResume();

		fragInputCellAccount.setLabelText("用户名");
		{
			fragInputCellAccount.setHintText("请输入用户名");
		}
		fragInputCellPassword.setLabelText("密码");
		{
			fragInputCellPassword.setHintText("请输入6-20位密码");
			fragInputCellPassword.setIsPassword(true);
		}
		fragInputCellPasswordRepeat.setLabelText("确认密码");
		{
			fragInputCellPasswordRepeat.setHintText("请再次输入密码");
			fragInputCellPasswordRepeat.setIsPassword(true);
		}
		fragInputUsername.setLabelText("昵称");
		{
			fragInputUsername.setHintText("请输入昵称");
		}
		fragInputEmail.setLabelText("邮箱地址");
		{
			fragInputEmail.setHintText("请输入验证邮箱地址");
		}

	}

	public void onRegister() {
		String password =  fragInputCellPassword.getText();
		String passwordRepeat =  fragInputCellPasswordRepeat.getText();
		// 判断两次输入的密码是不是一致,如果不一致,则弹出对话框提示并返回
		if (!password.equals(passwordRepeat) || password.length()==0) {
			new AlertDialog.Builder(RegisterActivity.this).setMessage("输入密码不一致!!请确认密码").setIcon(android.R.id.icon1)
					.setNegativeButton("OK", null).show();
			return;
		}

		String email = fragInputEmail.getText();
		String username = fragInputUsername.getText();
		String account = fragInputCellAccount.getText();
		// 新建一个progressdialog,在用户点击提交按钮后显示等待
		final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
		progressDialog.setMessage("请等待!!");// 设置对话的信息
		progressDialog.setCancelable(false);// 设置为不可以取消
		progressDialog.setCanceledOnTouchOutside(false);// 设置为不能因为点击外部而取消
//---------------------------------------
		//创建存储照片
		byte[] pngData =fragPicture.getPng();
		if(pngData!=null)
		{
			RequestBody imgBody =RequestBody.create(MediaType.parse("image.png"), pngData);
			
		}
		
		
		// 新建请求内容
		OkHttpClient client = new OkHttpClient();
		
		MultipartBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("account", account)
				.addFormDataPart("avatar","avatar.png",imgBody )
				.addFormDataPart("name", username).addFormDataPart("email", email)
				.addFormDataPart("passwordHash", password).build();
		Request requst = new Request.Builder().url("http://172.27.0.15:8080/membercenter/api/register")
				.method("POST", requestBody)
				.post(requestBody).build();

		// 建立请求
		client.newCall(requst).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				RegisterActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();
						try {
							RegisterActivity.this.onResponse(arg0, arg1.body().string());//
						} catch (Exception e) {

							e.printStackTrace();
							RegisterActivity.this.onFailure(arg0, e);
						}
					}
				});

			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {

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
		new AlertDialog.Builder(this).setTitle("注册成功").setMessage(response)
				.setPositiveButton("好", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).show();

	}

	// 请求失败,显示注册失败窗口,
	void onFailure(Call arg0, Exception e) {
		new AlertDialog.Builder(this).setTitle("请求失败").setMessage(e.getLocalizedMessage()).setNegativeButton("重试", null)
				.show();
	}
}

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
		
		//���󶨸��������?
		fragInputCellAccount = (SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_accout);
		fragInputCellPassword = (SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password_repeat);
		fragInputEmail=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_email);
		//Ϊ"�ύ"��ť��Ӽ����ӿ�
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goLogin();//��ת����½����
				
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

		fragInputCellAccount.setLabelText("�ʻ���");
		{
			fragInputCellAccount.setHintText("�������˻���");
		}
		fragInputCellPassword.setLabelText("����");
		{
			fragInputCellPassword.setHintText("����������");
			fragInputCellPassword.setIsPassword(true);
		}
		fragInputCellPasswordRepeat.setLabelText("�ظ�����");
		{
			fragInputCellPasswordRepeat.setHintText("���ظ���������");
			fragInputCellPasswordRepeat.setIsPassword(true);
		}
		fragInputEmail.setLabelText("��������");
		{
			fragInputEmail.setHintText("�������������");
		}

	}
}

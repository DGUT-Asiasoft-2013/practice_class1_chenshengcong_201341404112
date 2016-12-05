package com.example.activities;

import com.example.fragments.SimpleTextInputCellFragment;
import com.example.login.R;

import android.app.Activity;
import android.os.Bundle;

public class RegisterActivity extends Activity {
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordRepeat;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		
		fragInputCellAccount = (SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_accout);
		fragInputCellPassword = (SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password_repeat);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stu
		super.onResume();
		
		fragInputCellAccount.setLabelText("�ʻ���");
		fragInputCellAccount.setHintText("�������˻���");
		fragInputCellPassword.setLabelText("����");
		fragInputCellPassword.setHintText("����������");
		fragInputCellPassword.setIsPassword(true);
		fragInputCellPasswordRepeat.setLabelText("�ظ�����");
		fragInputCellPasswordRepeat.setHintText("���ظ���������");
		fragInputCellPasswordRepeat.setIsPassword(true);
	}
}

package com.example.fragments.inputcells;

import com.example.activities.LoginActivity;
import com.example.login.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentPasswordRecoverStep2 extends Fragment {
 SimpleTextInputCellFragment frag_CheckCode;
 SimpleTextInputCellFragment frag_NewPassword;
 SimpleTextInputCellFragment frag_NewPasswordRepeat;
 View view;
 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	 			if(view==null)
	 			{
	 				view =inflater.inflate(R.layout.fragment_password_recover_step2, null);
	 				frag_CheckCode=(SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_checkcode);
	 				frag_NewPassword=(SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_new_password);
	 				frag_NewPasswordRepeat=(SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_new_password_repeat);
	 				
	 			}
	 //为重置按钮添加监听接口
	 			view.findViewById(R.id.btn_submit_new_password).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
							goLogin();
						
					}
				});
	 
	 return view;
	}

@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		frag_CheckCode.setLabelText("验证码");
		{
			frag_CheckCode.setHintText("请输入收到的验证码");
		}
		frag_NewPassword.setLabelText("新密码");
		{
			frag_NewPassword.setHintText("请输入新密码");
			frag_NewPassword.setIsPassword(true);
		}
		frag_NewPasswordRepeat.setLabelText("确认新密码");
		{
			frag_NewPasswordRepeat.setHintText("请再次输入新密码");
			frag_NewPasswordRepeat.setIsPassword(true);
		}
	}
//函数功能:使用Intent跳转到LoginActivity
void goLogin() {
	Intent itnt = new Intent(getActivity(), LoginActivity.class);
	startActivity(itnt);
	
}
}

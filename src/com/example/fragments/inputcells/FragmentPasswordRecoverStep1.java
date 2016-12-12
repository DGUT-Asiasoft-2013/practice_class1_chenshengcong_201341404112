package com.example.fragments.inputcells;

import com.example.login.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentPasswordRecoverStep1 extends Fragment {
 SimpleTextInputCellFragment frag_Email;
	View view;
	static String email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_password_recover_step1, null);
			frag_Email = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_email);
			 email = frag_Email.getText();
			// getEmail(email);
			view.findViewById(R.id.btn_goNext).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					goNext();
				}
			});
		}

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		frag_Email.setLabelText("邮箱");
		{
			frag_Email.setHintText("请输入注册时的认证邮箱");
		}

	}

	/*
	 * 以下的东西不太懂 1创建一个static接口 2创建一个此接口的类 3创建一个方法,使用该接口变量作为参数
	 */
	//
	public static interface OnGoNextLister {
		void onGoNext();
	}

	OnGoNextLister onGoNextLister;//

	public void setOnGoNextLister(OnGoNextLister onGoNextListener) {
		this.onGoNextLister = onGoNextListener;

	}

	void goNext() {
		if (onGoNextLister != null) {
			onGoNextLister.onGoNext();
		}
	}
	
	
	public static interface OnGetEmailListener{
		String  onGetEmail(String email);
	}
	OnGetEmailListener onGetEmailListener;
	
	public void setOnGetEmailListener(OnGetEmailListener onGetEmailListener)
	{
		this.onGetEmailListener = onGetEmailListener;
	}
	public static String getEmail(){
		return email;
	}

	
	

	
}

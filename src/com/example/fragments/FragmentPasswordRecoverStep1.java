package com.example.fragments;

import com.example.login.R;

import android.app.Fragment;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_password_recover_step1, null);
			frag_Email = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_email);
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
		frag_Email.setLabelText("注册邮箱");
		{
			frag_Email.setHintText("请输入注册时的邮箱");
		}

	}

	
	
	/*
	 * 以下的东西不懂
	 *1创建一个接口
	 *2创建一个此接口的变量
	 *3变量
	 */
	// 创建"下一步"按钮的接口
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
}

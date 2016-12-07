package com.example.activities;

import com.example.fragments.inputcells.FragmentPasswordRecoverStep1;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep2;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep1.OnGoNextLister;
import com.example.login.R;

import android.app.Activity;
import android.os.Bundle;

public class PasswordRecover extends Activity {
	FragmentPasswordRecoverStep1 step1 = new FragmentPasswordRecoverStep1();
	FragmentPasswordRecoverStep2 step2 = new FragmentPasswordRecoverStep2();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_recover);
//为"下一步"按钮添加监听接口实现
		step1.setOnGoNextLister(new OnGoNextLister() {

			@Override
			public void onGoNext() {
				goStep2();//跳转到重置密码Fragment

			}
		});

		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();//事务必须commit才能生效
	}

	void goStep2() {
		getFragmentManager()
				.beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,
						R.animator.slide_in_right, R.animator.slide_out_right)//设置动画效果
				.replace(R.id.container, step2).addToBackStack(null).commit();
	}
}

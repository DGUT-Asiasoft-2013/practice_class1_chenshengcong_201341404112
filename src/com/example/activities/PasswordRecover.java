package com.example.activities;

import com.example.fragments.FragmentPasswordRecoverStep1;
import com.example.fragments.FragmentPasswordRecoverStep1.OnGoNextLister;
import com.example.fragments.FragmentPasswordRecoverStep2;
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
//Ϊ"��һ��"��ť���Ӽ����ӿ�
		step1.setOnGoNextLister(new OnGoNextLister() {

			@Override
			public void onGoNext() {
				goStep2();//��ת�������������

			}
		});

		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();//���뽫�����ύ�Ż���ת�������������
	}

	void goStep2() {
		getFragmentManager()
				.beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,
						R.animator.slide_in_right, R.animator.slide_out_right)//���ö���Ч��
				.replace(R.id.container, step2).addToBackStack(null).commit();
	}
}

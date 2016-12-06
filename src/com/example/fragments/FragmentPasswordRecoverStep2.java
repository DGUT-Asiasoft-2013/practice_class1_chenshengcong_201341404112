package com.example.fragments;

import com.example.activities.LoginActivity;
import com.example.activities.PasswordRecover;
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
 View view;
 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	 			if(view==null)
	 			{
	 				view =inflater.inflate(R.layout.fragment_password_recover_step2, null);
	 				frag_CheckCode=(SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_checkcode);
	 			}
	 
	 
	 return view;
	}


}

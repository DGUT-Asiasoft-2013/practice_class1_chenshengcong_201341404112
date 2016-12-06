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
		}

		return view;
	}
	
	void goNext(){
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		frag_Email.setLabelText("◊¢≤·” œ‰");
		{
			frag_Email.setHintText("«Î ‰»Î◊¢≤· ±µƒ” œ‰");
		}
		
	}

}

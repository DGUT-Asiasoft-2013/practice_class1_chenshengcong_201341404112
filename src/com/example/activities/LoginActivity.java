package com.example.activities;

import com.example.login.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
	
		Button btn_login = (Button)findViewById(R.id.btn_login);
		
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Toast toast=Toast.makeText(LoginActivity.this, "hello", Toast.LENGTH_SHORT);
			toast.show();
				
			}
		});
	}


}

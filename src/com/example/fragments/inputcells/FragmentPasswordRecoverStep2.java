package com.example.fragments.inputcells;

import java.io.IOException;

import com.example.activities.*;
import com.example.activities.pages.MyProfileFragment;
import com.example.fragments.inputcells.FragmentPasswordRecoverStep1.*;
import com.example.login.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import model.HttpServer;
import model.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;


public class FragmentPasswordRecoverStep2 extends Fragment {
 SimpleTextInputCellFragment frag_CheckCode;
 SimpleTextInputCellFragment frag_NewPassword;
 SimpleTextInputCellFragment frag_NewPasswordRepeat;
 static String password;
 View view;

 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	 			if(view==null)
	 			{
	 				view =inflater.inflate(R.layout.fragment_password_recover_step2, null);
	 				frag_CheckCode=(SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_checkcode);
	 				frag_NewPassword=(SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_new_password);
	 				frag_NewPasswordRepeat=(SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_new_password_repeat);
	 			
	 				password = frag_NewPassword.getText();
	 			
	 			}
	 //为重置按钮添加监听接口
	 			view.findViewById(R.id.btn_submit_new_password).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
							//goLogin();
							//goPasswordRecover();
						goSubmit();
						
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

public String getNewPassword(){
	return password;
}
	


void goSubmit() {
	if (onGoSubmitLister != null) {
		onGoSubmitLister.onGoSubmit();
	}
}
public static interface OnGoSubmitLister {
	void onGoSubmit();
}

OnGoSubmitLister onGoSubmitLister;//

public void setOnGoSubmitLister(OnGoSubmitLister onGoSubmitListener) {
	this.onGoSubmitLister = onGoSubmitListener;

}


public void goPasswordRecover(){
	//email.setOnDataTransmission(new OnDataTransmisson());
	
	OkHttpClient client = HttpServer.getSharedClient();

	MultipartBody.Builder requestBody = new MultipartBody.Builder()
			.addFormDataPart("email",step1.getEmail()).addFormDataPart("passwordHash",step2.getNewPassword() );
	

	Request request = HttpServer.requestBuilderWithApi("passwordrecover")
			.method("POST", null)
			.post(requestBody.build())
			.build();
	
	client.newCall(request).enqueue(new Callback() {
		
		@Override
		public void onResponse( Call arg0, Response arg1) throws IOException {
		Boolean b ;
		ObjectMapper oMapper = new ObjectMapper();
		b=oMapper.readValue(arg1.body().string(), Boolean.class);
		if(b==true)
		{
			ResponeSucceed();
		}
		else{
			ResponeFailure();
		}
		}	
		
		@Override
		public void onFailure(Call arg0, IOException arg1) {
			
			ResponeFailure();
		}
	});
	
}
public void ResponeSucceed(){
	new AlertDialog.Builder(PasswordRecover.this).setTitle("重设密码成功").setMessage("重置密码成功").setPositiveButton("确定", null).show();
	//goLogin();
}
public void ResponeFailure(){
	new AlertDialog.Builder(PasswordRecover.this).setTitle("重设密码失败").setMessage("重置密码失败").setNegativeButton("确定", null).show();
}


}

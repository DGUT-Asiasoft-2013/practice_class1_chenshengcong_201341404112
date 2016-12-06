package com.example.fragments;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.login.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureInputCellFragment extends BaseInputFragment {
	ImageView imageView;
	TextView labelText;
	TextView HintText;

	final int RESULTCODE_CAMERA = 1;
	final int RESULTCODE_ALBUM = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_input_picture, container);
		imageView = (ImageView) view.findViewById(R.id.imageView_choose);
		labelText = (TextView) view.findViewById(R.id.label);

		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onImageViewClicked();
			}

		});
		return view;
	}

	public void onImageViewClicked() {
		String[] items = { "≈ƒ’’", "œ‡≤·" };

		new AlertDialog.Builder(getActivity()).setTitle(labelText.getText())
				.setItems(items, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							takePhoto();
							break;
						case 1:
							pickFormAlbum();
							break;
						default:
							break;
						}

					}
				}).setNegativeButton("»°œ˚", null).show();
	}

	void takePhoto() {
		Intent itnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(itnt, RESULTCODE_CAMERA);
	}

	void pickFormAlbum() {
		Intent itnt = new Intent(Intent.ACTION_GET_CONTENT);
		itnt.setType("image/*");
		startActivityForResult(itnt, RESULTCODE_ALBUM);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_CANCELED)
		{
			return;
		}
		if(requestCode==RESULTCODE_CAMERA)
		{
		Bitmap bmp=(Bitmap)data.getExtras().get("data");
		imageView.setImageBitmap(bmp);
		}
		else if(requestCode==RESULTCODE_ALBUM){
			try {
				Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
				imageView.setImageBitmap(bmp);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void setLabelText(String labelText1) {
		labelText.setText(labelText1);

	}

	@Override
	public void setHintText(String hintText) {
		// TODO Auto-generated method stub
		labelText.setHint(hintText);
	}

}

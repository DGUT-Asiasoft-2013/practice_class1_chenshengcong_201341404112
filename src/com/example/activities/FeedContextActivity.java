package com.example.activities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.example.activities.pages.FeedListFragment;
import com.example.login.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import model.Article;
import model.Comments;
import model.HttpServer;
import model.Pages;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedContextActivity extends Activity {

	View btnLoadMore;
	View addComment;
	TextView textLoadMore;
	List<Comments> commentData;
	Article article;
	Button btnComment;
	EditText editComment;
	static String CommentText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedcontext);
		ListView listComment = (ListView)findViewById(R.id.list_comments);
		//LayoutInflater inflater = null;
		//btnLoadMore = inflater.inflate(R.layout.widget_load_more, null);
		//textLoadMore = (TextView) btnLoadMore.findViewById(R.id.text);
		TextView textFeedMessage = (TextView) findViewById(R.id.text_feed_list_message);
		TextView textFeedAuthorName = (TextView) findViewById(R.id.text_feed_author_name);
		TextView textFeedEditDate = (TextView) findViewById(R.id.text_feed_edit_time);
		btnComment = (Button) findViewById(R.id.btn_comment);
		editComment = (EditText) findViewById(R.id.edit_comment);

		textFeedAuthorName.setText(getIntent().getStringExtra("authorName"));
		textFeedEditDate.setText(getIntent().getStringExtra("editDate"));
		article = (Article) getIntent().getSerializableExtra("article");
		textFeedMessage.setText(article.getText());

		

		//listComment.addFooterView(btnLoadMore);
		listComment.setAdapter(listCommentAdapter);


		btnComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addComment();
			}
		});

	}

	BaseAdapter listCommentAdapter = new BaseAdapter() {
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.fragment_single_list, null);
			} 
			TextView textMessage = (TextView) view.findViewById(R.id.text_list_message);
			TextView textAuthorName = (TextView) view.findViewById(R.id.text_author_name);
			TextView textEditTime = (TextView) view.findViewById(R.id.text_edit_time);
			
			Comments comment = commentData.get(position);
			textMessage.setText(comment.getAuthour().getName() + " : " + comment.getText());
			textAuthorName.setText(comment.getAuthour().getName());
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			textEditTime.setText(dateFormater.format(comment.getEditDate()));
			return view;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public Object getItem(int position) {

			return commentData.get(position);
		}

		@Override
		public int getCount() {
			if (commentData == null)
				return 0;
			else
				return commentData.size();
		}
	};

	void reloadComment() {

		OkHttpClient client = HttpServer.getSharedClient();

		Request request = HttpServer.requestBuilderWithApi("article/" + article.getId() + "/comments").get().build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				Log.d("comments", arg1.body().string());
				final Pages<Comments> commentData = new ObjectMapper().readValue(arg1.body().string(),
						new TypeReference<Pages<Comments>>() {
				});
				FeedContextActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						FeedContextActivity.this.commentData=commentData.getContent();
						listCommentAdapter.notifyDataSetInvalidated();
					}
				});
			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				FeedContextActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						new AlertDialog.Builder(FeedContextActivity.this).setMessage(e.getMessage()).show();
					}
				});
			}
		});


	}

	void addComment() {
		CommentText = editComment.getText().toString();
		Log.d("on", "我按下评论啦");
		OkHttpClient client = HttpServer.getSharedClient();
		MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("text", CommentText).build();
		Request request = HttpServer.requestBuilderWithApi("article/" + article.getId() + "/comments")
				.method("POST", requestBody).build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				Log.d("fanh", arg1.body().string());
				/*
				 * final Pages<Comment> commentData = new
				 * ObjectMapper().readValue(arg1.body().string(), new
				 * TypeReference<Pages<Comment>>() { });
				 * FeedContextActivity.this.runOnUiThread(new Runnable() {
				 * 
				 * @Override public void run() { String
				 * haha=commentData.toString(); Log.d("fanS", haha); } });
				 */
			}

			@Override
			public void onFailure(Call arg0, IOException e) {
				Log.d("fanF", e.getMessage());
				new AlertDialog.Builder(getBaseContext()).setMessage(e.getMessage()).show();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reloadComment();
	}
}

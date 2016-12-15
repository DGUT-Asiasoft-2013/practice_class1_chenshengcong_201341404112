package com.example.activities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.example.activities.pages.FeedListFragment;
import com.example.fragments.widgets.AvatarView;
import com.example.login.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
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
	int page = 0;
	Button btnLikes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedcontext);
		ListView listComment = (ListView) findViewById(R.id.list_comments);
	//	AvatarView avatar = (AvatarView)findViewById(R.id.avatar);
		// LayoutInflater inflater = null;
		// btnLoadMore = inflater.inflate(R.layout.widget_load_more, null);
		// textLoadMore = (TextView) btnLoadMore.findViewById(R.id.text);
		TextView textFeedMessage = (TextView) findViewById(R.id.text_feed_list_message);
		TextView textFeedAuthorName = (TextView) findViewById(R.id.text_feed_author_name);
		TextView textFeedEditDate = (TextView) findViewById(R.id.text_feed_edit_time);
		btnComment = (Button) findViewById(R.id.btn_comment);
		editComment = (EditText) findViewById(R.id.edit_comment);

		btnLikes = (Button) findViewById(R.id.btn_likes);
		textFeedAuthorName.setText(getIntent().getStringExtra("authorName"));
		textFeedEditDate.setText(getIntent().getStringExtra("editDate"));
		article = (Article) getIntent().getSerializableExtra("article");
		textFeedMessage.setText(article.getText());
		//avatar.load(article.getAuthorAvatar());
		// listComment.addFooterView(btnLoadMore);
		listComment.setAdapter(listCommentAdapter);
//为评论按钮添加监听接口
		btnComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addComment();
			}
		});
		
		//为点赞按钮添加接口
		btnLikes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleLikes();
			}
		});

	}

	// 评论列表接口
	BaseAdapter listCommentAdapter = new BaseAdapter() {
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {

				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				convertView = inflater.inflate(R.layout.fragment_single_list, null);
			}
			Comments comment = commentData.get(position);
			TextView textMessage = (TextView) convertView.findViewById(R.id.text_list_message);
			TextView textAuthorName = (TextView) convertView.findViewById(R.id.text_author_name);
			TextView textEditTime = (TextView) convertView.findViewById(R.id.text_edit_time);
			AvatarView avatar = (AvatarView)convertView.findViewById(R.id.avatar);
			textMessage.setText(comment.getAuthor().getName() + " : " + comment.getText());
			textAuthorName.setText(comment.getAuthor().getName());
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			textEditTime.setText(dateFormater.format(comment.getEditDate()));
			avatar.load(HttpServer.serverAddress+article.getAuthorAvatar());
			return convertView;
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
	
	void reloadLikes(){
		Request request = HttpServer.requestBuilderWithApi("/article/"+article.getId()+"/likes")
				.get().build();
		
		HttpServer.getSharedClient().newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{
					String responseString = arg1.body().string();
					final Integer count = new ObjectMapper().readValue(responseString, Integer.class);
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onReloadLikesResult(count);
						}
					});
				}catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onReloadLikesResult(0);
						}
					});
				}
			}
			
			@Override
			public void onFailure(Call arg0, IOException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onReloadLikesResult(0);
					}
				});
			}
		});
	}
	void onReloadLikesResult(int count){
		if(count>0){
			btnLikes.setText("赞("+count+")");
		}else{
			btnLikes.setText("赞");
		}
	}
	// 重新加载评论的方法
	void reloadComment() {
		reloadLikes();
		checkLiked();
		OkHttpClient client = HttpServer.getSharedClient();

		Request request = HttpServer.requestBuilderWithApi("article/" + article.getId() + "/comments").get().build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				String responeseComment = arg1.body().string();

				Log.d("comments", responeseComment);
				try {
					final Pages<Comments> commentData = new ObjectMapper().readValue(responeseComment,
							new TypeReference<Pages<Comments>>() {
							});
					FeedContextActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							FeedContextActivity.this.reloadData(commentData);
						}
					});
				} catch (final Exception e) {
					Log.d("fanOF", e.getMessage());
					// FeedContextActivity.this.onFailure(e);
				}
			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				FeedContextActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Log.d("fanFF", e.getMessage());
						// FeedContextActivity.this.onFailure(e);
					}
				});
			}
		});

	}

	 void checkLiked() {
		 Request request = HttpServer.requestBuilderWithApi("article/"+article.getId()+"/isliked").get().build();
			HttpServer.getSharedClient().newCall(request).enqueue(new Callback() {
				@Override
				public void onResponse(Call arg0, Response arg1) throws IOException {
					try{
						final String responseString = arg1.body().string();
						final Boolean result = new ObjectMapper().readValue(responseString, Boolean.class);
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								onCheckLikedResult(result);
							}
						});
					}catch(final Exception e){
						e.printStackTrace();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								onCheckLikedResult(false);
							}

						
						});
					}
				}
				
				@Override
				public void onFailure(Call arg0, IOException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onCheckLikedResult(false);
						}
					});				
				}
			});
		
	}
		private void onCheckLikedResult(boolean result) {
			isLiked = result;
			btnLikes.setTextColor(result ? Color.BLUE : Color.BLACK);
			
		}
	protected void onFailure(Exception e) {
		// new AlertDialog.Builder(this).setMessage(e.getMessage()).show();
		Log.d("listF", e.toString());
	}

	protected void reloadData(Pages<Comments> commentData2) {
		page = commentData2.getNumber();
		commentData = commentData2.getContent();
		listCommentAdapter.notifyDataSetInvalidated();
	}

	// 发送评论接口
	void addComment() {
		
		CommentText = editComment.getText().toString();
		OkHttpClient client = HttpServer.getSharedClient();
		MultipartBody requestBody = new MultipartBody.Builder().addFormDataPart("text", CommentText).build();
		Request request = HttpServer.requestBuilderWithApi("article/" + article.getId() + "/comments")
				.method("POST", requestBody).build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				Log.d("fanh", arg1.body().string());
				reloadComment();
				
			}

			@Override
			public void onFailure(Call arg0, IOException e) {
				Log.d("fanF", e.getMessage());
				new AlertDialog.Builder(getBaseContext()).setMessage(e.getMessage()).show();
			}
		});
		editComment.setText("");
	}
	
	private boolean isLiked;
	void toggleLikes(){
		MultipartBody requestBody=  new MultipartBody.Builder().addFormDataPart("likes", String.valueOf(!isLiked)).build();
		Request request = HttpServer.requestBuilderWithApi("article/"+article.getId()+"/likes")
				.post(requestBody).build();
		HttpServer.getSharedClient().newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						reloadComment();
					}
				});
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						reloadComment();
					}
				});
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

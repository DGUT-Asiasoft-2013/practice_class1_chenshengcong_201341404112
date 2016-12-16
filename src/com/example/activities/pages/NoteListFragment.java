package com.example.activities.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.activities.FeedContextActivity;
import com.example.fragments.widgets.AvatarView;
import com.example.login.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import model.Article;
import model.Comments;
import model.HttpServer;
import model.Pages;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NoteListFragment extends Fragment {
	View view = null;
	ListView feedsList;
	List<Comments> data;
	int page = 0;
	View btnLoadMore;
	TextView textLoadMore;
	AvatarView avatar;
	TextView btnMyArticle;
	TextView btnMyComment;
	String myThing;
	TextView textMyHead;
	Object obj;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_note_list, null);
			btnLoadMore = inflater.inflate(R.layout.widget_load_more, null);

			textLoadMore = (TextView) btnLoadMore.findViewById(R.id.text);
			btnMyArticle = (TextView) view.findViewById(R.id.btn_receive_comment);
			btnMyComment = (TextView) view.findViewById(R.id.btn_my_comment);
			textMyHead = (TextView) view.findViewById(R.id.text_mythings);
			ListView feedsList = (ListView) view.findViewById(R.id.list_mythings);// 绑定feeds页面中的ListView
			feedsList.addFooterView(btnLoadMore);
			feedsList.setAdapter(listAdapter);

			feedsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onItemClicked(position);

				}
			});

			btnLoadMore.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					loadMore();
				}
			});

			btnMyArticle.setClickable(true);
			btnMyComment.setClickable(true);
			btnMyArticle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					goReceiveComment();
				}
			});

			btnMyComment.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					goMyComment();
				}
			});
		}
		goReceiveComment();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		reload();
	}

	// 我的收到的评论按钮监听器
	void goReceiveComment() {
		myThing = "/myReceiveComment";
		textMyHead.setText("我收到的评论");
		btnMyArticle.setTextColor(Color.BLUE);
		btnMyComment.setTextColor(Color.BLACK);
		reload();
	}

	// 我的评论监听器
	void goMyComment() {
		myThing = "/myComment";
		textMyHead.setText("我的评论");
		btnMyArticle.setTextColor(Color.BLACK);
		btnMyComment.setTextColor(Color.BLUE);
		reload();
	}

	// listView的监听器
	BaseAdapter listAdapter = new BaseAdapter() {
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());// 当converView为空时,获取父容器的布局
				view = inflater.inflate(R.layout.fragment_single_list, null);// 将当前的view;
			} else {
				view = convertView;
			}
			TextView textMessage = (TextView) view.findViewById(R.id.text_list_message);//
			TextView textAuthorName = (TextView) view.findViewById(R.id.text_author_name);
			TextView textEditTime = (TextView) view.findViewById(R.id.text_edit_time);
			AvatarView avatar = (AvatarView) view.findViewById(R.id.avatar);
			Comments comment = data.get(position);
			textMessage.setText(comment.getAuthor().getName() + " : " + comment.getText());
			textAuthorName.setText(comment.getAuthor().getName());
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			textEditTime.setText(dateFormater.format(comment.getEditDate()));

			avatar.load(HttpServer.serverAddress + comment.getAuthor().getAvatar());

			return view;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public Object getItem(int position) {

			return data.get(position);
		}

		@Override
		public int getCount() {

			if (data == null)
				return 0;
			else
				return data.size();
		}
	};

	public void onItemClicked(int position) {

		Comments article = data.get(position);
		String authorName = data.get(position).getAuthor().getName();

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String editDate = dateFormater.format(data.get(position).getEditDate());// 得到文章editDate并格式化为年-月-日
																				// 时:分:秒形式

		Intent itnt = new Intent(getActivity(), FeedContextActivity.class);

		itnt.putExtra("editDate", editDate);
	//	itnt.putExtra("comment", comment);
		startActivity(itnt);
	}

	void reload() {

		OkHttpClient client = HttpServer.getSharedClient();

		Request request = HttpServer.requestBuilderWithApi("search" + myThing).get().build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final Pages<Comments> pageData = new ObjectMapper().readValue(arg1.body().string(),
						new TypeReference<Pages<Comments>>() {
						});

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						NoteListFragment.this.page = pageData.getNumber();
						NoteListFragment.this.data = pageData.getContent();
						listAdapter.notifyDataSetInvalidated();
					}
				});

			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						new AlertDialog.Builder(getActivity()).setMessage(e.getMessage()).show();
					}
				});
			}
		});

	}

	void loadMore() {
		btnLoadMore.setEnabled(false);
		textLoadMore.setText("载入中…");
		OkHttpClient client = HttpServer.getSharedClient();
		Request request = HttpServer.requestBuilderWithApi("search" + myThing + (page + 1)).get().build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						btnLoadMore.setEnabled(true);

					}
				});

				try {
					final Pages<Comments> feeds = new ObjectMapper().readValue(arg1.body().string(),
							new TypeReference<Pages<Comments>>() {
							});
					if (feeds.getNumber() > page) {

						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								textLoadMore.setText("加载更多");
								if (data == null) {
									data = feeds.getContent();
								} else {
									data.addAll(feeds.getContent());
								}
								page = feeds.getNumber();
								listAdapter.notifyDataSetChanged();
							}
						});
					} else {

						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								textLoadMore.setText("没有更多了");
								listAdapter.notifyDataSetChanged();
							}
						});
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						btnLoadMore.setEnabled(true);
						textLoadMore.setText("没有更多了");
					}
				});
			}
		});

	}
}

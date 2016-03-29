package com.android.tongxunlu.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.activity.SmsActivity;
import com.android.tongxunlu.adapter.MessageAdapter;
import com.android.tongxunlu.biz.SmsBiz;
import com.android.tongxunlu.entity.Conversation;


/**
 * �������ŻỰ�б����
 */
public class SmsFragment extends Fragment{
	private ListView listView;
	private List<Conversation> cs;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_sms, null);
		//控件初始化
		listView=(ListView)view.findViewById(R.id.lv_sms);
		//获得信息记录
		SmsBiz biz=new SmsBiz(getActivity());
		cs=biz.findAllConversations();
		Log.i("TAG","cs"+cs);
		//自定义Adapter
		//ArrayAdapter<Conversation> adapter=
				//new ArrayAdapter<Conversation>(getActivity(), android.R.layout.simple_list_item_1, cs);
		//再定义一个MessageAdapter
		MessageAdapter adapter=new MessageAdapter(getActivity(), cs, listView);
		listView.setAdapter(adapter);
		//添加监听
		setListener();
		return view;
	}

	/**
	 * 对ListView的Item添加监听事件，点击进入短消息聊天界面
	 */
	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Conversation c=cs.get(position);
				int threadId = c.getId();
				int photoId=c.getPhotoId();
				String name=c.getName();
				//点击ListView的某个item，跳转至短消息对话界面
				Intent i=new Intent(getActivity(), SmsActivity.class);
				i.putExtra("threadId", threadId);
				i.putExtra("photoId",photoId);
				i.putExtra("name", name);
				startActivity(i);
			}
		});
	}
	

}




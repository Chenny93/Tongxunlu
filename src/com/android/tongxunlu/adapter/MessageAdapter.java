package com.android.tongxunlu.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.entity.Conversation;
import com.android.tongxunlu.util.ImageLoader;


public class MessageAdapter extends BaseAdapter {
	private  Context context;
	private List<Conversation>list;
	private LayoutInflater flater;
	private ImageLoader set;
	private AbsListView ListView;
	private int id;
	public MessageAdapter(Context context, List<Conversation> list,AbsListView ListView) {
		super();
		this.context = context;
		this.list = list;
		this.ListView=ListView;
		this.flater=LayoutInflater.from(context);
		set=new ImageLoader(context, ListView);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {

		return position;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if(convertView==null){
			convertView=flater.inflate(R.layout.item_message,null);
			vh=new ViewHolder();
			vh.tvBody=(TextView) convertView.findViewById(R.id.tv_message_body);
			vh.tvName=(TextView) convertView.findViewById(R.id.tv_message_name);
			vh.ivPhoto=(ImageView) convertView.findViewById(R.id.iv_Photo);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		Conversation con=list.get(position);
		String address=con.getNumber();
		Log.i("TMd","address"+address);
		vh.tvBody.setText(con.getBody());
		vh.tvName.setText(con.getName());
		int photoId=con.getPhotoId();
//		set.displayImage(photoId, vh.ivPhoto, position);
		return convertView;
	}

	class ViewHolder{
		ImageView ivPhoto;
		TextView tvName;
		TextView tvBody;
	}
}

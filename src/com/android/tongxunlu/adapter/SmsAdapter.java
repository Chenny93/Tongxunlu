package com.android.tongxunlu.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.entity.Sms;
import com.android.tongxunlu.util.ImageLoader;

public class SmsAdapter extends BaseAdapter{
	private Context context;
	private List<Sms> smss;
	private LayoutInflater inflater;
	private ImageLoader imageloader;
	private int photoId;
	private AbsListView view;
	
	public SmsAdapter(Context context, List<Sms> smss,int photoId,AbsListView listview) {
		this.context=context;
		this.smss=smss;
		this.inflater=LayoutInflater.from(context);
		this.photoId=photoId;
		imageloader=new ImageLoader(context, listview);
	}

	@Override
	public int getCount() {
		return smss.size();
	}

	@Override
	public Object getItem(int position) {
		return smss.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public static final int TYPE_LEFT=1;
	public static final int TYPE_RIGHT=2;
	
public int getType(int position){
		Sms sms=smss.get(position);
		//return sms.getType();
		 if(sms.getType()==1){
		 return TYPE_LEFT;
		 }else{
		 return TYPE_RIGHT;
		 }
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		//如果convertView==null  或者 
		//convertView中使用的布局和当前Item
		//所需要加载的布局不一样
		//都需要重新创建convertView
		if(convertView==null   || 
				getType(position) != ((ViewHolder)convertView.getTag()).type){
			////重新创建convertView
			if(getType(position)==TYPE_LEFT){ //别人发的短息位于左边
				convertView=inflater.inflate(R.layout.item_lv_sms_left, null);
			}else { //自己发的位于右边
				convertView=inflater.inflate(R.layout.item_lv_sms_right, null);
			}
			//给ViewHolder中的属性进行赋值
			holder = new ViewHolder();
			holder.type=getType(position);
			holder.ivPhoto=(ImageView)convertView.findViewById(R.id.iv_Photo);
			holder.tvDate=(TextView)convertView.findViewById(R.id.tv_sms_date);
			holder.tvBody=(TextView)convertView.findViewById(R.id.tv_sms_body);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		Sms sms=smss.get(position);
		//赋值
		//设置时间格式为yyyy-MM-dd
		holder.tvDate.setText(new SimpleDateFormat("yyyy-MM-dd")
		.format(new Date(sms.getDate())));
		holder.tvBody.setText(sms.getBody());
		if(getType(position)==TYPE_LEFT){
//			imageloader.displayImage(photoId, holder.ivPhoto, position);
		return convertView;
	}
		return convertView;
	}
	

	class ViewHolder{
		ImageView ivPhoto;
		TextView tvDate;
		TextView tvBody;
		int type;
	}
	
	
}

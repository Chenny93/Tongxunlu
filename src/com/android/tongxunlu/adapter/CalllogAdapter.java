package com.android.tongxunlu.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.provider.CallLog.Calls;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.entity.Calllog;
import com.android.tongxunlu.util.DateUtil;
import com.android.tongxunlu.util.ImageLoader;

public class CalllogAdapter extends BaseAdapter{
	private Context context;
	private List<Calllog> logs;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	
	public CalllogAdapter(Context context, List<Calllog> logs,ListView listView) {
		this.context=context;
		this.logs=logs;
		this.inflater=LayoutInflater.from(context);
		this.imageLoader=new ImageLoader(context, listView);
	}

	@Override
	public int getCount() {
		return logs.size();
	}

	@Override
	public Object getItem(int position) {
		return logs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item_lv_calllog, null);
			holder=new ViewHolder();
			holder.ivPhoto=(ImageView)convertView.findViewById(R.id.iv_Photo);
			holder.ivIcon=(ImageView)convertView.findViewById(R.id.iv_calllog);
			holder.tvName=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tvDate=(TextView)convertView.findViewById(R.id.tv_date);
			holder.tvNumber=(TextView)convertView.findViewById(R.id.tv_number);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
		Calllog log=logs.get(position);
		//联系人头像
//		imageLoader.displayImage(log.getPhotoId(), holder.ivPhoto, position);
		//姓名为空（本地没有查询到），标记为未知号码
		holder.tvName.setText(log.getName() == null ? "未知号码" : log.getName());
		//未接来电 文本标记为红色
		if(log.getType()==Calls.MISSED_TYPE){
			holder.tvName.setTextColor(Color.RED);
		}else{
			holder.tvName.setTextColor(Color.BLACK);
		}
		//呼出
		if( log.getType() == Calls.OUTGOING_TYPE ) {
			holder.ivIcon.setVisibility(View.VISIBLE);
		}else if( log.getType() == Calls.INCOMING_TYPE ) {
			holder.ivIcon.setImageResource(R.drawable.img01g_50);
		}
		//显示电话号码
		holder.tvNumber.setText(log.getNumber());
		//显示通话时间
		holder.tvDate.setText(DateUtil.parse(log.getDate()));
		return convertView;
	}

	class ViewHolder{
		ImageView ivPhoto;
		TextView tvName;
		ImageView ivIcon;
		TextView tvNumber;
		TextView tvDate;
	}
	
}

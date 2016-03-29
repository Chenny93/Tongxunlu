package com.android.tongxunlu.adapter;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.entity.Contact;
import com.android.tongxunlu.util.ContactNames;
import com.android.tongxunlu.util.ImageLoader;

public class ContactAdapter extends BaseAdapter{
	private Context context;
	private List<Contact> contacts;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	
	public ContactAdapter(Context context, List<Contact> contacts,GridView gridView) {
		this.context=context;
		this.contacts=contacts;
		this.contacts.add(0, new Contact());
		this.inflater=LayoutInflater.from(context);
		imageLoader=new ImageLoader(context, gridView);
	}
	
	@Override
	public int getCount() {
		return 21;
	}

	@Override
	public Object getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item_gv_contact, null);
			holder=new ViewHolder();
			holder.tvName=(TextView)convertView.findViewById(R.id.tv_Name);
			holder.ivPhoto=(ImageView)convertView.findViewById(R.id.iv_Photo);
			convertView.setTag(holder);	
		}
		holder=(ViewHolder)convertView.getTag();
		//在GridView的第一个位置添加一个小图标，点击实现添加联系人
		if(position==0){
//			holder.tvName.setText("添加联系人");
			Random random = new Random();
			holder.tvName.setText(ContactNames.contactNames[random.nextInt(ContactNames.contactNames.length)]);
//			//设置【添加联系人】的小图标
//			holder.ivPhoto.setImageResource(R.drawable.img02_07);
			holder.ivPhoto.setImageResource(R.drawable.img_11);
			/*holder.ivPhoto.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context,ContactActivity.class);
					context.startActivity(intent);
				}
			});*/
//			return convertView; 
		}
		//GridView的其他位置则是添加之后显示出来的联系人头像和姓名
		//给holder中的控件赋值
		Contact contact=contacts.get(position);
		holder.tvName.setText(contact.getName());
		//设置联系人头像 Bitmap
		imageLoader.displayImage(contact.getPhotoId(), holder.ivPhoto, position); 
		return convertView;
	}

	class ViewHolder{
		ImageView ivPhoto;
		TextView tvName;
	}
	
}

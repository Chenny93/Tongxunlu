package com.android.tongxunlu.fragment;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tongxunlu.R;
import com.android.tongxunlu.adapter.CalllogAdapter;
import com.android.tongxunlu.biz.CalllogBiz;
import com.android.tongxunlu.entity.Calllog;

/**
 * 描述通话记录界面
 * 创新点：
 * 1.点击通话记录上方的电话号码，弹出一个拨号软键盘
 * 2.通过动画的平移方式弹出拨号软键盘——TranslateAnimation——属于补间动画
 */
public class DialFragment extends Fragment{
	private ListView listView;
	private TextView tvTitle;
	private GridView gvKeyboard;
	private RelativeLayout relativeLayout;
	private String[] keys={"1","2","3","4","5","6","7","8","9","*","0","#"};
	private ImageView imagecall;
	private ImageView imagedelete;
	private ImageView ivKeyBoardDown;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_dial, null);
		//控件初始化
		listView=(ListView)view.findViewById(R.id.lv_dial);
		gvKeyboard=(GridView)view.findViewById(R.id.gv_keyboard);
		tvTitle=(TextView)view.findViewById(R.id.tv_dial_title);
		relativeLayout=(RelativeLayout)view.findViewById(R.id.rl_dial2);
		imagecall=(ImageView) view.findViewById(R.id.iv_dial_call);
		imagedelete=(ImageView) view.findViewById(R.id.iv_dial_delete);
		ivKeyBoardDown = (ImageView) view.findViewById(R.id.iv_keyboarddown);
		//添加监听
		setListener();
		//获取通话记录集合 List <Calllog>
		CalllogBiz biz=new CalllogBiz(getActivity());
		List<Calllog> logs=biz.loadLogs();
		//自定义Adapter
		CalllogAdapter adapter=new CalllogAdapter(getActivity(), logs, listView);
		listView.setAdapter(adapter);
		
		//给gvKeyboard设置Adapter
		KeyBoardAdapter keyAdapter=new KeyBoardAdapter();
		gvKeyboard.setAdapter(keyAdapter);
		return view;
	}
	
	private void setListener() {
		listView.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {		
			}
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//TODO  
			}
		});
		
		tvTitle.setOnTouchListener(new OnTouchListener() {
//			int count=0;
			public boolean onTouch(View v, MotionEvent event) {
				//隐藏软键盘
				if(event.getAction()==MotionEvent.ACTION_DOWN){
//	            count++;
//				if(count==1){
				//使用动画弹出软键盘
				relativeLayout.setVisibility(View.VISIBLE);
				//执行动画
				Animation a=new TranslateAnimation(0, 0, relativeLayout.getHeight(), 0);
				a.setDuration(300);
				relativeLayout.startAnimation(a);
				tvTitle.setText(" ");
//				} 
//				else {
//					//隐藏软键盘
//					relativeLayout.setVisibility(View.GONE);
//					//执行动画
//					Animation a=new TranslateAnimation(0, 0, 0, relativeLayout.getHeight());
//					a.setDuration(300);
//					relativeLayout.startAnimation(a);
//					count=0;
//					}
				}
				return false;
			}
		});
		
		ivKeyBoardDown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//隐藏软键盘
				relativeLayout.setVisibility(View.GONE);
				//执行动画
				Animation a=new TranslateAnimation(0, 0, 0, relativeLayout.getHeight());
				a.setDuration(300);
				relativeLayout.startAnimation(a);
			}
		});
	

	//点击拨号键添加号码
	gvKeyboard.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, 
            int position, long id) {
			String s =(String) parent.getItemAtPosition(position);	
			String pres=tvTitle.getText().toString();
			StringBuilder sb=new StringBuilder(pres);
			sb.append(s);
		    tvTitle.setText(sb);		
		}
	});

	//拨打电话
imagecall.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent=new Intent();
intent.setAction(Intent.ACTION_CALL);
			String phone=tvTitle.getText().toString();
intent.setData(Uri.parse("tel:"+phone));
startActivity(intent);
		}
	});

	//删除号码
imagedelete.setOnClickListener(new OnClickListener() {	
		@Override
		public void onClick(View arg0) {
			String sb=tvTitle.getText().toString();
			if(sb.length()!=0){
				String sb1=sb.substring(0,sb.length()-1);
				tvTitle.setText(sb1);
			} else {
				Toast.makeText(getContext(), "请输入号码", Toast.LENGTH_SHORT).show();
			}
		}
	});

	//点击通话记录拨打电话
listView.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, 
int position, long id) {	
Calllog log=(Calllog) parent.getItemAtPosition(position);
			String phonenumber=log.getNumber();
			Intent intent=new Intent();
intent.setAction(Intent.ACTION_CALL);
intent.setData(Uri.parse("tel:"+phonenumber));
startActivity(intent);	
		}
	});
}


	class KeyBoardAdapter extends BaseAdapter{
		public int getCount() {
			return keys.length;
		}
		public Object getItem(int position) {
			return keys[position];
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=View.inflate(getActivity(), R.layout.item_gv_keyboard, null);
			TextView tvKey=(TextView)convertView.findViewById(R.id.tv_Key);
			tvKey.setText(keys[position]);
			return convertView;
		}
	}
	
	}


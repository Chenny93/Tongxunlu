package com.android.tongxunlu.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.adapter.ContactAdapter;
import com.android.tongxunlu.biz.ContactBiz;
import com.android.tongxunlu.entity.Contact;

public class ContactFragment extends Fragment{
	
	private GridView gridView;
	
/*	public ContactFragment(Context context){
		
	}*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_contact, null);
		gridView = (GridView)view.findViewById(R.id.gv);
		setListener();
		//访问业务类 ContactBiz 获取联系人列表
		ContactBiz contactBiz = new ContactBiz( this);
		contactBiz.execute();
		return view;
	}
	
	private void setListener(){
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * 更新 GridView
	 * @param result
	 */
	public void updateGridView(List<Contact> result) {
		System.out.println("updateGridView前");
		ContactAdapter contactAdapter = new ContactAdapter(getContext(), result, gridView);
		System.out.println("updateGridView后");
		gridView.setAdapter(contactAdapter);
	}

}

package com.android.tongxunlu.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.entity.Contact;
import com.android.tongxunlu.fragment.ContactFragment;

public class ContactActivity extends FragmentActivity{
	
	private ImageView ivEdit;
	private EditText etContactName;
	private EditText etNumber;
	private List<Contact> contacts;
	private ImageView ivSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_detail);
		setViews();
		setListener();
}
	
	private void setViews(){
		ivEdit = (ImageView) findViewById(R.id.iv_contact_detail);
		ivSave = (ImageView) findViewById(R.id.iv_contact_detail2);
		etContactName = (EditText) findViewById(R.id.tv_contact_detail);
		etNumber = (EditText) findViewById(R.id.tv_contact_detail2);
		contacts = new ArrayList<Contact>();
		}
	
	private void setListener(){
		ivEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Contact c = new Contact();
				c.setName(etContactName.getText().toString());
				c.setPhone(etNumber.getText().toString());
				contacts.add(c);
			}
		});
		
		ivSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContactActivity.this.finish();
				System.out.println("finish了");
				ContactFragment contactFragment = new ContactFragment();
				System.out.println("eeeeeeeeeeeeeeeeeee");
				contactFragment.updateGridView(contacts);		
				System.out.println("contacts.size():"+contacts.size());
			}
		});
	}
	
	}

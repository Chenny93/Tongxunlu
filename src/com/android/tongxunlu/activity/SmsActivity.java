package com.android.tongxunlu.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tongxunlu.R;
import com.android.tongxunlu.adapter.SmsAdapter;
import com.android.tongxunlu.biz.SmsBiz;
import com.android.tongxunlu.entity.Sms;

public class SmsActivity extends Activity {
	
	private ListView listView;
	private TextView textView;
	private EditText etSmsBody;
	private int threadId;
	private int photoId;
	private List<Sms> smss = new ArrayList<Sms>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		setViews();
		//获取ThreadId
		 threadId = getIntent().getIntExtra("threadId", 0);
		 photoId = getIntent().getIntExtra("photoId", 0);
		String name = getIntent().getStringExtra("name");
		//查询该id 中所有的短消息
		SmsBiz smsBiz = new SmsBiz( this);
		smss = smsBiz.findSmsByThreadId(threadId);
		
		textView.setText(name);
		
		SmsAdapter smsAdapter = new SmsAdapter(this, smss, photoId, listView);
		listView.setAdapter(smsAdapter);
	}
	
	private void setViews() {
		listView = (ListView) findViewById(R.id.lv_sms);
		textView = (TextView) findViewById(R.id.tv_sms);
		etSmsBody = (EditText) findViewById(R.id.et_smsBody);
	}
	
/*	//处理返回的发送状态 
	String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	Intent sentIntent = new Intent(SENT_SMS_ACTION);
	PendingIntent sentPI = PendingIntent.getBroadcast(SmsActivity.this, 0, sentIntent,0);
	// register the Broadcast Receivers
	this.registerReceiver( new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context _context, Intent _intent) {
	        switch (getResultCode()) {
	        case Activity.RESULT_OK:
	        	Toast.makeText(SmsActivity.this,"短信发送成功", Toast.LENGTH_SHORT).show();
	        break;
	        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
	        break;
	        case SmsManager.RESULT_ERROR_RADIO_OFF:
	        break;
	        case SmsManager.RESULT_ERROR_NULL_PDU:
	        break;
	        }
	    }
	}, new IntentFilter(SENT_SMS_ACTION));
	
	//处理返回的接收状态 
	String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	// create the deilverIntent parameter
	Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
	PendingIntent deliverPI = PendingIntent.getBroadcast( this, 0,deliverIntent, 0);
	SmsActivity.this.registerReceiver( new BroadcastReceiver() {
	   @Override
	   public void onReceive(Context _context, Intent _intent) {
	       Toast.makeText(SmsActivity.this,
	  "收信人已经成功接收", Toast.LENGTH_SHORT)
	  .show();
	   }
	}, new IntentFilter(DELIVERED_SMS_ACTION));*/
	
	public void sendSms(View view){
		
		switch (view.getId()) {
		case R.id.bt_sendSms:
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(textView.getText().toString(), null, etSmsBody.getText().toString(), null, null);
				Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
				etSmsBody.setText("请输入短信内容");
				SmsAdapter smsAdapter = new SmsAdapter( this, smss, photoId, listView);
				listView.setAdapter(smsAdapter);
			break;
		}
	}
}


	


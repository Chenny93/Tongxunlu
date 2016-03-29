package com.android.tongxunlu.biz;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.util.Log;

import com.android.tongxunlu.entity.Conversation;
import com.android.tongxunlu.entity.Sms;

public class SmsBiz {
	
	private Context context;
	private int smsId;
	
	public SmsBiz(Context context){
		this.context = context;
	}
	
	/**
	 * 查询所有的短消息会话信息
	 */
	public List<Conversation> findAllConversations(){
		ContentResolver resolver = context.getContentResolver();
		// uri 固定写法
		Uri uri = Uri.parse("content://mms-sms/conversations");
		String[] columns = {
				"thread_id",                    //0
				"address",                      //1  电话号码
				"body",                           //2
				"date"                             //3
		};
		//降序排序
		Cursor cursor = resolver.query(uri, columns, null, null, "date  desc");
		List<Conversation> cs= new ArrayList<Conversation>();
		while(cursor.moveToNext()) {
			Conversation conversation = new Conversation();
			conversation.setId(cursor.getInt(0));
			conversation.setNumber(cursor.getString(1));
			conversation.setBody(cursor.getString(2));
			conversation.setDate(cursor.getLong(3));
			
			//根据手机号码获取联系人头像
			conversation.setPhotoId(getMessageImage(cursor.getString(1)));
			System.out.println("chenny getMessageImage");
			//根据手机号码获取联系人名字
			conversation.setName(getMessageName(cursor.getString(1)));
			System.out.println("chenny getMessageName");
			cs.add(conversation);
		}
		cursor.close();
		return cs;
	}
	
	/**
	 * 
	 */
	public List<Sms> findSmsByThreadId( int threadId){
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://sms/");
		String[] columns = {
				"date",                     //0
				"body",                    //1
				"type",                     //2
				"_id"                        //3
		};
		//升序排序
		Cursor cursor = resolver.query(uri, columns, "thread_id="+threadId, null, "date  asc");
		List<Sms> smss = new ArrayList<Sms>();
		while(cursor.moveToNext()){
			Sms sms = new Sms();
			sms.setDate(cursor.getLong(0));
			sms.setBody(cursor.getString(1));
			sms.setType(cursor.getInt(2));
			sms.setId(cursor.getInt(3));
			smss.add(sms);
		}
		cursor.close();
		return smss;
	}
	
	/**
	 * 根据手机号码获取联系人头像
	 * @param address
	 * @return
	 */
	private int getMessageImage(String address) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Contacts.CONTENT_URI;
		String [] columns = {Contacts.PHOTO_ID};
		Cursor cursor = resolver.query(uri, columns, Contacts._ID+"="+smsId, null, null);
		if(cursor.moveToNext()) {
			int photoId = cursor.getInt(0);
			return photoId;
		}
		cursor.close();
		return 0;
	}
	
	private String getMessageName(String address) {
		if(address == null) {
			address = "未知号码";
		}
		Log.i("SmsBiz", "address:"+address);
		String number = getString(address);
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Data.CONTENT_URI;
		String[] columns = {Data.RAW_CONTACT_ID};
		Cursor cursor = resolver.query(uri, columns, "data1 = ?", new String [] {number}, null);
		if (cursor.moveToNext()) {
			smsId = cursor.getInt(0);
			Log.i("SmsBiz", "smsId:"+smsId);
		}
		cursor.close();
		String[] columns1 = {Data.MIMETYPE,Data.DATA1};
		Cursor cursor1 = resolver.query(uri, columns1, Data.RAW_CONTACT_ID+"= "+smsId, null, null);
		while(cursor1.moveToNext()) {
			String mimetype = cursor1.getString(0);
			String data1 = cursor1.getString(1);
			if(mimetype.equals("vnd.android.cursor.item/name")){
				String name = data1;
				return name;
			}
		}
		cursor1.close();
		return address;
	}
	
	/**
	 * 判断号码类型
	 * @param address
	 * @return
	 */
	private String getString(String address) {
		if(address.indexOf("-")!= -1) {
			return address;
		} else {
			char[ ] arr = address.toCharArray();
			String str = "";
			for(int i = 0; i<arr.length; i++){
				if(i == 1) {
					str += " ";
				}
				if(i == 4 || i == 7) {
					str +="-";
				}
				str += arr[i];
			}
			Log.i("SmsBiz","str"+str);
			return str;
		}
	}
	
}

package com.android.tongxunlu.biz;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.support.v4.app.Fragment;

import com.android.tongxunlu.entity.Contact;
import com.android.tongxunlu.fragment.ContactFragment;

public class ContactBiz  extends AsyncTask<String, String, List<Contact>>{
	
	private Fragment fragment;
	private Context context;
	
	 public ContactBiz(Fragment fragment) {
		this.fragment = fragment;
		this.context = fragment.getActivity();   // getContext()也行
	}

	 /**
	  * 工作线程中异步查询联系人列表，在后台进程中执行
	  */
	@Override
	protected List<Contact> doInBackground(String... params) {
		List<Contact> contacts = loadContacts();
		return contacts;
	}
	
	public  List<Contact> loadContacts(){
		ContentResolver resolver = context.getContentResolver();
		// content : // com.android.contacts/contacts
		Uri uri = Contacts.CONTENT_URI;
		String[] columns = {
				Contacts._ID,                    //0    contacts表中的ID
				Contacts.PHOTO_ID         //1
		};
		Cursor cursor = resolver.query(uri, columns, null, null, null);
		List<Contact> contacts = new ArrayList<Contact>();
		while(cursor.moveToNext()) {
			Contact contact = new Contact();
			int contactId = cursor.getInt(0);
			contact.setPhotoId(cursor.getInt(1));
			contact.setId(contactId);
			//通过 id 去 data 表查询其他数据
			//content: //com.android.contacts/data
			Uri dataUri = Data.CONTENT_URI;
			String[] dataColumns = {
					Data._ID,                           //0  selection 表中的id
					Data.MIMETYPE,               //1
					Data.DATA1,                      //2
//					Data.DATA15
			};
			//String selection,相当于SQL中的where语句
			//通过contacts 表中的_id 跟 data 表中的raw_contact_id 关联起来实现查询操作
			Cursor cursor2 = resolver.query(dataUri, dataColumns, Data.RAW_CONTACT_ID+"="+contactId, null, null);
			while(cursor2.moveToNext()) {
				int dataId = cursor2.getInt(0);
				// mimetype 返回的数据不是id，而是 mimetypes表中相应id对应的value
				String mimetype = cursor2.getString(1);
				String data1 = cursor2.getString(2);
//				String data15 = cursor2.getString(3);
				//判断当前的这一条数据是什么类型的属性
				if(mimetype.equals(Email.CONTENT_ITEM_TYPE)){
					//data1 是邮箱
					contact.setEmail(data1);
				} else if(mimetype.equals(Phone.CONTENT_ITEM_TYPE)) {
					//data1 是电话
					contact.setPhone(data1);
				} else if(mimetype.equals("vnd.android.cursor.item/postal-address_v2")) {
					//data1 是地址
					contact.setAddress(data1);
				} else if(mimetype.equals("vnd.android.cursor.item/photo")) {
					//data15 是头像,暂不处理
/*					try {
						contact.setPhoto(data15);
					} catch (NumberFormatException e) {
						// TODO: handle exception
						System.out.println("NumberFormatException");
					}*/
				} else if(mimetype.equals("vnd.android.cursor.item/name")) {
					//data1 是联系人的名字
					contact.setName(data1);
				}
			}
			cursor2.close();
			contacts.add(contact);
		}
		cursor.close();
		return contacts;
	}
	
	/**
	 * doInBackground 方法执行完毕之后，执行此方法
	 */
	@Override
	protected void onPostExecute(List<Contact> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//更新UI GridView
		ContactFragment contactFragment = (ContactFragment)fragment;
		contactFragment.updateGridView(result);
	}

}

package com.android.tongxunlu.biz;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;

import com.android.tongxunlu.entity.Calllog;

/**
 * 通话记录业务类
 * @author 陈俊畅
 *
 */
public class CalllogBiz {
	private Context context;
	
	public CalllogBiz(Context context){
		this.context = context;
	}
	
	/**
	 * 加载所有的通话记录
	 * @return
	 */
	public List<Calllog> loadLogs(){
		ContentResolver cr = context.getContentResolver();
		Uri uri = Calls.CONTENT_URI;
		String[] columns = {
				Calls._ID,                  //0
				"photo_id",                //1
				"name",                     //2
				Calls.NUMBER,         //3
				Calls.TYPE,              //4 通话类型：incoming 呼入,outgoing呼出,missed 未接来电.
				Calls.DATE               //5
		};
		//按通话时间降序排列
		Cursor cursor = cr.query(uri, columns, null, null, Calls.DATE+" desc");
		List<Calllog> logs = new ArrayList<Calllog>();
		//Cursor 移动指针查询数据
		while(cursor.moveToNext()) {
			Calllog log = new Calllog();
			log.setId(cursor.getInt(0));
			log.setPhotoId(cursor.getInt(1));
			log.setName(cursor.getString(2));
			log.setNumber(cursor.getString(3));
			log.setType(cursor.getInt(4));
			log.setDate(cursor.getLong(5));
			//将查询到的所有数据存储到集合中
			logs.add(log);
	}
		//关闭cursor,防止内存泄漏
		cursor.close();
		return logs;
}
}

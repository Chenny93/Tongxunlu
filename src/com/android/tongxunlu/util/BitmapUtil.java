package com.android.tongxunlu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.ContactsContract.Data;

public class BitmapUtil {
	
	/**
	 * 通过路径path 返回相应的Bitmap对象
	 * @param path
	 * @return
	 */
	public static Bitmap loadBitmap(String path) {
		File file = new File(path);
		if(!file.exists()) {
			return null;
		}
		return BitmapFactory.decodeFile(path);
	}
	
	/**
	 * 保存图片
	 * @param bitmap  要保存的图片
	 * @param targetFile  保存图片的路径
	 * @throws FileNotFoundException
	 */
	public static void saveBitmap(Bitmap bitmap, File targetFile) throws FileNotFoundException {
		// 判断父目录是否存在
		if (!targetFile.getParentFile().exists()) {
			//创建父目录
			targetFile.getParentFile().mkdirs();
		}
		//将bitmap 输出到 targetFile 中
		FileOutputStream fos = new FileOutputStream(targetFile);
		/**
		 * @param format 图片压缩格式
		 * @param quality 压缩比率
		 * @param stream 输出流 
		 */
		bitmap.compress(CompressFormat.JPEG, 100, fos);
	}
	
	/**
	 *  从字节数组中按照用户的参数要求压缩图片
	 * @param bytes  源字节数组
	 * @param width  压缩后的图片宽度
	 * @param height 压缩后的图片高度
	 * @return
	 */
	public static Bitmap loadBitmap(byte[ ] bytes, int width, int height) {
		Options options = new Options();
		// 仅加载图片的边界属性
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		//获取原始的宽度和高度
		int rawWidth = options.outWidth/width;
		int rawHeight = options.outHeight/height;
		int scale = rawWidth > rawHeight ? rawWidth : rawHeight;
		options.inSampleSize = scale;
		// 不仅仅加载边界属性
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
	}
	
	/**
	 * 通过photoId 从data 表中获取图片信息
	 * @param photoId
	 * @param context
	 * @return
	 */
	public static Bitmap loadBitmap(int photoId, Context context){
		//select data15 from data where _id=photoId
		ContentResolver resolver = context.getContentResolver();
		Uri dataUri = Data.CONTENT_URI;
		Cursor  cursor = resolver.query(dataUri, new String[]{Data.DATA15}, Data._ID+"="+photoId, null, null);
		Bitmap bitmap = null;
		if (cursor.moveToNext()) {
			byte[ ] bytes = cursor.getBlob(0);
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}
		cursor.close();
		return bitmap;
	}

}

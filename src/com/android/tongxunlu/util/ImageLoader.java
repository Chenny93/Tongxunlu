package com.android.tongxunlu.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.entity.Contact;

/**
 * 批量加载图片工具类
 */
public class ImageLoader {
	private Context context;
	private AbsListView listView;
	//声明用于保存缓存图片的Map
	private Map<Integer, SoftReference<Bitmap>> cache=new HashMap<Integer, SoftReference<Bitmap>>();
	//声明任务集合  存储图片下载任务
	private List<ImageLoaderTask> tasks=new ArrayList<ImageLoaderTask>();
	//声明工作线程  轮循任务集合
	private Thread workThread;
	private boolean isLoop=true;
	
	private LayoutInflater inflater;
	
	public static final int HANDLER_LOAD_IMAGE_SUCCESS=0;
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_LOAD_IMAGE_SUCCESS:
				//图片已经下载完成 
				ImageLoaderTask task=(ImageLoaderTask)msg.obj;
				//position
				int position=task.position;
				ImageView view=(ImageView)listView.findViewWithTag(position);
				//如果可以找到imageView
				if(view!=null){
					//如果bitmap不是null
					if(task.bitmap!=null){
						view.setImageBitmap(task.bitmap);
					}else {
//						Random random = new Random();
//						view.setImageResource(ContactNames.contactPhotoIds[random.nextInt(ContactNames.contactPhotoIds.length)]);
						view.setImageResource(R.drawable.img_11);
/*						inflater=LayoutInflater.from(context);
						View convertView=inflater.inflate(R.layout.item_contact, null);
						TextView tvNameSubString = (TextView) convertView.findViewById(R.id.tv_NameSubString);
						List<Contact> contacts = new ArrayList<Contact>();
						tvNameSubString.setText(contacts.get(task.position).getName().substring(0, 1));*/
					}
				}
				break;
			}
		}
	};

	public ImageLoader(Context context, AbsListView listView) {
		this.context=context;
		this.listView=listView;
		//对工作线程进行初始化 并且启动
		workThread=new Thread(){
			@Override
			public void run() {
				//不断的轮循任务集合  
				while(isLoop){
					//如果集合不是空集
					if(!tasks.isEmpty()){
						ImageLoaderTask task=tasks.remove(0);
						//获取请求路径  image/xxxx.jpg
						Bitmap bitmap=loadBitmap(task.photoId);
						task.bitmap=bitmap;
						//给handler发送消息 
						//消息中携带ImageLoaderTask
						//把bitmap 设置到 ImageView中
						Message msg=new Message();
						msg.what=HANDLER_LOAD_IMAGE_SUCCESS;
						msg.obj=task;
						handler.sendMessage(msg);
					}else{
						//空集合的话  工作线程等待 
						try {
							synchronized (workThread) {
								workThread.wait();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		//启动工作线程
		workThread.start();
	}
	
	/**
	 * 通过图片的photoId  获取图片对象
	 * @param photoId
	 * @return
	 */
	public Bitmap loadBitmap(int photoId){
		Bitmap bitmap=BitmapUtil.loadBitmap(photoId, context);
		//把bitmap存入内存缓存
		cache.put(photoId, new SoftReference<Bitmap>(bitmap));
		return bitmap;
	}

	
	public void displayImage(int photoId, ImageView ivAlbum,int position) {
		//给imageView设置不同的tag 
		//当图片在工作线程下载完毕后，
		//handler中使用该tag值可以找到相应ImageView
		ivAlbum.setTag(position);
		//判断缓存中是否已经下载过该图片
		//如果缓存中有，则直接获取使用。
		SoftReference<Bitmap> ref=cache.get(photoId);
		if(ref!=null && ref.get()!=null){
			Log.i("info", "当前图片是从缓存中读取的....");
			//缓存内有    直接使用
			Bitmap bitmap=ref.get();
			ivAlbum.setImageBitmap(bitmap);
			return;
		}
		
		//给holder的imageView设置图片
		//图片需要从服务端下载
		//不可以再此启动工作线程
		//向任务集合中添加一个图片下载任务
		ImageLoaderTask task=new ImageLoaderTask();
		task.photoId=photoId;
		task.position=position;
		//把task add 到tasks集合中
		tasks.add(task);
		synchronized (workThread) {
			//唤醒工作线程  继续轮循集合
			workThread.notify();
		}
		
	}

	/**
	 * 封装一个图片下载任务对象
	 */
	class ImageLoaderTask{
		int position; //
		int photoId; //图片Id
		Bitmap bitmap; //通过路径下载后的图片对象
	}

	public void stopThread() {
		isLoop=false;
		//唤醒工作线程 执行一次循环
		synchronized (workThread) {
			workThread.notify();
		}
	}
	
}

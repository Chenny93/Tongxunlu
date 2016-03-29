package com.android.tongxunlu.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.tongxunlu.R;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		try {
			Thread.sleep(4000);
			Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
			startActivity(intent);			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		Timer mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(intent);
			}
		}, 8000);*/
	}
}

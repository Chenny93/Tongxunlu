package com.android.tongxunlu.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.android.tongxunlu.R;
import com.android.tongxunlu.fragment.CalllogFragment;
import com.android.tongxunlu.fragment.ContactFragment;
import com.android.tongxunlu.fragment.DialFragment;
import com.android.tongxunlu.fragment.SmsFragment;

public class MainActivity extends FragmentActivity {
	
	private ViewPager viewPager;
	private ArrayList<Fragment> fs;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private RadioButton rb4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setViews();
		setListener();
		fs = new ArrayList<Fragment>();
		fs.add(new CalllogFragment());
		fs.add(new ContactFragment());
		fs.add(new SmsFragment());
		fs.add(new DialFragment());
		FragmentManager fm = getSupportFragmentManager();
		MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fm);
		viewPager.setAdapter(myPagerAdapter);
	}
	
	private void setViews() {
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		rb1=(RadioButton)findViewById(R.id.rb1);
		rb2=(RadioButton)findViewById(R.id.rb2);
		rb3=(RadioButton)findViewById(R.id.rb3);
		rb4=(RadioButton)findViewById(R.id.rb4);
	}	
	

	@SuppressWarnings("deprecation")
	private void setListener(){
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					rb1.setChecked(true);
					break;
				case 1:
					rb2.setChecked(true);
					break;
				case 2:
					rb3.setChecked(true);
					break;
				case 3:
					rb4.setChecked(true);
					break;
				}
			}
			@Override
			public void onPageScrolled( int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged( int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
/*	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.rb1:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rb2:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb3:
			viewPager.setCurrentItem(2);
			break;
		case R.id.rb4:
			viewPager.setCurrentItem(3);
			break;
		}
	}*/
	
	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem( int arg0) {
			// TODO Auto-generated method stub
			return fs.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fs.size();
		}
		
	}

}

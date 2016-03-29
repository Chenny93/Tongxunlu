package com.android.tongxunlu.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.tongxunlu.R;
import com.android.tongxunlu.adapter.CalllogAdapter;
import com.android.tongxunlu.biz.CalllogBiz;
import com.android.tongxunlu.entity.Calllog;

public class CalllogFragment extends Fragment{
	
	private ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_calllog, null);
		listView = (ListView)view.findViewById(R.id.lv_calllog);
		CalllogBiz calllogBiz = new CalllogBiz(getActivity());
		List<Calllog> logs = calllogBiz.loadLogs();
		CalllogAdapter calllogAdapter = new CalllogAdapter(getActivity(), logs, listView);
		listView.setAdapter(calllogAdapter);
		return view;
	}

}

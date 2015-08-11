package com.example.student_buy_android.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.HomeAdapter;

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class HomeFragment extends Fragment implements OnClickListener {

	View messageLayout;

	private ListView lv;
	private HomeAdapter showAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_home, container, false);

		init();

		showAdapter = new HomeAdapter(getActivity());
		lv.addHeaderView(inflater.inflate(R.layout.layout_head_view, null,
				false));
		lv.setAdapter(showAdapter);

		return messageLayout;
	}

	private void init() {
		lv = (ListView) messageLayout.findViewById(R.id.lv_first);
	}

	@Override
	public void onClick(View v) {

	}

}

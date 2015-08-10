package com.example.student_buy_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.ExcooAdapter;

public class ExcooFragment extends Fragment {

	View messageLayout;

	private ListView lv;
	private ExcooAdapter excooAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_excoo, container,
				false);

		initView();
		excooAdapter = new ExcooAdapter(getActivity());
		lv.setAdapter(excooAdapter);

		return messageLayout;
	}

	/**
	 * ≥ı ºªØ
	 */
	private void initView() {
		lv = (ListView) messageLayout.findViewById(R.id.lv_excoo);
	}
}

package com.example.student_buy_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.student_buy_android.R;

public class SameSchoolFragment extends Fragment {

	View messageLayout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_same_school, container,
				false);

		return messageLayout;
	}

}

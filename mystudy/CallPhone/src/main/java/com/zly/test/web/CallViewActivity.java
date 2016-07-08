package com.zly.test.web;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CallViewActivity extends Activity {

	private static final String TAG = "CallViewActivity";

	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_view);

		mTextView = (TextView) findViewById(R.id.phone_number_edit);
		String data = getIntent().getData().getQueryParameter("tel");
		if (data == null) {
			mTextView.setText("无");
		} else {
			mTextView.setText(data);
		}

	}

}

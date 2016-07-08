package com.zly.test.web.widget.overlay;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zly.test.web.R;

import static android.content.ContentValues.TAG;


public class OverlayLayoutView extends LinearLayout {

    private Button closeBtn;
    private Button actionBtn1;
    private Button actionBtn2;
    private TextView nameText;
    private TextView phoneText;

    public OverlayLayoutView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.in_call_layout, this,
                true);

        closeBtn = (Button) findViewById(R.id.close_btn);
        actionBtn1 = (Button) findViewById(R.id.action1_btn);
        actionBtn2 = (Button) findViewById(R.id.action2_btn);
        nameText = (TextView) findViewById(R.id.name_text);
        phoneText = (TextView) findViewById(R.id.phone_number_text);

    }

    public void setPhoneNumber(String phoneNum) {
        phoneText.setText(phoneNum);
    }

    public void setNameText(String name) {
        nameText.setText(name);
    }

    public void setCloseClickListener(OnClickListener listener) {
        closeBtn.setOnClickListener(listener);
    }

    public void setActionBtn1ClickListener(OnClickListener listener) {
        actionBtn1.setOnClickListener(listener);
    }

    public void setActionBtn2ClickListener(OnClickListener listener) {
        actionBtn2.setOnClickListener(listener);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        Log.d(TAG, "dispatchKeyEvent keyCode:" + event.getKeyCode());
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            OverlayView.hide(getContext());
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}

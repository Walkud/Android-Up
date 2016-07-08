package com.walkud.self.module.transparent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.walkud.self.R;

/**
 * 透明界面
 * Created by Walkud on 16/7/8.
 */
public class TransparentActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survive);
    }
}

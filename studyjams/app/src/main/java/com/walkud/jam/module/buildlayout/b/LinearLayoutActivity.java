package com.walkud.jam.module.buildlayout.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.walkud.jam.R;
import com.walkud.jam.module.BaseActivity;

import butterknife.ButterKnife;

/**
 * 学习创建Lauout_B
 * Created by jan on 16/3/29.
 */
public class LinearLayoutActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout);

        ButterKnife.bind(this);
    }
}

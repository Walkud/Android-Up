package com.walkud.jam.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.walkud.jam.R;
import com.walkud.jam.module.buildlayout.BuildLayoutActivity;
import com.walkud.jam.module.makeapp.MakeAppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jan on 16/3/29.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.buttonLayoutBtn)
    Button buttonLayoutBtn;
    @BindView(R.id.makeAppBtn)
    Button makeAppBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        buttonLayoutBtn.setOnClickListener(this);
        makeAppBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.buttonLayoutBtn:
                toIntent(BuildLayoutActivity.class);
                break;
            case R.id.makeAppBtn:
                toIntent(MakeAppActivity.class);
                break;
        }

    }
}

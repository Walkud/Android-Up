package com.walkud.jam.module.buildlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.walkud.jam.R;
import com.walkud.jam.module.BaseActivity;
import com.walkud.jam.module.buildlayout.b.LinearLayoutActivity;
import com.walkud.jam.module.buildlayout.b.RelativeLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jan on 16/3/29.
 */
public class BuildLayoutActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.linearLayoutBtn)
    Button linearLayoutBtn;
    @BindView(R.id.relativeLayoutBtn)
    Button relativeLayoutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_layout);

        ButterKnife.bind(this);

        linearLayoutBtn.setOnClickListener(this);
        relativeLayoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.linearLayoutBtn:
                toIntent(LinearLayoutActivity.class);
                break;
            case R.id.relativeLayoutBtn:
                toIntent(RelativeLayoutActivity.class);
                break;
        }
    }
}

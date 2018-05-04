package com.walkud.self.module.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;
import com.walkud.self.view.RoundRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Walkud on 17/11/17.
 */

public class RoundRelativeActivity extends BaseActivity {

    @BindView(R.id.round_relative_layout)
    RoundRelativeLayout roundRelativeLayout;
    @BindView(R.id.shadow_view)
    RoundShadowView roundShadowView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_relative);
        ButterKnife.bind(this);

        roundRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        roundRelativeLayout.setFoucsStatus(R.color.colorPrimary, 1.1f);
                        roundShadowView.setScaleX(1.1f);
                        roundShadowView.setScaleY(1.1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        roundRelativeLayout.setFoucsStatus(R.color.colorAccent, 1f);
                        roundShadowView.setScaleX(1f);
                        roundShadowView.setScaleY(1f);
                        return true;
                }

                return false;
            }
        });
    }
}

package com.walkud.jam.module.makeapp.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.walkud.jam.R;
import com.walkud.jam.module.BaseActivity;

/**
 * Created by jan on 16/4/20.
 */
public class ObjectActivity extends BaseActivity {

    private int privateStudy;
    int defaultStudy;
    protected int protectedStudy;
    public int publicStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.img_football);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        setContentView(imageView);

        init();
    }

    private void init() {
        privateStudy = 1;
        defaultStudy = 2;
        protectedStudy = 3;
        publicStudy = 4;
    }
}

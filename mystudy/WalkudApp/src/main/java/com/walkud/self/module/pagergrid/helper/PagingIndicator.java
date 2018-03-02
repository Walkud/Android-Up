package com.walkud.self.module.pagergrid.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.walkud.self.R;


/**
 * Created by zhuguohui on 2016/8/21 0021.
 */
public class PagingIndicator extends LinearLayout implements PagingScrollHelper.PageIndicator {
    public PagingIndicator(Context context) {
        this(context, null);
    }

    public PagingIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void InitIndicatorItems(int itemsNumber) {
        removeAllViews();
        for (int i = 0; i < itemsNumber; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.icon_switch_n_v2);
            imageView.setPadding(10, 0, 10, 0);
            addView(imageView);
        }
    }

    @Override
    public void onPageSelected(int pageIndex) {
        ImageView imageView = (ImageView) getChildAt(pageIndex);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.icon_switch_s_v2);
        }
    }

    @Override
    public void onPageUnSelected(int pageIndex) {
        ImageView imageView = (ImageView) getChildAt(pageIndex);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.icon_switch_n_v2);
        }
    }
}

package com.walkud.self.module.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.walkud.self.R;

/**
 * Created by Walkud on 17/11/17.
 */

public class RoundShadowView extends View {

    private Paint mShadowPaint;
    private float mBmfRadius;
    private float mRoundRadius;
    private float mPadding;
    private int color;
    private RectF rectF;

    public RoundShadowView(Context context) {
        this(context, null);
    }

    public RoundShadowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RoundShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundShadowView);
            mBmfRadius = ta.getDimension(R.styleable.RoundShadowView_rsv_bmf_radius, 0);
            mRoundRadius = ta.getDimension(R.styleable.RoundShadowView_rsv_round_radius, 0);
            mPadding = ta.getDimension(R.styleable.RoundShadowView_rsv_padding, 0);
            color = ta.getColor(R.styleable.RoundShadowView_rsv_color, Color.BLACK);
            ta.recycle();
        }


        mShadowPaint = new Paint();
        mShadowPaint.setColor(color);
        // 设置画笔遮罩滤镜  ,传入度数和样式
        mShadowPaint.setMaskFilter(new BlurMaskFilter(mBmfRadius, BlurMaskFilter.Blur.OUTER));

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(mPadding, mPadding, w - mPadding, h - mPadding);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        onDrawShadow(canvas);
    }

    /**
     * 绘制外发光阴影
     *
     * @param canvas
     */
    private void onDrawShadow(Canvas canvas) {
        canvas.save();
        //裁剪处理(使阴影矩形框内变为透明)
        if (mRoundRadius > 0) {
            canvas.clipRect(0, 0, getWidth(), getHeight());
            RectF tempRectF = new RectF();
            tempRectF.set(rectF);
            tempRectF.inset(mRoundRadius / 2f, mRoundRadius / 2f);
            canvas.clipRect(rectF, Region.Op.DIFFERENCE);
        }
        //绘制外发光阴影效果
        canvas.drawRoundRect(rectF, mRoundRadius, mRoundRadius, mShadowPaint);
        canvas.restore();
    }


}

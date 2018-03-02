package com.walkud.self.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.walkud.self.R;


/**
 * Created by owen on 2016/10/20.
 */

public class RoundRelativeLayout extends RelativeLayout {

    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;

    private boolean mIsDrawRound;
    private Path mRoundPath;
    private Paint mRoundPaint;
    private Paint mBorderPaint;
    private RectF mRoundRectF;
    private float mBorderSize;
    private float mRoundRadius = 0;

    private Paint mShadowPaint;

    public RoundRelativeLayout(Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        int mColor = 0;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout);
            mRoundRadius = ta.getDimension(R.styleable.RoundRelativeLayout_rfl_radius, 0);
            mTopLeftRadius = ta.getDimension(R.styleable.RoundRelativeLayout_topLeftRadius, mRoundRadius);
            mTopRightRadius = ta.getDimension(R.styleable.RoundRelativeLayout_topRightRadius, mRoundRadius);
            mBottomLeftRadius = ta.getDimension(R.styleable.RoundRelativeLayout_bottomLeftRadius, mRoundRadius);
            mBottomRightRadius = ta.getDimension(R.styleable.RoundRelativeLayout_bottomRightRadius, mRoundRadius);
            mColor = ta.getColor(R.styleable.RoundRelativeLayout_border_color, 0);
            mBorderSize = ta.getDimension(R.styleable.RoundRelativeLayout_border_size, 0);
            ta.recycle();
        }
        mIsDrawRound = mTopLeftRadius != 0 || mTopRightRadius != 0 || mBottomLeftRadius != 0 || mBottomRightRadius != 0;

        mRoundPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mRoundPaint.setAntiAlias(true);
        if (mColor != 0) {
            mBorderPaint.setColor(mColor);
        }
        mBorderPaint.setStrokeWidth(mBorderSize);

        // 取两层绘制交集。显示下层。
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));


        mShadowPaint = new Paint();
        mShadowPaint.setColor(Color.parseColor("#0f0f12"));
        // 设置画笔遮罩滤镜  ,传入度数和样式
        mShadowPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));

//        setLayerType(LAYER_TYPE_SOFTWARE, null);
//        setClipChildren(false);
//        ShadowView shadowView = new ShadowView(context);
//        shadowView.setLayoutParams(new LayoutParams(dp2px(166), dp2px(166)));
//        this.addView(shadowView);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if ((w != oldw || h != oldh) && mIsDrawRound) {
            final Path path = new Path();
            float offset = mBorderSize / 2;
            mRoundRectF = new RectF(0 + offset, 0 + offset, w - offset, h - offset);
            final float[] radii = new float[]{
                    mTopLeftRadius, mTopLeftRadius,
                    mTopRightRadius, mTopRightRadius,
                    mBottomRightRadius, mBottomRightRadius,
                    mBottomLeftRadius, mBottomLeftRadius};
            path.addRoundRect(mRoundRectF, radii, Path.Direction.CW);
            mRoundPath = path;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mIsDrawRound) {
            super.draw(canvas);
        } else {

            final int count = canvas.save();
            final int count2 = canvas.saveLayer(mRoundRectF, null, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            canvas.drawPath(mRoundPath, mRoundPaint);
            if (count2 > 0)
                canvas.restoreToCount(count2);
            canvas.restoreToCount(count);

            canvas.drawPath(mRoundPath, mBorderPaint);
        }
    }

    /**
     * 绘制外发光阴影
     *
     * @param canvas
     */
    private void onDrawShadow(Canvas canvas) {
        int size = 10;
//        RectF rectF = new RectF(size, size, getWidth() - size, getHeight() - size);
        RectF rectF = new RectF(50, 50, 380, 380);
        RectF mTempRectF = new RectF();
        canvas.save();
        //裁剪处理(使阴影矩形框内变为透明)
        if (mRoundRadius > 0) {
            canvas.clipRect(0, 0, getWidth(), getHeight());
            mTempRectF.set(rectF);
            mTempRectF.inset(mRoundRadius / 2f, mRoundRadius / 2f);
            canvas.clipRect(rectF, Region.Op.DIFFERENCE);
        }
        //绘制外发光阴影效果
//        canvas.drawRoundRect(rectF, mRoundRadius, mRoundRadius, mShadowPaint);


        canvas.drawRoundRect(rectF, mRoundRadius, mRoundRadius, mShadowPaint);
        canvas.restore();
    }

    public int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
    }

    /**
     * 设置边框颜色
     *
     * @param colorResId
     */
    public void setBorderColorResId(int colorResId) {
        mBorderPaint.setColor(getContext().getResources().getColor(colorResId));
    }

    /**
     * 设置焦点状态
     *
     * @param borderColor 边框颜色资源Id
     * @param scale       缩放系数
     */
    public void setFoucsStatus(int borderColor, float scale) {
        setBorderColorResId(borderColor);
        setScaleX(scale);
        setScaleY(scale);
        postInvalidate();
    }
}

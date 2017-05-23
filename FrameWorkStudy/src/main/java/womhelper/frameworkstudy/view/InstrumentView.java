package womhelper.frameworkstudy.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import java.text.DecimalFormat;

import womhelper.frameworkstudy.R;


/**
 * 仪表盘View
 * Created by Walkud on 17/5/22.
 */
public class InstrumentView extends ImageView {

    public static final String TAG = InstrumentView.class.getSimpleName();

    private static float MASK_ARC_START_ANGLE = -210;//原型遮罩层开始角度
    private float POINTER_START_ANGLE = -120;//指针开始角度
    private static final int SECTION = 7; // 值域（mMax-mMin）等分份数

    public static final int MODE_DELAY = 1;//延迟
    public static final int MODE_SPEEDTEST = 2;//测速

    private Bitmap dstBitmap;//低层背景图片资源
    private Bitmap srcBitmap;//上层图片资源
    private Bitmap centerPointBitmap;//中心指针点资源
    private Bitmap pointerBitmap;

    private RectF maskRect;//
    private Rect mRectText;
    private RectF mRectFArc;
    private RectF mRectFInnerArc;

    private Path mPath;

    private Paint paint;
    private Paint dialTextPaint;

    //取下层图像非交集部分
    private Xfermode xfermode;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.DST_OUT;

    private float sweepAngle = -360;//弧度
    private float pointerAngle = POINTER_START_ANGLE;//指针角度
    private int mStartAngle = 150; // 起始角度
    private int mSweepAngle = 240; // 绘制角度

    private int mRadius; // 扇形半径
    private int mPadding;
    private int mStrokeWidth; // 画笔宽度
    private int mLength1; // 长刻度的相对圆弧的长度
    private int mLength2; // 刻度读数顶部的相对圆弧的长度
    private float mCenterX, mCenterY; // 圆心坐标

    private float[] dialTexts;

    private int mode = MODE_SPEEDTEST;//动画模式

    private int speedTestRate = 1000;//测速速率

    private ObjectAnimator pointerAnim;//指针动画

    public InstrumentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InstrumentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public InstrumentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, @Nullable AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InstrumentView, 0, 0);
        //低层背景图片资源
        int dstImgResId = typedArray.getResourceId(R.styleable.InstrumentView_dst_img, 0);
        //上层图片资源
        int srcImgResId = typedArray.getResourceId(R.styleable.InstrumentView_src_img, 0);
        //中心指针点资源
        int centerPointResId = typedArray.getResourceId(R.styleable.InstrumentView_center_point_img, 0);
        //指针资源
        int pointerResId = typedArray.getResourceId(R.styleable.InstrumentView_pointer_img, 0);

        if (dstImgResId == 0 || srcImgResId == 0 || centerPointResId == 0 || pointerResId == 0) {
            throw new IllegalArgumentException(typedArray.getPositionDescription() +
                    "缺少图片资源");
        }

        dstBitmap = BitmapFactory.decodeResource(getResources(), dstImgResId);
        srcBitmap = BitmapFactory.decodeResource(getResources(), srcImgResId);
        centerPointBitmap = BitmapFactory.decodeResource(getResources(), centerPointResId);
        pointerBitmap = BitmapFactory.decodeResource(getResources(), pointerResId);

        maskRect = new RectF(0, 0, dstBitmap.getWidth(), dstBitmap.getHeight());
        mRectText = new Rect();
        mRectFArc = new RectF();
        mRectFInnerArc = new RectF();

        mPath = new Path();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);//使用抗锯齿功能
//        paint.setColor(Color.parseColor("#66FF0000"));

        dialTextPaint = new Paint();
        dialTextPaint.setAntiAlias(true);
        dialTextPaint.setColor(Color.parseColor("#007BB5"));
        dialTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mStrokeWidth = dp2px(1);
        mLength1 = dp2px(8) + mStrokeWidth;
        mLength2 = mLength1 + dp2px(2);

        xfermode = new PorterDuffXfermode(mPorterDuffMode);

        //设置默认分格值
        dialTexts = new float[SECTION];
        dialTexts[0] = 0;
        dialTexts[1] = 100;
        dialTexts[2] = 1000;
        dialTexts[3] = 2500;
        dialTexts[4] = 5000;
        dialTexts[5] = 9000;
        dialTexts[6] = 10000;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );
        setPadding(mPadding, mPadding, mPadding, mPadding);

        int width = getMeasuredWidth();
        mRadius = (width - mPadding * 2 - mStrokeWidth * 2) / 2;

        mCenterX = mCenterY = width / 2f;
        mRectFArc.set(
                getPaddingLeft() + mStrokeWidth,
                getPaddingTop() + mStrokeWidth,
                width - getPaddingRight() - mStrokeWidth,
                width - getPaddingBottom() - mStrokeWidth
        );

        dialTextPaint.setTextSize(sp2px(10));
        dialTextPaint.getTextBounds("0", 0, "0".length(), mRectText);

        mRectFInnerArc.set(
                getPaddingLeft() + mLength2 + mRectText.height(),
                getPaddingTop() + mLength2 + mRectText.height(),
                getMeasuredWidth() - getPaddingRight() - mLength2 - mRectText.height(),
                getMeasuredWidth() - getPaddingBottom() - mLength2 - mRectText.height()
        );

    }

    @Override
    protected void onDraw(Canvas canvas) {
        draw1(canvas);
    }

    private void draw1(Canvas canvas) {

        canvas.drawBitmap(dstBitmap, null, maskRect, null);
        if (mode == MODE_DELAY) {
            //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
            int saveCount = canvas.saveLayer(maskRect, paint, Canvas.ALL_SAVE_FLAG);

            canvas.drawBitmap(srcBitmap, null, maskRect, paint);

            //画遮罩
            paint.setXfermode(xfermode);
            canvas.drawArc(maskRect, MASK_ARC_START_ANGLE, sweepAngle, true, paint);//画弧

            //清除混合模式
            paint.setXfermode(null);
            //还原画布
            canvas.restoreToCount(saveCount);

            drawDialText(canvas);

            canvas.drawBitmap(centerPointBitmap, 0, 0, null);

        } else {

            drawDialText(canvas);

            drawPointerBitmap(canvas, pointerAngle);
        }


    }

    /**
     * 绘制指针
     *
     * @param canvas
     * @param rotation 旋转度数
     */
    private void drawPointerBitmap(Canvas canvas,
                                   float rotation) {
        Matrix matrix = new Matrix();
        int offsetX = pointerBitmap.getWidth() / 2;
        int offsetY = pointerBitmap.getHeight() / 2;
        matrix.postTranslate(-offsetX, -offsetY);
        matrix.postRotate(rotation);
        matrix.postTranslate(offsetX, offsetY);
        canvas.drawBitmap(pointerBitmap, matrix, null);
    }

    /**
     * 画长刻度读数
     */
    private void drawDialText(Canvas canvas) {
        dialTextPaint.setTextSize(sp2px(10));
        dialTextPaint.setStyle(Paint.Style.FILL);
        float α;
        float[] p;
        for (int i = 0; i < SECTION; i++) {

            //计算每个刻度初始角度
            switch (i) {
                case 0:
                    α = mStartAngle;
                    break;
                case SECTION - 1:
                    α = mStartAngle + 240;
                    break;
                default:
                    α = mStartAngle + 30 + (45 * (i - 1));
                    break;
            }

            float value = dialTexts[i];
            String dialTexts = formatDecimal1(value) + "Mbps";
            //将角度转换为坐标
            p = getCoordinatePoint(mRadius - dp2px(30), α);
            //获取文本宽高
            dialTextPaint.getTextBounds(dialTexts, 0, dialTexts.length(), mRectText);

            //修正对应坐标的位置
            if (i <= 1) {
                p[0] = p[0] - dp2px(8);
                p[1] = p[1] + mRectText.height() / 2;
            } else if (i == 2) {
                p[0] = p[0] - dp2px(5);
                p[1] = p[1] + mRectText.height() / 2;
            } else if (i == 3) {
                p[0] = p[0] - mRectText.width() / 2;
            } else if (i == 4) {
                p[0] = p[0] - mRectText.width() + dp2px(5);
                p[1] = p[1] + mRectText.height() / 2;
            } else {
                p[0] = p[0] - mRectText.width() + dp2px(8);
                p[1] = p[1] + mRectText.height() / 2;
            }

            //画刻度
            canvas.drawText(dialTexts, p[0], p[1], dialTextPaint);
        }
    }

    private int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    /**
     * 开始延迟动画模式
     *
     * @param duration 测试延迟时间(单位秒)
     */
    public void startDelayAnim(int duration) {
        mode = MODE_DELAY;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "sweepAngle", -360, -120).setDuration(duration * 1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                postInvalidate();
            }
        });
        animator.start();
    }

    /**
     * 开始测速动画
     */
    public void startSpeedTestAnim(float value) {
        mode = MODE_SPEEDTEST;

        cancelPointerAnim();

        float angle = calculateAngle(value);

        Log.d(TAG, "pointerAngle:" + pointerAngle + ",value:" + value + ",angle:" + angle);

        pointerAnim = ObjectAnimator.ofFloat(this, "pointerAngle", pointerAngle, angle).setDuration(900);
        pointerAnim.setInterpolator(new DecelerateInterpolator());
        pointerAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                postInvalidate();
            }
        });
        pointerAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cancelPointerAnim();
            }
        });

        pointerAnim.start();
    }

    /**
     * 取消指针动画
     */
    private void cancelPointerAnim() {
        if (pointerAnim != null) {
            pointerAnim.cancel();
            pointerAnim = null;
        }
    }

    /**
     * 设置弧度
     *
     * @param sweepAngle
     */
    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    /**
     * 设置指针角度
     *
     * @param pointerAngle
     */
    public void setPointerAngle(float pointerAngle) {
        this.pointerAngle = pointerAngle;
    }

    /**
     * 将角度转换为坐标
     *
     * @param radius
     * @param angle
     * @return
     */
    public float[] getCoordinatePoint(int radius, float angle) {
        float[] point = new float[2];
        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
        point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        return point;
    }

    /**
     * 计算测速结果角度
     *
     * @param value 速率
     * @return
     */
    private float calculateAngle(float value) {
        float angle = POINTER_START_ANGLE;
        //每一格的角度最小单位计算出来，再乘以速率值 ，再加上前面格子的角度值之和
        if (value <= 100) {
            angle += ((float) 30 / 100) * value;
        } else if (value <= 1000) {
            angle += ((float) 45 / 1000) * value + 30;
        } else if (value <= 2500) {
            angle += ((float) 45 / 2500) * value + 30 + 45;
        } else if (value <= 5000) {
            angle += ((float) 45 / 5000) * value + 30 + 45 + 45;
        } else if (value <= 9000) {
            angle += ((float) 45 / 9000) * value + 30 + 45 + 45 + 45;
        } else {
            angle += ((float) 30 / 10000) * value + 30 + 45 + 45 + 45 + 45;
        }

        return angle;
    }

    /**
     * 保留小数位1位
     *
     * @return
     */
    public static String formatDecimal1(double value) {
        DecimalFormat df = new DecimalFormat("0.#");
        return df.format(value);
    }
}

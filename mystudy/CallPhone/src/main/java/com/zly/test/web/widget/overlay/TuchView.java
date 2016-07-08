package com.zly.test.web.widget.overlay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * 透明TuchView
 */
public class TuchView extends View {

    private static Context mContext;

    private static final String TAG = "TuchView";
    protected static final Object monitor = new Object();
    public static View mOverlay;

    private static TuchViewClickListener mListener;

    public TuchView(Context context) {
        super(context);
    }

    /**
     * 获取显示参数
     *
     * @return
     */
    private static WindowManager.LayoutParams getShowingParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = 1;
        params.height = 1;
        params.gravity = 51;
        params.x = 0;
        params.y = 0;
        params.format = PixelFormat.RGBA_8888;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;

        return params;
    }

    public static void init(Context context, TuchViewClickListener listener) {

        if (mOverlay == null) {
            mOverlay = new TuchView(context);
            mOverlay.setBackgroundColor(0);
        }

        mListener = listener;

        show(context);

    }

    /**
     * 显示
     *
     * @param context 上下文对象
     */
    private static void show(final Context context) {
        mContext = context;

        synchronized (monitor) {
            WindowManager wm = (WindowManager) context.getApplicationContext()
                    .getSystemService(Context.WINDOW_SERVICE);

            wm.addView(mOverlay, getShowingParams());

        }
    }

    /**
     * 隐藏
     *
     * @param context
     */
    public static void hide(Context context) {
        synchronized (monitor) {
            if (mOverlay != null) {
                try {
                    WindowManager wm = (WindowManager) context
                            .getSystemService(Context.WINDOW_SERVICE);
                    wm.removeView(mOverlay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mOverlay = null;
            }
        }
    }

    /**
     * 监听点击事件
     */
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "action:" + event.getAction());

        if (mListener != null) {
            mListener.tuchClick();
        }

        return super.onTouchEvent(event);
    }

    /**
     * 屏幕点击事件
     */
    public interface TuchViewClickListener {

        void tuchClick();

    }
}

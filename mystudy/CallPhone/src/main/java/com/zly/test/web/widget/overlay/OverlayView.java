package com.zly.test.web.widget.overlay;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.zly.test.web.R;

/**
 * 半屏显示
 */
public class OverlayView extends Overlay {

    /**
     * 来电号码extra
     */
    public static final String EXTRA_PHONE_NUM = "phoneNum";

    private static Context mContext;

    /**
     * 显示来电接管
     *
     * @param context 上下文对象
     * @param number
     */
    public static void showInCall(final Context context, final String number,
                                  String name, final int percentScreen) {
        mContext = context;

        synchronized (monitor) {

            init(context, number, name, R.layout.in_call_layout, percentScreen);
        }
    }

    /**
     * 显示去电接管
     *
     * @param context 上下文对象
     * @param number
     */
    public static void showOutCall(final Context context, final String number,
                                   String name, final int percentScreen) {
        mContext = context;

        synchronized (monitor) {
            init(context, number, name, R.layout.out_call_layout, percentScreen);
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
                    // Remove view from WindowManager
                    wm.removeView(mOverlay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mOverlay = null;
            }
        }
    }

    /**
     * 初始化布局
     *
     * @param context 上下文对象
     * @param number  电话号码
     * @param layout  布局文件
     * @return 布局
     */
    private static ViewGroup init(Context context, String number, String name,
                                  int layout, int percentScreen) {
        WindowManager.LayoutParams params = getShowingParams();
        int height = getHeight(context, percentScreen);
        params.height = height;
        OverlayLayoutView overlay = init(context, params);

        switch (layout) {
            case R.layout.out_call_layout:// 初始化去电界面
                initCallView(overlay, number, name);
                break;
            case R.layout.in_call_layout:// 初始化来电界面
                initCallView(overlay, number, name);
                break;
        }

        return overlay;
    }

    // /**
    // * 初始化去电界面
    // */
    // private static void initOutCallView(View v, String phoneNum,
    // int percentScreen) {
    //
    // final EditText phoneNumberET = (EditText) v
    // .findViewById(R.id.phone_number_edit);
    // Button outCallBtn = (Button) v.findViewById(R.id.out_call_btn);
    // outCallBtn.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View arg0) {
    // String phoneNumber = phoneNumberET.getText().toString();
    // Intent intent = new Intent();
    // intent.setAction(Intent.ACTION_CALL);
    // intent.setData(Uri.parse("tel:" + phoneNumber));
    // mContext.startActivity(intent);
    // }
    // });
    // }

    /**
     * 初始化来电界面
     */
    private static void initCallView(OverlayLayoutView v, String phoneNum, String name) {
        v.setPhoneNumber(phoneNum);
        v.setNameText(name);
        v.setCloseClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hide(mContext);
            }
        });
        v.setActionBtn1ClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // answerRingingCall(mContext);
                Toast.makeText(mContext, "功能1", Toast.LENGTH_SHORT).show();
            }
        });
        v.setActionBtn2ClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // endCall(mContext);
                Toast.makeText(mContext, "功能2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取显示参数
     *
     * @return
     */
    private static WindowManager.LayoutParams getShowingParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.x = 0;
        params.y = getStatusBarHeight();
        params.format = PixelFormat.RGBA_8888;// value = 1
        params.gravity = Gravity.TOP;

        //如何flags使用WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,监听不了onKey事件
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        return params;
    }

    /**
     * 获取界面显示的高度 ，默认为手机高度的2/3
     *
     * @param context 上下文对象
     * @return
     */
    private static int getHeight(Context context, int percentScreen) {
        return getLarger(context) * percentScreen / 100;
    }

    @SuppressWarnings("deprecation")
    private static int getLarger(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int height = 0;
        if (hasHoneycombMR2()) {
            height = getLarger(display);
        } else {
            height = display.getHeight() > display.getWidth() ? display
                    .getHeight() : display.getWidth();
        }
        Log.d("OverlayView", "getLarger: " + height);
        return height;
    }

    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private static int getLarger(Display display) {
        Point size = new Point();
        display.getSize(size);
        return size.y > size.x ? size.y : size.x;
    }

    /**
     * 关闭输入法
     */
    public static void closeEditer(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 获取顶部状态Bar高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        return Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height",
                        "dimen", "android"));
    }
}

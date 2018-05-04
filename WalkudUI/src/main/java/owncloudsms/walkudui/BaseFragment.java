package owncloudsms.walkudui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shizhefei.fragment.LazyFragment;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 * Created by Walkud on 16/9/21.
 */
public abstract class BaseFragment extends LazyFragment {

    protected Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(getLyaoutId());
        unbinder = ButterKnife.bind(this, getContentView());
        initData();
        addListener();
    }

    /**
     * 获取布局Id
     *
     * @return
     */
    public abstract int getLyaoutId();

    /**
     * 初始化
     */
    public abstract void initData();

    /**
     * 添加事件
     */
    public abstract void addListener();

    /**
     * 显示Toast提示
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        unbinder.unbind();
    }

    /**
     * 设置沉侵式顶部状态栏间距
     *
     * @param view
     */
    public void steepStatusBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            view.getLayoutParams().height += statusBarHeight;
            view.setPadding(view.getPaddingLeft(), statusBarHeight,
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dip2px(25);
    }

    protected void toIntent(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}

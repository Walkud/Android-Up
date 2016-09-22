package owncloudsms.walkudui;

import android.os.Bundle;
import android.widget.Toast;

import com.shizhefei.fragment.LazyFragment;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * Created by Walkud on 16/9/21.
 */
public abstract class BaseFragment extends LazyFragment {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(getLyaoutId());
        ButterKnife.bind(this, getContentView());
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

}

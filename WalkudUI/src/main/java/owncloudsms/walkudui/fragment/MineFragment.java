package owncloudsms.walkudui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import owncloudsms.walkudui.BaseFragment;
import owncloudsms.walkudui.R;

/**
 * 我的
 * Created by Walkud on 16/9/22.
 */
public class MineFragment extends BaseFragment {
    private String tabName;

    @Bind(R.id.fragment_text)
    TextView textView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
    }

    @Override
    public int getLyaoutId() {
        return R.layout.fragment_tabmain;
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        tabName = bundle.getString("intent_String_tabname");
        textView.setText(tabName);
    }

    @Override
    public void addListener() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(tabName + "点击事件");
            }
        });
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        Log.d("cccc", "Fragment所在的Activity onResume, onResumeLazy " + this);
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        Log.d("cccc", "Fragment 显示 " + this);
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        Log.d("cccc", "Fragment 掩藏 " + this);
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        Log.d("cccc", "Fragment所在的Activity onPause, onPauseLazy " + this);
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        Log.d("cccc", "Fragment View将被销毁 " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cccc", "Fragment 所在的Activity onDestroy " + this);
    }

}

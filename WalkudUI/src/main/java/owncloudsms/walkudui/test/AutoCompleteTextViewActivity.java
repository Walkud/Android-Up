package owncloudsms.walkudui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import owncloudsms.walkudui.BaseActivity;
import owncloudsms.walkudui.R;

public class AutoCompleteTextViewActivity extends BaseActivity {

    @BindView(R.id.autotext)
    AutoCompleteTextView autotext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_auto_complete_text_view;
    }

    @Override
    public void initData() {
        //设置数据源
        String[] autoStrings = new String[]{"马卡龙", "马达", "卡募集", "扩大开放", "马达", "马达", "马达", "马达", "马达", "马达", "马达", "马达", "马达", "马达", "马达", "马达"};
        //设置ArrayAdapter，并且设定以单行下拉列表风格展示（第二个参数设定）。
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.cell_drop_down_list_item, autoStrings);
        //设置AutoCompleteTextView的Adapter
        autotext.setAdapter(adapter);
    }

    @Override
    public void addListener() {

    }
}

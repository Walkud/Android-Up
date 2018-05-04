package com.walkud.self.module.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;
import com.walkud.self.module.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutoCompleteTextViewActivity extends BaseActivity {

    @BindView(R.id.autotext)
    AutoCompleteTextView autotext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete_text_view);
        ButterKnife.bind(this);

        //设置数据源
        String[] autoStrings = new String[]{"New York", "Tokyo", "beijing", "london", "Seoul Special", "Los Angeles"};
        //设置ArrayAdapter，并且设定以单行下拉列表风格展示（第二个参数设定）。
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, autoStrings);
        //设置AutoCompleteTextView的Adapter
        autotext.setAdapter(adapter);
    }
}

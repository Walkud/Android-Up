package com.walkud.self.module.i18n;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 国际化
 * Created by Walkud on 18/3/2.
 */

public class I18nActivity extends BaseActivity {

    @BindView(R.id.switch_language)
    Button switchLanguage;
    @BindView(R.id.language_text)
    TextView languageText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i18n);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.switch_language, R.id.language_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_language://切换
                switchLanguage();
                break;
            case R.id.language_text:
                toIntent(I18nActivity.class);
                break;
        }
    }

    /**
     * 切换国际化
     */
    private void switchLanguage() {
        Configuration configuration = getResources().getConfiguration();
        if (Locale.SIMPLIFIED_CHINESE == configuration.locale) {
            configuration.locale = Locale.ENGLISH;
        } else {
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }

        //此处更新，进程被杀后就会失效，可以采用缓存，下次加载的时候在Application中进行更新
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        recreate();
    }
}

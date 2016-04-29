package com.walkud.self.module;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.walkud.self.R;
import com.walkud.self.module.sqllite.realm.RealmMainActivity;
import com.walkud.self.module.stetho.StethoActivity;
import com.walkud.self.module.survive.SurviveActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.realmMainBtn)
    Button realmMainBtn;
    @Bind(R.id.surviveBtn)
    Button surviveBtn;
    @Bind(R.id.stethoBtn)
    Button stethoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        realmMainBtn.setOnClickListener(this);
        surviveBtn.setOnClickListener(this);
        stethoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.realmMainBtn:
                toIntent(RealmMainActivity.class);
                break;
            case R.id.surviveBtn:
                toIntent(SurviveActivity.class);
                break;
            case R.id.stethoBtn:
                toIntent(StethoActivity.class);
                break;
        }
    }
}

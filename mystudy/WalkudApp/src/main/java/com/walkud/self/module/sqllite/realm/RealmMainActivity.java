package com.walkud.self.module.sqllite.realm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jan on 16/3/30.
 */
public class RealmMainActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.basisRealmBtn)
    Button basisRealmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_realm_main);
        ButterKnife.bind(this);

        basisRealmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.basisRealmBtn:
                toIntent(BasisRealmActivity.class);
                break;
        }
    }
}

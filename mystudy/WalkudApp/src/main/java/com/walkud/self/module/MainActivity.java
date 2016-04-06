package com.walkud.self.module;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.walkud.self.R;
import com.walkud.self.module.sqllite.realm.RealmMainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.realmMainBtn)
    Button realmMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        realmMainBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.realmMainBtn:
                toIntent(RealmMainActivity.class);
                break;
        }
    }
}

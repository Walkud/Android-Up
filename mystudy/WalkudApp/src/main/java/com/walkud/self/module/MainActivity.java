package com.walkud.self.module;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.walkud.self.R;
import com.walkud.self.module.bluetooth.BluetoothActivity;
import com.walkud.self.module.i18n.I18nActivity;
import com.walkud.self.module.messenger.MessengerActivity;
import com.walkud.self.module.pagergrid.paper.RecyclerViewActivity;
import com.walkud.self.module.qrc.ScanQrcActivity;
import com.walkud.self.module.share.IntentShareActivity;
import com.walkud.self.module.sqllite.realm.RealmMainActivity;
import com.walkud.self.module.stetho.StethoActivity;
import com.walkud.self.module.survive.SurviveActivity;
import com.walkud.self.module.transparent.TransparentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.realmMainBtn)
    Button realmMainBtn;
    @Bind(R.id.surviveBtn)
    Button surviveBtn;
    @Bind(R.id.stethoBtn)
    Button stethoBtn;
    @Bind(R.id.transparentBtn)
    Button transparentBtn;
    @Bind(R.id.bluetoothBtn)
    Button bluetoothBtn;
    @Bind(R.id.shareBtn)
    Button shareBtn;
    @Bind(R.id.scanQrcBtn)
    Button scanQrcBtn;
    @Bind(R.id.roundView)
    Button roundView;
    @Bind(R.id.messengerView)
    Button messengerView;
    @Bind(R.id.i18nView)
    Button i18nView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        realmMainBtn.setOnClickListener(this);
        surviveBtn.setOnClickListener(this);
        stethoBtn.setOnClickListener(this);
        transparentBtn.setOnClickListener(this);
        bluetoothBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        scanQrcBtn.setOnClickListener(this);
        roundView.setOnClickListener(this);
        messengerView.setOnClickListener(this);
        i18nView.setOnClickListener(this);
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
            case R.id.transparentBtn:
                toIntent(TransparentActivity.class);
                break;
            case R.id.bluetoothBtn:
                toIntent(BluetoothActivity.class);
                break;
            case R.id.shareBtn:
                toIntent(IntentShareActivity.class);
                break;
            case R.id.scanQrcBtn:
                toIntent(ScanQrcActivity.class);
                break;
            case R.id.roundView:
                toIntent(RecyclerViewActivity.class);
                break;
            case R.id.messengerView:
                toIntent(MessengerActivity.class);
                break;
            case R.id.i18nView:
                toIntent(I18nActivity.class);
                break;
        }
    }

}

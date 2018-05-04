package com.walkud.self.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.walkud.self.R;
import com.walkud.self.module.bluetooth.BluetoothActivity;
import com.walkud.self.module.i18n.I18nActivity;
import com.walkud.self.module.messenger.MessengerActivity;
import com.walkud.self.module.pagergrid.paper.RecyclerViewActivity;
import com.walkud.self.module.qrc.ScanQrcActivity;
import com.walkud.self.module.share.IntentShareActivity;
import com.walkud.self.module.stetho.StethoActivity;
import com.walkud.self.module.survive.SurviveActivity;
import com.walkud.self.module.transparent.TransparentActivity;
import com.walkud.self.module.view.AutoCompleteTextViewActivity;
import com.walkud.self.mvp.ui.NavActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.testViewBtn)
    Button testViewBtn;
    @BindView(R.id.stethoBtn)
    Button stethoBtn;
    @BindView(R.id.surviveBtn)
    Button surviveBtn;
    @BindView(R.id.transparentBtn)
    Button transparentBtn;
    @BindView(R.id.bluetoothBtn)
    Button bluetoothBtn;
    @BindView(R.id.shareBtn)
    Button shareBtn;
    @BindView(R.id.scanQrcBtn)
    Button scanQrcBtn;
    @BindView(R.id.roundView)
    Button roundView;
    @BindView(R.id.messengerView)
    Button messengerView;
    @BindView(R.id.i18nView)
    Button i18nView;
    @BindView(R.id.kotlinMvp)
    Button kotlinMvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        testViewBtn.setOnClickListener(this);
        surviveBtn.setOnClickListener(this);
        stethoBtn.setOnClickListener(this);
        transparentBtn.setOnClickListener(this);
        bluetoothBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        scanQrcBtn.setOnClickListener(this);
        roundView.setOnClickListener(this);
        messengerView.setOnClickListener(this);
        i18nView.setOnClickListener(this);
        kotlinMvp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.testViewBtn:
                toIntent(AutoCompleteTextViewActivity.class);
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
            case R.id.kotlinMvp:
                toIntent(NavActivity.class);
                break;
        }
    }

    protected void toIntent(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


}

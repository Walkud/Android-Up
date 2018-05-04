package com.walkud.jam.module.makeapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.walkud.jam.R;
import com.walkud.jam.module.BaseActivity;
import com.walkud.jam.module.makeapp.a.BuyGoodsActivity;
import com.walkud.jam.module.makeapp.a.ObjectActivity;
import com.walkud.jam.module.makeapp.p2.BasketBallActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jan on 16/3/29.
 */
public class MakeAppActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.buyGoodsBtn)
    Button buyGoodsBtn;
    @BindView(R.id.basketBallBtn)
    Button BasketBallBtn;
    @BindView(R.id.objectBtn)
    Button objectBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_app);

        ButterKnife.bind(this);

        buyGoodsBtn.setOnClickListener(this);
        BasketBallBtn.setOnClickListener(this);
        objectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.buyGoodsBtn:
                toIntent(BuyGoodsActivity.class);
                break;
            case R.id.basketBallBtn:
                toIntent(BasketBallActivity.class);
                break;
            case R.id.objectBtn:
                toIntent(ObjectActivity.class);
                break;
        }
    }
}

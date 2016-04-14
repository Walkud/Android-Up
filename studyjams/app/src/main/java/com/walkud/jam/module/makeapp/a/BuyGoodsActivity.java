package com.walkud.jam.module.makeapp.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.walkud.jam.R;
import com.walkud.jam.module.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jan on 16/4/6.
 */
public class BuyGoodsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.plusBtn)
    Button plusBtn;
    @Bind(R.id.quantityTV)
    TextView quantityTV;
    @Bind(R.id.minusBtn)
    Button minusBtn;
    @Bind(R.id.priceTV)
    TextView priceTV;
    @Bind(R.id.thinkyouTV)
    TextView thinkyouTV;
    @Bind(R.id.orderBtn)
    Button orderBtn;

    private static final int UNIT_PRICE = 399;//单价

    private int quantity = 0;//数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_goods);
        ButterKnife.bind(this);


        plusBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        orderBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.plusBtn://加
                quantity++;
                quantityTV.setText(String.valueOf(quantity));
                priceTV.setText("￥" + 0);
                break;
            case R.id.minusBtn://减
                quantity--;
                quantityTV.setText(String.valueOf(quantity));
                priceTV.setText("￥" + 0);
                break;
            case R.id.orderBtn://下单
                thinkyouTV.setText("ThinkYou!");
                priceTV.setText("￥" + quantity * UNIT_PRICE);
                break;
        }

    }
}

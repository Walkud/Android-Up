package com.walkud.jam.module.makeapp.a;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.walkud.jam.R;
import com.walkud.jam.module.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jan on 16/4/6.
 */
public class BuyGoodsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.plusBtn)
    Button plusBtn;
    @BindView(R.id.quantityTV)
    TextView quantityTV;
    @BindView(R.id.minusBtn)
    Button minusBtn;
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.thinkyouTV)
    TextView thinkyouTV;
    @BindView(R.id.orderBtn)
    Button orderBtn;
    @BindView(R.id.adidasCB)
    CheckBox adidasCB;
    @BindView(R.id.nikeCB)
    CheckBox nikeCB;
    @BindView(R.id.feedbackTV)
    TextView feedbackTV;
    @BindView(R.id.feedbackET)
    EditText feedbackET;
    @BindView(R.id.emailToBtn)
    Button emailToBtn;

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
        emailToBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.plusBtn://加
                quantity++;
                quantityTV.setText(String.valueOf(quantity));
                priceTV.setText("￥" + 0);
                feedbackTV.setVisibility(View.GONE);
                feedbackET.setVisibility(View.GONE);
                break;
            case R.id.minusBtn://减
                quantity--;
                quantityTV.setText(String.valueOf(quantity));
                priceTV.setText("￥" + 0);
                feedbackTV.setVisibility(View.GONE);
                feedbackET.setVisibility(View.GONE);
                break;
            case R.id.orderBtn://下单
                if (quantity == 0) {
                    Toast.makeText(this, "请先选择数量!", Toast.LENGTH_LONG).show();
                } else {

                    StringBuilder sb = new StringBuilder("ThinkYou!\n");
                    if (adidasCB.isChecked()) {
                        sb.append("喜欢adidas品牌\n");
                    }
                    if (nikeCB.isChecked()) {
                        sb.append("喜欢Nike品牌\n");
                    }

                    thinkyouTV.setText(sb.toString());
                    priceTV.setText("￥" + quantity * UNIT_PRICE);

                    feedbackTV.setVisibility(View.VISIBLE);
                    feedbackET.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.emailToBtn:

                if (feedbackET.getVisibility() == View.GONE) {
                    Toast.makeText(this, "请先购买!", Toast.LENGTH_LONG).show();
                } else {

                    String msg = feedbackET.getText().toString();

                    emailTo("football", msg);
                }
                break;
        }

    }

    /**
     * 发送邮件
     *
     * @param name
     * @param priceMsg
     */
    private void emailTo(String name, String priceMsg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:Walkud@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Buy goods " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMsg);
        startActivity(intent);
    }
}

package cn.studyjams.s1.sj10.zhuliya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 风景详情介绍界面
 */
public class DetailedActivity extends AppCompatActivity {

    private ImageView detailedImageView;
    private TextView introductionTextView;
    private TextView aspectTextView;
    private TextView openTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        bindView();
        updateUi();
    }

    /**
     * 绑定视图
     */
    private void bindView() {
        detailedImageView = (ImageView) findViewById(R.id.detailedImageView);
        introductionTextView = (TextView) findViewById(R.id.introductionTextView);
        aspectTextView = (TextView) findViewById(R.id.aspectTextView);
        openTimeTextView = (TextView) findViewById(R.id.openTimeTextView);
    }

    /**
     * 更新UI
     */
    private void updateUi() {
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        int imgRes = intent.getIntExtra("imgRes", 0);
        String introduction = intent.getStringExtra("introduction");
        String aspect = intent.getStringExtra("aspect");
        String openTime = intent.getStringExtra("openTime");

        //设置Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(name);

        if (imgRes != 0) {
            //图片资源id不为0设置图片
            detailedImageView.setImageResource(imgRes);
        }

        introductionTextView.setText(introduction);
        aspectTextView.setText(aspect);
        openTimeTextView.setText(openTime);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home://返回按钮
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

package cn.studyjams.s1.sj10.zhuliya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private Button introductionBtn;
    private Button historyBtn;
    private Button trafficBtn;
    private Button tourismBtn;
    private Button economicBtn;
    private Button sceneryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
    }

    /**
     * 绑定View
     */
    private void bindView() {
        introductionBtn = (Button) findViewById(R.id.introductionBtn);
        historyBtn = (Button) findViewById(R.id.historyBtn);
        trafficBtn = (Button) findViewById(R.id.trafficBtn);
        tourismBtn = (Button) findViewById(R.id.tourismBtn);
        economicBtn = (Button) findViewById(R.id.economicBtn);
        sceneryBtn = (Button) findViewById(R.id.sceneryBtn);

        //简介
        introductionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(IntroductionActivity.class);
            }
        });

        //历史文化
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(HistoryActivity.class);
            }
        });

        //交通
        trafficBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(TrafficActivity.class);
            }
        });

        //旅游资源
        tourismBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(TourismActivity.class);
            }
        });

        //经济
        economicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(EconomicActivity.class);
            }
        });

        //风景图
        sceneryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDetailedActivity(SceneryActivity.class);
            }
        });
    }

    /**
     * 跳转界面
     */
    private void jumpDetailedActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

}

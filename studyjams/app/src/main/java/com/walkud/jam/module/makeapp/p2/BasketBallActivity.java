package com.walkud.jam.module.makeapp.p2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.walkud.jam.R;
import com.walkud.jam.module.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 篮球计分器
 * Created by jan on 16/4/12.
 */
public class BasketBallActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.teamScoreA)
    TextView teamScoreA;
    @BindView(R.id.fa3Btn)
    Button fa3Btn;
    @BindView(R.id.fa2Btn)
    Button fa2Btn;
    @BindView(R.id.ftaBtn)
    Button ftaBtn;
    @BindView(R.id.teamScoreB)
    TextView teamScoreB;
    @BindView(R.id.fb3Btn)
    Button fb3Btn;
    @BindView(R.id.fb2Btn)
    Button fb2Btn;
    @BindView(R.id.ftbBtn)
    Button ftbBtn;
    @BindView(R.id.resetBtn)
    Button resetBtn;

    int scoreTeamA = 0;
    int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basketball);
        ButterKnife.bind(this);


        calculateForTeamA(scoreTeamA);
        calculateForTeamB(scoreTeamB);

        fa3Btn.setOnClickListener(this);
        fa2Btn.setOnClickListener(this);
        ftaBtn.setOnClickListener(this);
        fb3Btn.setOnClickListener(this);
        fb2Btn.setOnClickListener(this);
        ftbBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }

    public void addTeamA(int score) {
        scoreTeamA = scoreTeamA + score;
        calculateForTeamA(scoreTeamA);
    }

    public void calculateForTeamA(int score) {
        teamScoreA.setText(String.valueOf(score));
    }


    public void addTeamB(int score) {
        scoreTeamB = scoreTeamB + score;
        calculateForTeamB(scoreTeamB);
    }

    public void calculateForTeamB(int score) {
        teamScoreB.setText(String.valueOf(score));
    }

    /**
     * 重置
     */
    public void reset() {
        scoreTeamA = 0;
        scoreTeamB = 0;
        calculateForTeamA(scoreTeamA);
        calculateForTeamB(scoreTeamB);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fa3Btn:
                addTeamA(3);
                break;
            case R.id.fa2Btn:
                addTeamA(2);
                break;
            case R.id.ftaBtn:
                addTeamA(1);
                break;
            case R.id.fb3Btn:
                addTeamB(3);
                break;
            case R.id.fb2Btn:
                addTeamB(2);
                break;
            case R.id.ftbBtn:
                addTeamB(1);
                break;
            case R.id.resetBtn:
                reset();
                break;
        }
    }
}

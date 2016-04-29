package com.walkud.self.module.stetho;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.walkud.self.App;
import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;
import com.walkud.self.module.stetho.db.SdkDbActivity;

import java.io.IOException;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jan on 16/4/29.
 */
public class StethoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.stetho_zengjia)
    Button zengjia;
    @Bind(R.id.stetho_get_sp)
    Button modifyBtn;
    @Bind(R.id.stetho_toSdkDb)
    Button stethoToSdkDb;
    SharedPreferences sp;
    @Bind(R.id.stetho_okhttp)
    Button stethoOkhttp;

    private String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stetho);
        ButterKnife.bind(this);
        sp = getSharedPreferences("WalkudApp", Context.MODE_APPEND);

        zengjia.setOnClickListener(this);
        modifyBtn.setOnClickListener(this);
        stethoToSdkDb.setOnClickListener(this);
        stethoOkhttp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.stetho_zengjia:
                key = "Stetho" + new Random().nextInt(1000);
                sp.edit().putString(key, key).commit();
                break;
            case R.id.stetho_get_sp:
                Toast.makeText(this, "key:" + sp.getString(key, "为空"), Toast.LENGTH_LONG).show();
                break;
            case R.id.stetho_toSdkDb:
                toIntent(SdkDbActivity.class);
                break;
            case R.id.stetho_okhttp:

                new Thread() {
                    @Override
                    public void run() {
                        OkHttpClient client = App.getInstance().getOkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://www.baidu.com")
                                .build();

                        try {
                            Response response = client.newCall(request).execute();
                            System.out.println(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


                break;
        }
    }
}

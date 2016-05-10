package com.walkud.self.module.stetho.db;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * SdkDb
 */
public class SdkDbActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SdkDbActivity.class.getSimpleName();

    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.button5)
    Button button5;
    @Bind(R.id.list1)
    ListView list1;

    SdkDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stetho_db);
        ButterKnife.bind(this);
        db = new SdkDbHelper(this);

        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void queryData() {
        Cursor cursor = db.select();
        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{SdkDbHelper.TITLE},
                new int[]{android.R.id.text1});
        list1.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button2:// 插入
                db.insert("the one");
                break;
            case R.id.button3:// 更新
                db.update("the two");
                break;
            case R.id.button4:// 删除
                db.delete(null);
                queryData();
                break;
            case R.id.button5:// 查询
                queryData();
                break;
        }
    }
}

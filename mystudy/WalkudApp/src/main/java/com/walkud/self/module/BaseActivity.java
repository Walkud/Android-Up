package com.walkud.self.module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jan on 16/3/30.
 */
public class BaseActivity extends AppCompatActivity {


    protected void toIntent(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}

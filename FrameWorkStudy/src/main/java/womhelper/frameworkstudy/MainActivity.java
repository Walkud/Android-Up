package womhelper.frameworkstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import womhelper.frameworkstudy.utils.MLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v) {

        MLog.d("d(msg)");
        MLog.d("d(msg,args:%s)", "arg");
        MLog.i("i(msg,args:%s)", "arg");
        MLog.w("w(msg,args:%s)", "arg");
        MLog.e("e(msg,args:%s)", "arg");
        MLog.e(new Throwable("MLog.e()"), "e(Throwable,msg,args:%s)", "arg");
        MLog.v("v(msg,args:%s)", "arg");
        MLog.json("{\"name\":\"Logger\",\"age\":18}");
        MLog.wtf("wtf(msg,args:%s)", "arg");
        MLog.xml("<a><b>BB<b><c>CC</c></a>");

    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

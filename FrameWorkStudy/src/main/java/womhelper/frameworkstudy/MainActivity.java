package womhelper.frameworkstudy;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import womhelper.frameworkstudy.utils.MLog;
import womhelper.frameworkstudy.view.InstrumentView;

public class MainActivity extends AppCompatActivity {

    InstrumentView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedTest();

        view = (InstrumentView) findViewById(R.id.instrument_view);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                view.startDelayAnim(2);
//                speedTest();
//            }
//        }, 1000);
    }

    private void speedTest() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.startSpeedTestAnim(getRate());

                if (!isFinishing()) {
                    speedTest();
                }

            }
        }, 1000);
    }

    int[] values = {0, 100, 1000, 2500, 5000, 9000, 10000};

    private int getRate() {
        Random random = new Random();
        return random.nextInt(1000);
//        return values[random.nextInt(7)];
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
        MLog.xml("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n" +
                "\n" +
                "    <attr name=\"main_menu_changetheme\" format=\"string\" />\n" +
                "\n" +
                "    <attr name=\"myTheme\" format=\"integer\">\n" +
                "        <enum name=\"day\" value=\"0\" />\n" +
                "        <enum name=\"night\" value=\"1\" />\n" +
                "    </attr>\n" +
                "\n" +
                "</resources>");

    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

package cn.studyjams.s1.sj10.zhuliya.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.studyjams.s1.sj10.zhuliya.Configs;
import cn.studyjams.s1.sj10.zhuliya.R;
import cn.studyjams.s1.sj10.zhuliya.bean.Idiom;
import cn.studyjams.s1.sj10.zhuliya.bean.IdiomRealm;
import cn.studyjams.s1.sj10.zhuliya.bean.IdiomResult;
import cn.studyjams.s1.sj10.zhuliya.common.event.IdiomEvent;
import cn.studyjams.s1.sj10.zhuliya.net.IdiomRetrofit;
import cn.studyjams.s1.sj10.zhuliya.net.api.IdiomApi;
import cn.studyjams.s1.sj10.zhuliya.utils.GsonUtil;
import cn.studyjams.s1.sj10.zhuliya.view.ScrollGridView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 成语详情界面
 * Created by jan on 16/4/25.
 */
public class DetailedActivity extends BaseActivity {

    private static final String TAG = DetailedActivity.class.getSimpleName();

    @BindView(R.id.bushouTV)
    TextView bushouTV;
    @BindView(R.id.headTV)
    TextView headTV;
    @BindView(R.id.pinyinTV)
    TextView pinyinTV;
    @BindView(R.id.chengyujsTV)
    TextView chengyujsTV;
    @BindView(R.id.from_TV)
    TextView fromTV;
    @BindView(R.id.exampleTV)
    TextView exampleTV;
    @BindView(R.id.yufaTV)
    TextView yufaTV;
    @BindView(R.id.ciyujsTV)
    TextView ciyujsTV;
    @BindView(R.id.yinzhengjsTV)
    TextView yinzhengjsTV;
    @BindView(R.id.tongyiGV)
    ScrollGridView tongyiGV;
    @BindView(R.id.fanyiGV)
    ScrollGridView fanyiGV;

    private ActionBar actionBar;
    private Realm realm;
    private String word;

    public static void startActivity(Context context, String word) {
        Intent intent = new Intent(context, DetailedActivity.class);
        intent.putExtra("Word", word);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
        bindView();
        init();
    }

    private void bindView() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化
     */
    private void init() {
        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        word = intent.getStringExtra("Word");
        actionBar.setTitle(word);

        //查询缓存中是否存在
        IdiomRealm idiomRealm = realm.where(IdiomRealm.class).equalTo("word", word).findFirst();
        if (idiomRealm != null) {
            Log.d(TAG, "本地缓存");
            Gson gson = GsonUtil.buildGson();
            IdiomResult idiomResult = gson.fromJson(idiomRealm.getResult(), IdiomResult.class);
            updateUI(idiomResult);
        } else {
            //网络查询
            requestIdiom();
            Log.d(TAG, "网络查询");
        }

    }

    /**
     * 网络接口查询
     */
    private void requestIdiom() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("查询中...");
        dialog.show();
        IdiomApi api = IdiomRetrofit.getInstance().getService(IdiomApi.class);
        Call<Idiom> call = api.getIdiom(Configs.IDIOM_APP_KEY, word);
        call.enqueue(new Callback<Idiom>() {
            @Override
            public void onResponse(Call<Idiom> call, Response<Idiom> response) {
                dialog.dismiss();
                Idiom idiom = response.body();
                if (idiom.isSuccess()) {
                    IdiomResult idiomResult = idiom.getResult();
                    updateUI(idiomResult);

                    saveIdiom(idiomResult);
                } else {
                    Toast.makeText(DetailedActivity.this, idiom.getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Idiom> call, Throwable t) {
                dialog.dismiss();
                t.printStackTrace();
                Toast.makeText(DetailedActivity.this, "查询失败，请稍后再试!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 更新UI
     *
     * @param idiomResult
     */
    private void updateUI(IdiomResult idiomResult) {
        setIdiomText(bushouTV, idiomResult.getBushou());
        setIdiomText(headTV, idiomResult.getHead());
        setIdiomText(pinyinTV, idiomResult.getPinyin());
        setIdiomText(chengyujsTV, idiomResult.getChengyujs());
        setIdiomText(fromTV, idiomResult.getFrom_());
        setIdiomText(exampleTV, idiomResult.getExample());
        setIdiomText(ciyujsTV, idiomResult.getCiyujs());
        setIdiomText(yufaTV, idiomResult.getYufa());
        setIdiomText(yinzhengjsTV, idiomResult.getYinzhengjs());

        setGridAdapter(tongyiGV, idiomResult.getTongyi());
        setGridAdapter(fanyiGV, idiomResult.getFanyi());
    }

    /**
     * 缓存数据
     *
     * @param idiomResult
     */
    private void saveIdiom(IdiomResult idiomResult) {

        Gson gson = GsonUtil.buildGson();
        IdiomRealm idiomRealm = new IdiomRealm();
        idiomRealm.setWord(word);
        idiomRealm.setResult(gson.toJson(idiomResult));
        idiomRealm.setCreateDate(new Date(System.currentTimeMillis()));

        realm.beginTransaction();
        realm.copyToRealm(idiomRealm);
        realm.commitTransaction();

        //post缓存成语数据事件
        EventBus.getDefault().post(new IdiomEvent());
    }

    /**
     * 去空设置文本
     *
     * @param textView
     * @param text
     */
    private void setIdiomText(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setText(R.string.empty_text);
        } else {
            textView.setText(text);
        }
    }

    /**
     * 获取ArrayAdapter
     *
     * @param list
     * @return
     */
    private void setGridAdapter(GridView gridView, final List<String> list) {

        if (list == null) {
            return;
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.cell_idiom_item, R.id.idiomItemTV, list);
        gridView.setAdapter(adapter);
        //设置item点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //查询点击的成语
                DetailedActivity.startActivity(DetailedActivity.this, list.get(position));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://返回按钮
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭数据库
        realm.close();
    }
}

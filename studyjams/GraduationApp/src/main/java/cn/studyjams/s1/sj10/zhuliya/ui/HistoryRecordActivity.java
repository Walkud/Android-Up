package cn.studyjams.s1.sj10.zhuliya.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.studyjams.s1.sj10.zhuliya.R;
import cn.studyjams.s1.sj10.zhuliya.bean.IdiomRealm;
import cn.studyjams.s1.sj10.zhuliya.ui.adapter.IdiomAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * 历史记录
 * Created by jan on 16/4/26.
 */
public class HistoryRecordActivity extends BaseActivity {

    @Bind(R.id.emptyRecordTV)
    TextView emptyRecordTV;
    @Bind(R.id.historyRecordGV)
    GridView historyRecordGV;

    private ActionBar actionBar;
    private Realm realm;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HistoryRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);
        ButterKnife.bind(this);

        bindView();
        init();
    }

    private void bindView() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.history_record);
    }

    /**
     * 初始化
     */
    private void init() {
        realm = Realm.getDefaultInstance();

        RealmResults<IdiomRealm> idiomRealms = realm.where(IdiomRealm.class).findAll();

        if (idiomRealms.isEmpty()) {
            emptyRecordTV.setVisibility(View.VISIBLE);
            return;
        }

        idiomRealms.sort("createDate", Sort.DESCENDING);
        //设置适配器
        final IdiomAdapter adapter = new IdiomAdapter(this, idiomRealms, true, false);
        historyRecordGV.setAdapter(adapter);
        //设置item点击事件
        historyRecordGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //查询点击的成语
                DetailedActivity.startActivity(HistoryRecordActivity.this, adapter.getItem(position).getWord());
            }
        });
        historyRecordGV.setVisibility(View.VISIBLE);
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
        realm.close();
    }
}

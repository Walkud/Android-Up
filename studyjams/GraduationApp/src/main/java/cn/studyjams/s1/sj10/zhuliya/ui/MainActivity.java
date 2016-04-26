package cn.studyjams.s1.sj10.zhuliya.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.studyjams.s1.sj10.zhuliya.R;
import cn.studyjams.s1.sj10.zhuliya.bean.Idiom;
import cn.studyjams.s1.sj10.zhuliya.bean.IdiomRealm;
import cn.studyjams.s1.sj10.zhuliya.common.event.IdiomEvent;
import cn.studyjams.s1.sj10.zhuliya.ui.adapter.IdiomAdapter;
import cn.studyjams.s1.sj10.zhuliya.utils.Urls;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.bannerIV)
    ImageView bannerIV;
    @Bind(R.id.queryIdiomET)
    EditText queryIdiomET;
    @Bind(R.id.emptyRecordTV)
    TextView emptyRecordTV;
    @Bind(R.id.recordGV)
    GridView recordGV;


    private List<String> bannerUrls;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
        addListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        realm = Realm.getDefaultInstance();
        bannerUrls = Urls.getBannerUrls();
        int size = bannerUrls.size();//获取图片列表长度
        int index = new Random().nextInt(size);//随机选取一张图片
        //下载图片
        Picasso.with(this).load(bannerUrls.get(index)).into(bannerIV);

        setIdiomAdapter();
    }

    /**
     * 设置GridView适配器
     */
    private void setIdiomAdapter() {
        RealmResults<IdiomRealm> idiomRealms = realm.where(IdiomRealm.class).findAll();
        idiomRealms.sort("createDate", Sort.DESCENDING);

        if (idiomRealms.isEmpty()) {
            //历史记录为空，显示提示信息
            emptyRecordTV.setVisibility(View.VISIBLE);
        } else {
            recordGV.setVisibility(View.VISIBLE);
        }


        final IdiomAdapter adapter = new IdiomAdapter(this, idiomRealms, true);
        recordGV.setAdapter(adapter);
        //设置item点击事件
        recordGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //查询点击的成语
                DetailedActivity.startActivity(MainActivity.this, adapter.getItem(position).getWord());
            }
        });
    }

    /**
     * 添加事件
     */
    private void addListener() {
        queryIdiomET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String word = v.getText().toString().trim();
                    queryIdiom(word);
                    return true;
                }
                return false;
            }


        });
    }


    /**
     * 查询成语
     *
     * @param word 查询文本
     */
    private void queryIdiom(String word) {

        if (word.length() != 4) {
            Toast.makeText(this, "请输入4位成语!", Toast.LENGTH_LONG).show();
            return;
        }

        DetailedActivity.startActivity(this, word);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_history_record://历史记录
                HistoryRecordActivity.startActivity(this);
                break;
            case R.id.nav_share://分享
                navShare();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 分享
     */
    private void navShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "非常简单的一款应用，值得拥有");
        intent.putExtra(Intent.EXTRA_TITLE, "成语新秀App");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "请选择"));
    }

    /**
     * 成语缓存事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void idiomSaveEvent(IdiomEvent event) {
        //显示历史记录GridView
        if (recordGV.getVisibility() == View.GONE) {
            emptyRecordTV.setVisibility(View.GONE);
            recordGV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭数据库
        realm.close();
        EventBus.getDefault().unregister(this);
    }
}

package owncloudsms.walkudui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

import butterknife.Bind;
import owncloudsms.walkudui.BaseFragment;
import owncloudsms.walkudui.R;
import owncloudsms.walkudui.adapter.ActionRecyclerAdapter;
import owncloudsms.walkudui.view.SuperSwipeRefreshLayout;
import owncloudsms.walkudui.view.recycler.OnRecyclerViewItemClickListener;
import owncloudsms.walkudui.view.scroll.GridLayoutManagerPlus;

/**
 * 主页
 * Created by Walkud on 16/9/22.
 */
public class HomeFragment extends BaseFragment implements SuperSwipeRefreshLayout.OnPullRefreshListener {

    @Bind(R.id.banner_viewPager)
    ViewPager viewPager;
    @Bind(R.id.banner_indicator)
    Indicator indicator;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.swipe_refresh)
    SuperSwipeRefreshLayout swipeRefresh;
    @Bind(R.id.action_recycler_view)
    RecyclerView actionRecyclerView;

    @Bind(R.id.community_activities_recycler)
    RecyclerView communityActRecyclerView;


    private BannerComponent bannerComponent;

    private ActionRecyclerAdapter actionRecyclerAdapter;
    private ActionRecyclerAdapter communityActRecyclerAdapter;

    private String tabName;


    @Override
    public int getLyaoutId() {
        return R.layout.fragment_tab_home;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        steepStatusBar(toolbar);
        toolbarTitle.setText("首页");
        toolbar.inflateMenu(R.menu.main_tab_home_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        showToast("Menu1");
                        break;
                    case R.id.menu2:
                        showToast("Menu2");
                        break;
                }
                return true;
            }
        });
    }

    private void initActionRecyclerView() {
        GridLayoutManagerPlus layoutManager = new GridLayoutManagerPlus(getActivity(), 4);
        actionRecyclerView.setLayoutManager(layoutManager);
        actionRecyclerAdapter = new ActionRecyclerAdapter(getActivity());
        actionRecyclerView.setAdapter(actionRecyclerAdapter);

        layoutManager = new GridLayoutManagerPlus(getActivity(), 4);
        communityActRecyclerView.setLayoutManager(layoutManager);
        communityActRecyclerAdapter = new ActionRecyclerAdapter(getActivity());
        communityActRecyclerView.setAdapter(communityActRecyclerAdapter);
    }


    @Override
    public void initData() {
        Bundle bundle = getArguments();
        tabName = bundle.getString("intent_String_tabname");

        //被包含的View是否随手指的移动而移动
        swipeRefresh.setTargetScrollWithLayout(false);
        swipeRefresh.setOnPullRefreshListener(this);

        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.WHITE, 0, ScrollBar.Gravity.CENTENT_BACKGROUND));
        viewPager.setOffscreenPageLimit(2);

        bannerComponent = new BannerComponent(indicator, viewPager, false);
        bannerComponent.setAdapter(adapter);
        bannerComponent.setAutoPlayTime(2500);

        initToolBar();
        initActionRecyclerView();
    }

    private int[] images = {R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R.mipmap.p4};

    private IndicatorViewPager.IndicatorViewPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(container.getContext());
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new ImageView(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ImageView imageView = (ImageView) convertView;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[position]);
            return convertView;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    };

    @Override
    public void addListener() {
        actionRecyclerAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("Action ItemClick:" + position);
            }
        });

        communityActRecyclerAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("CommunityAct ItemClick:" + position);
            }
        });
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        Log.d("cccc", "Fragment所在的Activity onResume, onResumeLazy " + this);
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        Log.d("cccc", "Fragment 显示 " + this);
        bannerComponent.startAutoPlay();
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        Log.d("cccc", "Fragment 掩藏 " + this);
        bannerComponent.stopAutoPlay();
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        Log.d("cccc", "Fragment所在的Activity onPause, onPauseLazy " + this);
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        Log.d("cccc", "Fragment View将被销毁 " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cccc", "Fragment 所在的Activity onDestroy " + this);
    }

    /**
     * SuperSwipeRefreshLayout下拉刷新事件
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    /**
     * SuperSwipeRefreshLayout下拉距离
     *
     * @param distance
     */
    @Override
    public void onPullDistance(int distance) {
    }

    /**
     * SuperSwipeRefreshLayout下拉过程中，下拉的距离是否足够出发刷新
     *
     * @param enable
     */
    @Override
    public void onPullEnable(boolean enable) {
    }
}

package owncloudsms.walkudui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

import butterknife.BindView;
import owncloudsms.walkudui.fragment.HomeFragment;
import owncloudsms.walkudui.fragment.MineFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabmain_viewPager)
    SViewPager viewPager;
    @BindView(R.id.tabmain_indicator)
    FixedIndicatorView indicator;
    @BindView(R.id.tab_center)
    ImageView centerImageView;

    IndicatorViewPager indicatorViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        //这里可以添加一个view，作为centerView，会位于Indicator的tab的中间,在这里功能只做站位用
        View centerView = getLayoutInflater().inflate(R.layout.tab_main_center_stub, indicator, false);
        indicator.setCenterView(centerView);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void addListener() {
        centerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("中间按钮点击事件");
            }
        });
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"主页", "我的"};
        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector};
        private LayoutInflater inflater;
        private Fragment[] fragments = {new HomeFragment(), new MineFragment()};

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            ImageButton imageView = (ImageButton) convertView;
            imageView.setImageResource(tabIcons[position]);
            return imageView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment fragment = fragments[position];
            Bundle bundle = new Bundle();
            bundle.putString("intent_String_tabname", tabNames[position]);
            fragment.setArguments(bundle);
            return fragment;
        }
    }
}

package owncloudsms.walkudui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

import butterknife.Bind;
import owncloudsms.walkudui.view.indicator.ShadesIndicatorViewPager;

/**
 * Created by Walkud on 16/9/22.
 */

public class GuideShadesSteepStatusBarActivity extends BaseActivity {

    //    @Bind(R.id.root_layout)
//    FrameLayout rootLayout;
    @Bind(R.id.guide_viewPager)
    ViewPager viewPager;
    @Bind(R.id.guide_indicator)
    ScrollIndicatorView scrollIndicatorView;

    private ShadesIndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    int[] colorBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setSteepStatusBar(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_steep_status_bar;
    }

    @Override
    public void initData() {
        colorBg = getResources().getIntArray(R.array.splash_bg);
        indicatorViewPager = new ShadesIndicatorViewPager(scrollIndicatorView, viewPager, colorBg);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(adapter);

        scrollIndicatorView.setScrollBar(new DrawableBar(this, R.mipmap.wedget_icon_indicator_selected, ScrollBar.Gravity.CENTENT));
    }

    @Override
    public void addListener() {
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new TextView(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            ((TextView) convertView).setGravity(Gravity.CENTER);
            ((TextView) convertView).setText("第" + (position + 1) + "页");
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return colorBg.length;
        }
    };

}

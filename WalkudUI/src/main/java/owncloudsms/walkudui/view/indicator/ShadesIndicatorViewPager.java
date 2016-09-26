package owncloudsms.walkudui.view.indicator;

import android.support.v4.view.ViewPager;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

import owncloudsms.walkudui.utils.ColorShades;

/**
 * ViewPager背景颜色渐变指示器
 * 重写IndicatorViewPager 扩展监听ViewPager页面改变事件,
 * Created by Walkud on 16/9/23.
 */
public class ShadesIndicatorViewPager extends IndicatorViewPager {

    private int[] colorBg;//背景颜色集合

    private ViewPager.OnPageChangeListener onPageChangeListener;

    private ColorShades shades = new ColorShades();

    public ShadesIndicatorViewPager(Indicator indicator, ViewPager viewPager, int[] colors) {
        super(indicator, viewPager);
        this.colorBg = colors;
    }

    public ShadesIndicatorViewPager(Indicator indicator, ViewPager viewPager, int[] colors, boolean indicatorClickable) {
        super(indicator, viewPager, indicatorClickable);
        this.colorBg = colors;
    }

    /**
     * 重写ViewPager onPageChange监听事件
     */
    public void iniOnPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicatorView.setCurrentItem(position, true);
                if (onIndicatorPageChangeListener != null) {
                    onIndicatorPageChangeListener.onIndicatorPageChange(indicatorView.getPreSelectItem(), position);
                }

                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);

                //计算渐变背景色
                shades.setFromColor(colorBg[position % colorBg.length])
                        .setToColor(colorBg[(position + 1) % colorBg.length])
                        .setShade(positionOffset);
                int generate = shades.generate();
                //设置ViewPager渐变背景颜色
                viewPager.setBackgroundColor(generate);

                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                indicatorView.onPageScrollStateChanged(state);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    /**
     * OnPageChangeListener适配
     */
    public static class OnPageChangeListenerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    /**
     * 设置ViewPager页面监听
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    /**
     * 设置背景颜色集合
     *
     * @param colorBg
     */
    public void setColorBg(int[] colorBg) {
        this.colorBg = colorBg;
    }

}

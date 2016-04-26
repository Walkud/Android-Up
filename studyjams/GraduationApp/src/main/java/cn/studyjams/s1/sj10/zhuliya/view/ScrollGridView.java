package cn.studyjams.s1.sj10.zhuliya.view;

import android.widget.GridView;

/**
 * <p>
 * 文件描述：自定义GridView
 * </p>
 * <p>
 * 公 司：成都网优力软件有限公司
 * </p>
 * <p>
 * 内容摘要：该类用于GridView与scrollview共存时的问题
 * </p>
 * <p>
 * 其他说明：// 其它内容的说明
 * </p>
 * <p>
 * 完成日期：//输入完成日期，例：2000年2月25日
 * </p>
 * 
 * @author hi
 * @version 创建时间：2014年5月20日 上午11:58:01
 */
public class ScrollGridView extends GridView {

	/**
	 * @param context
	 * @param attrs
	 */
	public ScrollGridView(android.content.Context context,
						  android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
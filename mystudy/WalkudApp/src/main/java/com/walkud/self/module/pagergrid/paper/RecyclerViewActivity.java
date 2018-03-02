package com.walkud.self.module.pagergrid.paper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hhl.gridpagersnaphelper.GridPagerSnapHelper;
import com.hhl.gridpagersnaphelper.GridPagerUtils;
import com.hhl.gridpagersnaphelper.transform.TwoRowDataTransform;
import com.hhl.recyclerviewindicator.CirclePageIndicator;
import com.hhl.recyclerviewindicator.OnPageChangeListener;
import com.walkud.self.R;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        configSecondRecyclerView(2, 5);
    }

    private void configSecondRecyclerView(int row, int column) {
        RecyclerView secondRV = (RecyclerView) findViewById(R.id.recycler_view_second);
        secondRV.setHasFixedSize(true);

        //setLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, row, LinearLayoutManager.HORIZONTAL, false);
        secondRV.setLayoutManager(gridLayoutManager);

        //attachToRecyclerView
        GridPagerSnapHelper gridPagerSnapHelper = new GridPagerSnapHelper();
        gridPagerSnapHelper.setRow(row).setColumn(column);
        gridPagerSnapHelper.attachToRecyclerView(secondRV);

        int screenWidth = ScreenUtils.getScreenWidth(this);
        int itemWidth = screenWidth / column;

        //getDataSource
        List<DataSourceUtils.ItemData> dataList = DataSourceUtils.getDataSource();
        dataList = GridPagerUtils.transformAndFillEmptyData(
                new TwoRowDataTransform<DataSourceUtils.ItemData>(column), dataList);

        //setAdapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, dataList, itemWidth);
        secondRV.setAdapter(adapter);

        //indicator
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.first_page_indicator);
        indicator.setRecyclerView(secondRV);
        //Note: pageColumn must be config
        indicator.setPageColumn(column);

        indicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

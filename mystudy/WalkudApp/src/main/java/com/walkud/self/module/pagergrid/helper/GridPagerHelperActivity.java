package com.walkud.self.module.pagergrid.helper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.walkud.self.R;

public class GridPagerHelperActivity extends AppCompatActivity implements PagingScrollHelper.onPageChangeListener {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    TextView tv_title;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    RadioGroup rg_layout;
    PagingIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_pager_helper);
        init();
        rg_layout = (RadioGroup) findViewById(R.id.rg_layout);
        rg_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchLayout(checkedId);
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        pageIndicator = (PagingIndicator) findViewById(R.id.pageindicator);
        myAdapter = new MyAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(myAdapter);
        scrollHelper.setUpRecycleView(recyclerView);
        scrollHelper.setOnPageChangeListener(this);
        switchLayout(R.id.rb_horizontal_page);
    }

    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private LinearLayoutManager hLinearLayoutManager = null;
    private LinearLayoutManager vLinearLayoutManager = null;

    private void init() {
        hLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        horizontalPageLayoutManager = new HorizontalPageLayoutManager(2, 5);
    }

    private void switchLayout(int checkedId) {
        RecyclerView.LayoutManager layoutManager = null;
        switch (checkedId) {
            case R.id.rb_horizontal_page:
                layoutManager = horizontalPageLayoutManager;
                break;
            case R.id.rb_vertical_page:
                layoutManager = vLinearLayoutManager;
                break;
            case R.id.rb_vertical_page2:
                layoutManager = hLinearLayoutManager;
                break;
        }
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
            scrollHelper.updateLayoutManger();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPageChange(int index) {

        tv_title.setText("第" + (index + 1) + "页");
    }
}

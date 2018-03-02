package com.walkud.self.module.pagergrid.paper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.walkud.self.R;

import java.util.List;

/**
 * Created by hanhailong on 2017/8/20.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<DataSourceUtils.ItemData> mDataList;
    private final LayoutInflater mLayoutInflater;
    private final int mItemWidth;

    public RecyclerViewAdapter(Context context, List<DataSourceUtils.ItemData> dataList, int itemWidth) {
        mDataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
        this.mItemWidth = itemWidth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.view_item, parent, false), mItemWidth);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindData(mDataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "点击postion:" + mDataList.get(position).title, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private View itemView;

        public ViewHolder(View itemView, int itemWidth) {
            super(itemView);
            this.itemView = itemView;

            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = itemWidth;

            imageView = (ImageView) itemView.findViewById(R.id.view_item_icon);
            textView = (TextView) itemView.findViewById(R.id.view_item_title);
        }

        public void bindData(DataSourceUtils.ItemData itemData) {
            if (itemData != null) {
                itemView.setVisibility(View.VISIBLE);
//                GlideApp.with(itemView).load(itemData.url).into(imageView);
                textView.setText(itemData.title);
            } else {
                itemView.setVisibility(View.GONE);
            }
        }
    }
}

package owncloudsms.walkudui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import owncloudsms.walkudui.R;
import owncloudsms.walkudui.adapter.ActionRecyclerAdapter.ActionViewHolder;
import owncloudsms.walkudui.view.recycler.OnRecyclerViewItemClickListener;

/**
 * Created by Walkud on 16/10/13.
 */

public class ActionRecyclerAdapter extends RecyclerView.Adapter<ActionViewHolder> {

    private Context context;

    private OnRecyclerViewItemClickListener listener;

    public ActionRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.cell_tab_home_action_item, parent, false);
        return new ActionViewHolder(view, listener);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {
        holder.actionIV.setImageResource(R.mipmap.test_home_icon_more_camera);
        holder.ationTV.setText("成员授权");

    }

    static class ActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.action_item_image_view)
        ImageView actionIV;
        @Bind(R.id.ation_item_text_view)
        TextView ationTV;
        OnRecyclerViewItemClickListener listener;

        public ActionViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, getAdapterPosition());
            }
        }


    }


}

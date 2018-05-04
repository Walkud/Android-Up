package cn.studyjams.s1.sj10.zhuliya.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.studyjams.s1.sj10.zhuliya.R;
import cn.studyjams.s1.sj10.zhuliya.bean.IdiomRealm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * 成语查询记录Adapter
 * Created by jan on 16/4/26.
 */
public class IdiomAdapter extends RealmBaseAdapter<IdiomRealm> {

    //是否显示数量
    private boolean isLimit = true;

    public IdiomAdapter(Context context, RealmResults<IdiomRealm> realmResults, boolean automaticUpdate, boolean isLimit) {
        this(context, realmResults, automaticUpdate);
        this.isLimit = isLimit;
    }

    public IdiomAdapter(Context context, RealmResults<IdiomRealm> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        if (isLimit && count > 9) {
            //最多只显示9个
            return 9;
        }

        return count;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cell_idiom_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.idiomItemTV.setText(getItem(position).getWord());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.idiomItemTV)
        TextView idiomItemTV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
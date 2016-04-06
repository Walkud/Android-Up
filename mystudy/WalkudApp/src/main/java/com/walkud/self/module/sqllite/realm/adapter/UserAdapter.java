package com.walkud.self.module.sqllite.realm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.walkud.self.R;
import com.walkud.self.module.sqllite.realm.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by jan on 16/3/30.
 */
public class UserAdapter extends RealmBaseAdapter<User> implements ListAdapter {

    public UserAdapter(Context context, RealmResults<User> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cell_basis_realm_user_item,
                    parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = realmResults.get(position);
        viewHolder.userId.setText("" + user.getId());
        viewHolder.userName.setText(user.getName());
        viewHolder.userAge.setText("" + user.getAge());
        viewHolder.userDate.setText("" + user.getDate().getYear());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.userId)
        TextView userId;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.userAge)
        TextView userAge;
        @Bind(R.id.userDate)
        TextView userDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

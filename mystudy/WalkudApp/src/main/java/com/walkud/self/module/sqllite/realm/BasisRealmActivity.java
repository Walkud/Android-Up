package com.walkud.self.module.sqllite.realm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;
import com.walkud.self.module.sqllite.realm.adapter.UserAdapter;
import com.walkud.self.module.sqllite.realm.bean.User;
import com.walkud.self.utils.RealmUtil;

import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Realm基本用法Ativity
 * Created by jan on 16/3/30.
 */
public class BasisRealmActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.insertData)
    Button insertData;
    @Bind(R.id.modifyData)
    Button modifyData;
    @Bind(R.id.deleteData)
    Button deleteData;
    @Bind(R.id.queryData)
    Button queryData;
    @Bind(R.id.basisRealmLV)
    ListView basisRealmLV;

    private Realm realm;

    private Random r = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basis_realm);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        insertData.setOnClickListener(this);
        modifyData.setOnClickListener(this);
        deleteData.setOnClickListener(this);
        queryData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.insertData://插入数据
                insertData();
                break;
            case R.id.modifyData://修改数据
                modifyData();
                break;
            case R.id.deleteData://删除数据
                modidfyData();
                break;
            case R.id.queryData://查询数据
                queryData();
        }

    }


    /**
     * 插入数据
     */
    private void insertData() {
        int index = r.nextInt(9999);

        User user = new User();
        user.setId(r.nextInt(999999999));
        user.setName("Alpha Go" + index);
        user.setAge(r.nextInt(99));
        user.setDate(new Date(System.currentTimeMillis()));
        //保存数据
        RealmUtil.insertByTransaction(user);
    }

    /**
     * 修改数据
     */
    private void modifyData() {
        RealmResults<User> users = realm.where(User.class).findAll();
        if (users.size() > 0) {
            realm.beginTransaction();
            users.get(0).setAge(r.nextInt(10));
            realm.commitTransaction();
        }
    }

    /**
     * 删除数据
     */
    private void modidfyData() {
        realm.beginTransaction();
        //删除第一条记录
        realm.where(User.class).findFirst().removeFromRealm();
        realm.commitTransaction();
    }

    /**
     * 查询所有数据
     */
    private void queryData() {
        RealmResults<User> users = realm.where(User.class).findAll();
        UserAdapter adapter = new UserAdapter(this, users, true);
        basisRealmLV.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭数据库
        realm.close();
    }
}

package cn.studyjams.s1.sj10.zhuliya.bean;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * 实体基类
 * Created by jan on 16/4/25.
 */
public class BaseBean<T> {

    private String reason;//返回说明

    private int error_code;//返回码

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return "success".equals(reason);
    }
}

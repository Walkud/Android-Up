package cn.studyjams.s1.sj10.zhuliya.bean;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by jan on 16/4/26.
 */
public class IdiomRealm extends RealmObject {

    private String word;//成语
    private String result;//成语相关Json
    private Date createDate;//时间

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

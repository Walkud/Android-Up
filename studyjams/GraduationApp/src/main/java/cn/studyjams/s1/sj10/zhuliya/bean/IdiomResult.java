package cn.studyjams.s1.sj10.zhuliya.bean;

import java.util.List;

/**
 * Created by jan on 16/4/26.
 */
public class IdiomResult {
    private String bushou;//首字部首
    private String head;//成语词头
    private String pinyin;//拼音
    private String chengyujs;//成语解释
    private String from_;//成语出处
    private String example;//举例
    private String yufa;//语法
    private String ciyujs;//词语解释
    private String yinzhengjs;//引证解释
    private List<String> tongyi;//同义词
    private List<String> fanyi;//反义词

    public String getBushou() {
        return bushou;
    }

    public void setBushou(String bushou) {
        this.bushou = bushou;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getChengyujs() {
        return chengyujs;
    }

    public void setChengyujs(String chengyujs) {
        this.chengyujs = chengyujs;
    }

    public String getFrom_() {
        return from_;
    }

    public void setFrom_(String from_) {
        this.from_ = from_;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getYufa() {
        return yufa;
    }

    public void setYufa(String yufa) {
        this.yufa = yufa;
    }

    public String getCiyujs() {
        return ciyujs;
    }

    public void setCiyujs(String ciyujs) {
        this.ciyujs = ciyujs;
    }

    public String getYinzhengjs() {
        return yinzhengjs;
    }

    public void setYinzhengjs(String yinzhengjs) {
        this.yinzhengjs = yinzhengjs;
    }

    public List<String> getTongyi() {
        return tongyi;
    }

    public void setTongyi(List<String> tongyi) {
        this.tongyi = tongyi;
    }

    public List<String> getFanyi() {
        return fanyi;
    }

    public void setFanyi(List<String> fanyi) {
        this.fanyi = fanyi;
    }
}
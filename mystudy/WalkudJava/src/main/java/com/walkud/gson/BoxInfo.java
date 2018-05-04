package com.walkud.gson;

/**
 * Created by Walkud on 17/4/1.
 */

public class BoxInfo {

    /**
     * finish : true
     * ip : 192.168.0.1
     * code : xxxxxx
     * name : box1
     */

    private boolean finish;
    private String ip;
    private String code;
    private String name;
    private long expireTime;

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "BoxInfo{" +
                "finish=" + finish +
                ", ip='" + ip + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}

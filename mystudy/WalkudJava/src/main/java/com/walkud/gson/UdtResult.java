package com.walkud.gson;

/**
 * 封装UDT返回结果
 * Created by Walkud on 17/3/24.
 */
public class UdtResult<T> {
    /**
     * success : true
     * ecode : 0
     * msg : msg
     */

    private boolean success;
    private int ecode;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UdtResult{" +
                "success=" + success +
                ", ecode=" + ecode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

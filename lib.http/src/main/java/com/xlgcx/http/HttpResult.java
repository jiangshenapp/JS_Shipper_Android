package com.xlgcx.http;

/**
 * Created by miku on 2016/7/23.
 */
public class HttpResult<T>{
    /**
     * 返回状态
     */
    private int resultCode;
    private String resultMsg;
    private T resultValue;
    private String data;




    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultValue() {
        return resultValue;
    }

    public void setResultValue(T resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", resultValue=" + resultValue +
                '}';
    }


}

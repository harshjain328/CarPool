package com.noobs.carpool.models;

/**
 * Created by deepak on 6/5/17.
 */
public class VerifySmsCode {

    private String request_id;
    private int code;

    public VerifySmsCode(String request_id, int code){
        this.request_id = request_id;
        this.code = code;
    }

    public String getRequestId() {
        return request_id;
    }

    public void setRequestId(String request_id) {
        this.request_id = request_id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString(){
        return request_id + ", " + code;
    }
}

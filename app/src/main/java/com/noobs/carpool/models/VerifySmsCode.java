package com.noobs.carpool.models;

/**
 * Created by deepak on 6/5/17.
 */
public class VerifySmsCode {

    private String request_id;
    private String code;

    public VerifySmsCode(String request_id, String code){
        this.request_id = request_id;
        this.code = code;
    }

    public String getRequestId() {
        return request_id;
    }

    public void setRequestId(String request_id) {
        this.request_id = request_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString(){
        return request_id + ", " + code;
    }
}

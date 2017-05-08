package com.noobs.carpool.models;

/**
 * Created by deepak on 6/5/17.
 */
public class SmsCodeResponse {


    private String request_id;
    private String status;
    private String error_text;

    public SmsCodeResponse(String request_id, String status, String error_text){
        this.request_id = request_id;
        this.status = status;
        this.error_text = error_text;
    }

    public String getRequestId() {
        return request_id;
    }

    public void setRequestId(String request_id) {
        this.request_id = request_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorText() {
        return error_text;
    }

    public void setErrorText(String error_text) {
        this.error_text = error_text;
    }

    @Override
    public String toString(){
        return request_id + ",\n" + status;
    }
}

package com.noobs.carpool.models;

/**
 * Created by deepak on 6/5/17.
 */
public class VerifySmsCodeResponse {

    private String status;
    private String error_text;

    public VerifySmsCodeResponse(String status, String error_text){
        this.status = status;
        this.error_text = error_text;
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
}

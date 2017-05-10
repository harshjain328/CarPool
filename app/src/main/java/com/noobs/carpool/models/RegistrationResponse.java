package com.noobs.carpool.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepak on 10/5/17.
 */

public class RegistrationResponse {

    @SerializedName("user_id")
    private long userId;

    @SerializedName("user_token")
    private String token;

    @SerializedName("message")
    private String message;



    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }


}

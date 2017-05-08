package com.noobs.carpool.models;

/**
 * Created by deepak on 6/5/17.
 */
public class SmsCode {

    private long number;

    public SmsCode(long number){
        this.number = number;
    }

    @Override
    public String toString(){
        return number+"";
    }
}

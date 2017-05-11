package com.noobs.carpool.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepak on 10/5/17.
 */

public class Registration {

    @SerializedName("user_nickname")
    private String nickName;

    @SerializedName("user_phone_no")
    private String phoneNo;

    @SerializedName("user_profile_pic")
    private String profilePic;

    public Registration(String nickName, String phoneNo, String profilePic){
        this(nickName, phoneNo);
        this.setProfilePic(profilePic);
    }

    public Registration(String nickName, String phoneNo){
        this.setNickName(nickName);
        this.setPhoneNo(phoneNo);
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
package com.noobs.carpool.api;

import com.noobs.carpool.models.SmsCode;
import com.noobs.carpool.models.SmsCodeResponse;
import com.noobs.carpool.models.VerifySmsCode;
import com.noobs.carpool.models.VerifySmsCodeResponse;

import retrofit2.Call;
import retrofit2.http.*;

/**
 *
 * @author deepak
 */
public interface ApiClient {

    @POST("SMS/getcode")
    Call<SmsCodeResponse> getCode(@Body SmsCode smsCode);

    @POST("SMS/verifycode")
    Call<VerifySmsCodeResponse> verifyCode(@Body VerifySmsCode verifyCode);

}

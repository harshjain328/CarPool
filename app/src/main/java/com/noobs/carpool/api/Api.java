package com.noobs.carpool.api;

import com.noobs.carpool.models.SmsCode;
import com.noobs.carpool.models.SmsCodeResponse;
import com.noobs.carpool.models.VerifySmsCode;
import com.noobs.carpool.models.VerifySmsCodeResponse;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by deepak on 6/5/17.
 */
public class Api {


    public static class Verification{

        //send code to the given number
        public static Call<SmsCodeResponse> getCode(SmsCode smsCode, RetrofitCallback<SmsCodeResponse> resp){
            ApiClient client  = RetrofitClient.getClient().create(ApiClient.class);
            Call<SmsCodeResponse> call = client.getCode(smsCode);
            call.enqueue(resp);
            return call;
        }

        //verifies code and return VerifySmsCodeResponse
        public static Call<VerifySmsCodeResponse> verifyCode(VerifySmsCode verifyCode, RetrofitCallback<VerifySmsCodeResponse> resp){
            ApiClient client  = RetrofitClient.getClient().create(ApiClient.class);
            Call<VerifySmsCodeResponse> call = client.verifyCode(verifyCode);
            call.enqueue(resp);
            return call;
        }

    }

}

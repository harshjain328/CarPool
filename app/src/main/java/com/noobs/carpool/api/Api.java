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

        private static ApiClient.Verification verificationClient  = RetrofitClient.getClient().create(ApiClient.Verification.class);

        //send code to the given number
        public static Call<SmsCodeResponse> getCode(SmsCode smsCode, RetrofitCallback<SmsCodeResponse> resp){

            Call<SmsCodeResponse> call = verificationClient.getCode(smsCode);
            call.enqueue(resp);
            return call;
        }

        //verifies code and return VerifySmsCodeResponse
        public static Call<VerifySmsCodeResponse> verifyCode(VerifySmsCode verifyCode, RetrofitCallback<VerifySmsCodeResponse> resp){
            Call<VerifySmsCodeResponse> call = verificationClient.verifyCode(verifyCode);
            call.enqueue(resp);
            return call;
        }

    }


}

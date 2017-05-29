package com.noobs.carpool.api;

import com.noobs.carpool.models.Registration;
import com.noobs.carpool.models.RegistrationResponse;
import com.noobs.carpool.models.SmsCode;
import com.noobs.carpool.models.SmsCodeResponse;
import com.noobs.carpool.models.VerifySmsCode;
import com.noobs.carpool.models.VerifySmsCodeResponse;
import com.noobs.carpool.utils.MapModels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.*;

/**
 *
 * @author deepak
 */
public interface ApiClient {

    interface Verification {
        @POST("SMS/getcode")
        Call<SmsCodeResponse> getCode(@Body SmsCode smsCode);

        @POST("SMS/verifycode")
        Call<VerifySmsCodeResponse> verifyCode(@Body VerifySmsCode verifyCode);
    }

    interface Users{
        @POST("user/register")
        Call<RegistrationResponse> register(@Body Registration registration);
    }

    interface Maps{
        @GET("http://maps.googleapis.com/maps/api/directions/json")
        Call<MapModels.DirectionResults> getRoute(@Query("origin") String origin, @Query("destination") String destination);
    }

    interface Ride{
        //@POST("/ride")
        //Call<RideResponse> registerRide(@BODY RideResponse ride){ }
    }

}

package com.noobs.carpool.api;

import com.noobs.carpool.models.DirectionRequest;
import com.noobs.carpool.models.DirectionRequestByLatLng;
import com.noobs.carpool.models.Registration;
import com.noobs.carpool.models.RegistrationResponse;
import com.noobs.carpool.models.SmsCode;
import com.noobs.carpool.models.SmsCodeResponse;
import com.noobs.carpool.models.VerifySmsCode;
import com.noobs.carpool.models.VerifySmsCodeResponse;
import com.noobs.carpool.utils.MapModels;

import retrofit2.Call;

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

    public static class Users{

        private static ApiClient.Users usersClient  = RetrofitClient.getClient().create(ApiClient.Users.class);

        public static Call<RegistrationResponse> registerUser(final Registration registration, RetrofitCallback<RegistrationResponse> resp){
            Call<RegistrationResponse> call = usersClient.register(registration);
            call.enqueue(resp);
            return call;
        }

    }

    public static class Maps{
        private static ApiClient.Maps mapsClient  = RetrofitClient.getClient().create(ApiClient.Maps.class);

        public static Call<MapModels.DirectionResults> getRoutes(final DirectionRequest req, RetrofitCallback<MapModels.DirectionResults> resp){
            //Call<MapModels.DirectionResults> call = mapsClient.getRoute(70.56 + ", " + 23.80, 71.80 + ", " + 23.70);
            Call<MapModels.DirectionResults> call = mapsClient.getRoute(req.getSource(), req.getDestination());
            call.enqueue(resp);
            return call;
        }
    }


}

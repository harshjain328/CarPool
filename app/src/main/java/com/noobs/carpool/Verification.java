package com.noobs.carpool;

import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noobs.carpool.api.Api;
import com.noobs.carpool.api.RetrofitCallback;
import com.noobs.carpool.models.SmsCode;
import com.noobs.carpool.models.SmsCodeResponse;
import com.noobs.carpool.models.VerifySmsCode;
import com.noobs.carpool.models.VerifySmsCodeResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Verification extends AppCompatActivity implements View.OnClickListener{

    Button btnGetCode, btnVerify;
    EditText txtNumber, txtVerify;
    String requestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnGetCode:

                long number = Long.parseLong(txtNumber.getText().toString().trim());
                final Verification thisRef = this;
                Call<SmsCodeResponse> code = Api.Verification.getCode(new SmsCode(number), new RetrofitCallback<SmsCodeResponse>(this){
                    @CallSuper
                    @Override
                    public void onResponse(Call<SmsCodeResponse> call, Response<SmsCodeResponse> response)
                    {
                        super.onResponse(call, response);
                        try
                            {
                                //fetching request_id from response
                                requestId = response.body().getRequestId();
                                Toast.makeText(thisRef, response.errorBody().string(), Toast.LENGTH_SHORT).show();

                            } catch (IOException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(thisRef, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                    }
                });

                break;


            case R.id.btnVerify:

                int verificationCode = Integer.parseInt(txtVerify.getText().toString().trim());
                VerifySmsCode verificationCodeObj = new VerifySmsCode(requestId, verificationCode);
                Call<VerifySmsCodeResponse> verifySmsCodeCall = Api.Verification.verifyCode(verificationCodeObj, new RetrofitCallback<VerifySmsCodeResponse>(this));

                break;
        }
    }
}

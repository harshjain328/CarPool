package com.noobs.carpool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noobs.carpool.api.Api;
import com.noobs.carpool.api.ApiClient;
import com.noobs.carpool.api.RetrofitCallback;
import com.noobs.carpool.models.SmsCode;
import com.noobs.carpool.models.SmsCodeResponse;
import com.noobs.carpool.models.VerifySmsCode;
import com.noobs.carpool.models.VerifySmsCodeResponse;
import com.noobs.carpool.services.listeners.SmsListener;
import com.noobs.carpool.services.SmsReader;
import retrofit2.Call;
import retrofit2.Response;


public class Verification extends AppCompatActivity  implements SmsListener{

    Button btnGetCode, btnVerify;
    EditText txtNumber, txtVerify;
    TextView txtResult; // shows the verification process
    String requestId; // temporarily holds request_id sent by server for verification process
    final Context context = this;

    SmsReader smsReader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        txtNumber = (EditText) findViewById(R.id.txtNumber);
        txtVerify = (EditText) findViewById(R.id.txtVerify);

        btnGetCode = (Button) findViewById(R.id.btnGetCode);
        btnVerify = (Button) findViewById(R.id.btnVerify);

        txtResult = (TextView) findViewById(R.id.txtResult);

        handleGetCode();
        handleVerification();


        // instantiating and setting SmsReader(BroadcastReceiver)
        smsReader =  new SmsReader(this);
        smsReader.setSenderName("VERIFY");

        //adding SmsReader
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(500);
        registerReceiver(smsReader, intentFilter);

    }



    public void handleGetCode() {
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long number = Long.parseLong(txtNumber.getText().toString().trim());

                Call<SmsCodeResponse> codeResponse = Api.Verification.getCode(new SmsCode(number), new RetrofitCallback<SmsCodeResponse>(context) {
                    @CallSuper
                    @Override
                    public void onResponse(Call<SmsCodeResponse> call, Response<SmsCodeResponse> response) {
                        super.onResponse(call, response);
                        try {
                            //fetching request_id from response
                            requestId = response.body().getRequestId();
                            txtResult.setText("Code Sent( request_id => "+requestId+")");

                            // Storing request_id in shared-preference for later verification
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("request_id", requestId);
                            editor.apply();

                            Toast.makeText(context, "Successful : "+response.body().toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                Toast.makeText(context, "Class : " + codeResponse, Toast.LENGTH_LONG).show();
            }
        });
    }



    /**
     * This function takes verification code as parameter and request_id from shared-preference
     * for verification process.
     * @param code to verify
     */
    public void verifyCode(String code){
        //String verificationCode = txtVerify.getText().toString().trim();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String request_id = preferences.getString("request_id", "None");

        VerifySmsCode verificationCodeObj = new VerifySmsCode(request_id, code);
        Toast.makeText(context, "verifying with : " + verificationCodeObj, Toast.LENGTH_LONG).show();
        Call<VerifySmsCodeResponse> verifySmsCodeCall = Api.Verification.verifyCode(verificationCodeObj, new RetrofitCallback<VerifySmsCodeResponse>(context) {

            @CallSuper
            @Override
            public void onResponse(Call<VerifySmsCodeResponse> call, Response<VerifySmsCodeResponse> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    try {
                        int status = Integer.parseInt(response.body().getStatus());
                        if(status == 0){
                            txtResult.setTextColor(Color.GREEN);
                            txtResult.setText("Number Verified( status => "+status+")");

                            //opening registration screen
                            Intent registrationActivity = new Intent(context, RegistrationActivity.class);
                            startActivity(registrationActivity);

                        }else{
                            txtResult.setTextColor(Color.RED);
                            txtResult.setText("Unable To Verify( status => "+status+")");
                        }

                        Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_LONG).show();
                        System.out.print(response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void handleVerification() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = txtVerify.getText().toString().trim();
                verifyCode(verificationCode);
            }
        });
    }


    /* This will be called when SmsReader receives a new message
        and code verification should be handled here.
     */
    @Override
    public void onMessageReceive(String msgText) {

        // message body contains to number(code & validation time), so here
        // I'm extracting first number(verification code) from message body
        String verificationCode = msgText.replaceAll("[^-?0-9]+", " ").trim().split(" ")[0];
        verifyCode(verificationCode);


    }
}
package com.noobs.carpool;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Verification extends AppCompatActivity  {

    Button btnGetCode, btnVerify;
    EditText txtNumber, txtVerify;
    TextView txtResult; // shows the verification process
    String requestId; // temporarily holds request_id sent by server for verification process
    final Context context = this;


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

    public void handleVerification() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int verificationCode = Integer.parseInt(txtVerify.getText().toString().trim());
                VerifySmsCode verificationCodeObj = new VerifySmsCode(requestId, verificationCode);
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
        });
    }
}
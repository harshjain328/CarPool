package com.noobs.carpool;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.noobs.carpool.api.Api;
import com.noobs.carpool.api.RetrofitCallback;
import com.noobs.carpool.models.RegistrationResponse;
import com.noobs.carpool.utils.ImageUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText txtPhoneNo, txtUsername;
    private Button btnUploadImage, btnCaptureImage, btnRegister;
    private ImageView imgProfilePic;
    private Context context = this;

    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_IMAGE_REQUEST = 2088;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtUsername = (EditText) findViewById(R.id.txtUsername);

        btnCaptureImage = (Button) findViewById(R.id.btnCaptureImage);

        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);
        btnCaptureImage = (Button) findViewById(R.id.btnCaptureImage);

        // Handling Button events
        handleUploadProfileImage();
        handleCaptureImage();
        handlRegistration();
    }

    private void handleUploadProfileImage() {
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
    }

    private void handlRegistration() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = txtPhoneNo.getText().toString();
                String username = txtUsername.getText().toString();

                Bitmap bmp = ((BitmapDrawable)imgProfilePic.getDrawable()).getBitmap();

                String base64 = ImageUtil.convertToBase64(bmp);
                Log.i("BASE64", base64);

                Toast.makeText(context, phone + ", " + username + bmp, Toast.LENGTH_LONG).show();
                com.noobs.carpool.models.Registration registration = new com.noobs.carpool.models.Registration(username, phone, base64);
                Api.Users.registerUser(registration, new RetrofitCallback<RegistrationResponse>(context){
                    @CallSuper
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        super.onResponse(call, response);
                        Toast.makeText(context, "Result : " + response.body().toString(), Toast.LENGTH_LONG).show();

                        // showing result in dialog
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        dialogBuilder.setTitle("Registration Complete");
                        dialogBuilder.setMessage(response.body().toString());
                        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = dialogBuilder.create();
                        alert.show();

                    }
                });
            }
        });
    }


    private void handleCaptureImage() {
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgProfilePic.setImageBitmap(photo);
        }
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                imgProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }




}
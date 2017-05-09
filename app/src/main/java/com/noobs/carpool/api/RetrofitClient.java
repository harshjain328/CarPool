/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noobs.carpool.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author deepak
 */

public class RetrofitClient {
    
    private final static String API_BASE_URL = "http://letachal.pe.hu/api/";
    
    public static Retrofit getClient(){

        //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
              @Override
              public Response intercept(Chain chain) throws IOException {
              Request newRequest  = chain.request().newBuilder()
                //.addHeader("Authorization", "Bearer " + "")
                .build();
              return chain.proceed(newRequest);
              }
        }).build();

        return new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient)
                        .build();

    }
    
}

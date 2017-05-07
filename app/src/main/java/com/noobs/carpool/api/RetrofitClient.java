/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noobs.carpool.api;

import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author deepak
 */
public class RetrofitClient {
    
    public static String API_BASE_URL = "http://letachal.pe.hu/api/";
    
    public static Retrofit getClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    

        Retrofit.Builder builder =  
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    );

        Retrofit retrofit =  
            builder
                .client(
                    httpClient.build()
                )
                .build();
        return retrofit;
    }
    
}

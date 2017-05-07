package com.noobs.carpool.api;


import android.content.Context;
import android.support.annotation.CallSuper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public  class RetrofitCallback<T> implements Callback<T>
{

    final Context context;

    public RetrofitCallback(Context context)
    {
        this.context = context;
    }

    @CallSuper
    @Override
    public void onResponse(Call<T> call, Response<T> response)
    {
        //if (!response.isSuccessful() && response.errorBody() != null)
        if (response.errorBody() != null)
        {
            try
            {
                Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @CallSuper
    @Override
    public void onFailure(Call<T> call, Throwable t)
    {
        Toast.makeText(context, "FAILURE: " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
package com.example.baitur.remote_data;

import com.example.baitur.constant.ConstantApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private API api;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ConstantApi.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public API getApi() {
        return api;
    }
}
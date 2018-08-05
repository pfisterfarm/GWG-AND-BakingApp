package com.pfisterfarm.bakingapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class BakingClient {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static BakingInterface retrofit = null;

    public static BakingInterface getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().create();
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create(gson)).
                    callFactory(okHttpClientBuilder.build()).
                    build().create(BakingInterface.class);
        }
        return retrofit;
    }

}

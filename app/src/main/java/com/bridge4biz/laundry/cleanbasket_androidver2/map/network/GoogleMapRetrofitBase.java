package com.bridge4biz.laundry.cleanbasket_androidver2.map.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gingeraebi on 2016. 7. 7..
 */
public class GoogleMapRetrofitBase {

    private Retrofit retrofit;
    private static GoogleMapRetrofitBase instance;

    public static GoogleMapRetrofitBase getInstance() {
        if (instance == null) {
            instance = new GoogleMapRetrofitBase();
        }

        return instance;
    }

    private GoogleMapRetrofitBase() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://maps.googleapis.com/maps/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}

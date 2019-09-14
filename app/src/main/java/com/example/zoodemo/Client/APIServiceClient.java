package com.example.zoodemo.Client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceClient {
    private static APIClient apiClient;
    private static String BASE_URL = "https://data.taipei/opendata/datalist/";
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    public static APIClient getInstance() {
        if (apiClient == null) {
            synchronized (APIServiceClient.class) {
                new APIServiceClient();
            }
        }
        return apiClient;
    }

    private APIServiceClient() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(APIClient.class);
    }
}

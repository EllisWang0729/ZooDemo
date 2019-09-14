package com.example.zoodemo.Client;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIClient {
    @GET("apiAccess")
    Call<Object> zoo(@Query("scope") String token,
                     @Query("rid") String rid);

}

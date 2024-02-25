package com.example.demo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @GET("/test1/{id}")
    Call<Object> test1(@Path("id") String id);
    @POST("/test2")
    Call<Object> test2(@Body Object body);
}

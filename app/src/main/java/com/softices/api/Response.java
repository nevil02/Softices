package com.softices.api;

import com.softices.models.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Response {
    @POST("login")
    Call<User> login(@Body HashMap<String, String> body);
}

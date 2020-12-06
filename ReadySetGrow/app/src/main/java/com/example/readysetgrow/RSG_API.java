package com.example.readysetgrow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RSG_API {
    // retrofit auto gen if annotated
    // the stuff after the slash of base url, relative url

    // get all
    @GET("users")
    Call<List<User>> getUsers();

    // get one
    @GET("user/{username}")
    Call<List<User>> getUser(@Path("username") String u);

    // post one
    @POST("user")
    Call<User> createUser(@Body User u); //auto json-ed by gson
}

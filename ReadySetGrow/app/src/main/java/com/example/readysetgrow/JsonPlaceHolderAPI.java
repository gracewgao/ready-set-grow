package com.example.readysetgrow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderAPI {

    @GET("posts") // the stuff after the slash of base url, relative url
    Call<List<Post>> getPosts();
    // retrofit auto gen if annotated
}

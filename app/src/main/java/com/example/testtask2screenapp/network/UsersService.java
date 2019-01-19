package com.example.testtask2screenapp.network;


import com.example.testtask2screenapp.pojo.RandomuserResponse;

import io.reactivex.Single;
import retrofit2.http.GET;


public interface UsersService {
    @GET("?")
    Single<RandomuserResponse> queryUsers();
}

package com.example.testtask2screenapp.network;


import com.example.testtask2screenapp.pojo.RandomuserResponse;
import com.example.testtask2screenapp.pojo.Result;

import java.util.List;

import io.reactivex.Single;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface UsersService {
    @GET("?")
    Single<RandomuserResponse> queryUsers();
}

package com.ralph.mydashbord.service;

import com.ralph.mydashbord.model.LoginRequest;
import com.ralph.mydashbord.model.LoginResponse;
import com.ralph.mydashbord.model.RegisterRequest;
import com.ralph.mydashbord.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterResponse> userRegister(@Body RegisterRequest registerRequest);

}

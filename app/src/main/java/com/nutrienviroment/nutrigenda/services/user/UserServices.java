package com.nutrienviroment.nutrigenda.services.user;

import com.nutrienviroment.nutrigenda.models.user.TokenResponse;
import com.nutrienviroment.nutrigenda.models.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServices {
    @POST("api/user")
    Call<Void> registerUser(@Body User user);

    @POST("api/user")
    Call<TokenResponse> loginUser(@Body User user);
}

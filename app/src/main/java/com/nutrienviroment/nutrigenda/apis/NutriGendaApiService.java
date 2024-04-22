package com.nutrienviroment.nutrigenda.apis;

import com.nutrienviroment.nutrigenda.models.TokenModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NutriGendaApiService {
    @GET("users/{userId}")
    Call<TokenModel> getUserToken(@Path("userId") String userId);
}

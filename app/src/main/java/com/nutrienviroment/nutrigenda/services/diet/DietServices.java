package com.nutrienviroment.nutrigenda.services.diet;

import com.nutrienviroment.nutrigenda.models.diet.Diet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DietServices {
    @GET("api/diet")
    Call<Diet> getDietPlan(@Query("week") int week);
}
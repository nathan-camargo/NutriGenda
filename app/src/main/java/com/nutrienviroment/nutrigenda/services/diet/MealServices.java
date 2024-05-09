package com.nutrienviroment.nutrigenda.services.diet;

import com.nutrienviroment.nutrigenda.models.diet.Meal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MealServices {
    @GET("api/meal/{mealId}")
    Call<Meal> getMealById(@Path("mealId") String mealId);

    @POST("api/meal")
    Call<Meal> createMeal(@Body Meal mealDto);

    @PUT("api/meal/{mealId}")
    Call<Void> updateMeal(@Path("mealId") String mealId, @Body Meal mealDto);

    @DELETE("api/meal/{mealId}")
    Call<Void> deleteMeal(@Path("mealId") String mealId);
}

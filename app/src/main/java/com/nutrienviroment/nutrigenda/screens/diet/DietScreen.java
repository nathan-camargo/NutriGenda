package com.nutrienviroment.nutrigenda.screens.diet;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.models.diet.Diet;
import com.nutrienviroment.nutrigenda.services.diet.DietServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietScreen extends AppCompatActivity {
    private DietServices apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_screen);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nutrigendaapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(DietServices.class);

        fetchDietPlan(1);
    }

    private void fetchDietPlan(int week) {
        apiService.getDietPlan(week).enqueue(new Callback<Diet>() {
            @Override
            public void onResponse(Call<Diet> call, Response<Diet> response) {
                if (response.isSuccessful()) {
                    Diet diet = response.body();
                    Toast.makeText(DietScreen.this, "Dados carregados com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DietScreen.this, "Erro ao carregar dados: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Diet> call, Throwable t) {
                Toast.makeText(DietScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

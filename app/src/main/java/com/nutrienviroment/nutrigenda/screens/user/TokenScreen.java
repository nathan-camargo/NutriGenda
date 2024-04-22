package com.nutrienviroment.nutrigenda.screens.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.apis.NutriGendaApiService;
import com.nutrienviroment.nutrigenda.models.TokenModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenScreen extends AppCompatActivity {

    private NutriGendaApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.token_screen);

        // Inicializa o Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://yourapi.com/") // Substitua pela sua base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(NutriGendaApiService.class);

        // Exemplo de chamada de API para obter um token
        Call<TokenModel> call = apiService.getUserToken("user_id_aqui"); // Substitua pelo ID do usuário
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()) {
                    // Use o token como necessário
                    TokenModel tokenModel = response.body();
                } else {
                    // Handle API errors here
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                // Handle failure, como uma falha de rede
            }
        });
    }
}

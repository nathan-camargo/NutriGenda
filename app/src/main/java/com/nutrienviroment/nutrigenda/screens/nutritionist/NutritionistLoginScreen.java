package com.nutrienviroment.nutrigenda.screens.nutritionist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.models.nutritionist.Nutritionist;
import com.nutrienviroment.nutrigenda.models.user.TokenResponse;
import com.nutrienviroment.nutrigenda.services.nutritionist.NutritionistServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NutritionistLoginScreen extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private NutritionistServices apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutritionist_login_screen);
        initializeUI();
        setupRetrofit();
    }

    private void initializeUI() {
        emailEditText = findViewById(R.id.nutriLogin_emailEditText);
        passwordEditText = findViewById(R.id.nutriLogin_passwordEditText);
        Button loginButton = findViewById(R.id.nutriLogin_loginButton);

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            attemptLogin(email, password);
        });
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nutrigendaapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(NutritionistServices.class);
    }

    private void attemptLogin(String email, String password) {
        Nutritionist nutritionist = new Nutritionist(email, password);
        Call<TokenResponse> call = apiService.loginNutritionist(nutritionist);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(NutritionistLoginScreen.this, "Login bem-sucedido! Token: " + response.body().getToken(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(NutritionistLoginScreen.this, "Erro no login: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(NutritionistLoginScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

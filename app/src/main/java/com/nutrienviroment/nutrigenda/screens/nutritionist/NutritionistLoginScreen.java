package com.nutrienviroment.nutrigenda.screens.nutritionist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
    private NutritionistServices apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutritionist_login_screen);
        EdgeToEdge.enable(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nutrigendaapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(NutritionistServices.class);

        Button loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(view -> {
            String email = ((EditText) findViewById(R.id.editTextText)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextText2)).getText().toString();

            Nutritionist nutritionist = new Nutritionist(email, password);
            loginNutritionist(nutritionist);
        });
    }

    private void loginNutritionist(Nutritionist nutritionist) {
        Call<TokenResponse> call = apiService.loginNutritionist(nutritionist);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    // Aqui você pode salvar o token em SharedPreferences ou gerenciar o acesso
                    Toast.makeText(NutritionistLoginScreen.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NutritionistLoginScreen.this, "Erro no login: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(NutritionistLoginScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

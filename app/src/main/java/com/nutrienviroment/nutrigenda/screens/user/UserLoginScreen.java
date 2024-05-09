package com.nutrienviroment.nutrigenda.screens.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.models.user.TokenResponse;
import com.nutrienviroment.nutrigenda.models.user.User;
import com.nutrienviroment.nutrigenda.screens.diet.DietScreen;
import com.nutrienviroment.nutrigenda.services.user.UserServices;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginScreen extends AppCompatActivity {
    private UserServices apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_screen);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nutrigendaapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(UserServices.class);

        Button loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(view -> {
            String email = ((EditText) findViewById(R.id.editTextText)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextText2)).getText().toString();

            User user = new User(email, password);
            loginUser(user);
        });
    }

    private void loginUser(User user) {
        Call<TokenResponse> call = apiService.loginUser(user);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                    Toast.makeText(UserLoginScreen.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                    // Após sucesso no login, iniciar a DietScreen
                    Intent intent = new Intent(UserLoginScreen.this, DietScreen.class);
                    intent.putExtra("EXTRA_SESSION_TOKEN", token); // Passando o token como extra
                    startActivity(intent);
                    finish(); // Finaliza a tela de login para não retornar quando pressionar voltar
                } else {
                    Toast.makeText(UserLoginScreen.this, "Erro no login: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(UserLoginScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

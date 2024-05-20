package com.nutrienviroment.nutrigenda.screens.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginScreen extends AppCompatActivity {
    private static final String TAG = "UserLoginScreen";
    private UserServices apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_screen);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5136")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(UserServices.class);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String savedToken = sharedPreferences.getString("token", null);
        String savedUserId = sharedPreferences.getString("userId", null);
        if (savedToken != null && savedUserId != null) {
            navigateToDietScreen(savedToken, savedUserId);
            finish();
            return;
        }

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
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    String userId = response.body().getUserId();
                    Toast.makeText(UserLoginScreen.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Login bem-sucedido! Token: " + token + ", UserId: " + userId);

                    saveUserCredentials(token, userId);
                    navigateToDietScreen(token, userId);
                    finish();
                } else {
                    Toast.makeText(UserLoginScreen.this, "Erro no login: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Erro no login: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(UserLoginScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Falha na comunicação: " + t.getMessage(), t);
            }
        });
    }

    private void saveUserCredentials(String token, String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("userId", userId);
        editor.apply();
    }

    private void navigateToDietScreen(String token, String userId) {
        Intent intent = new Intent(UserLoginScreen.this, DietScreen.class);
        intent.putExtra("EXTRA_SESSION_TOKEN", token);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }
}

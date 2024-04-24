package com.nutrienviroment.nutrigenda.screens.user;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.services.user.UserServices;
import com.nutrienviroment.nutrigenda.models.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegisterScreen extends AppCompatActivity {
     private UserServices apiService;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.user_register_screen);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:5136/api/user")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(UserServices.class);

            Button registerButton = findViewById(R.id.button2);
            registerButton.setOnClickListener(view -> {
                String name = ((EditText) findViewById(R.id.editTextText7)).getText().toString();
                String email = ((EditText) findViewById(R.id.editTextText)).getText().toString();
                String password = ((EditText) findViewById(R.id.editTextText2)).getText().toString();

                User user = new User(name, email, password);
                registerUser(user);
            });
        }

        private void registerUser(User user) {
            Call<Void> call = apiService.registerUser(user);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(UserRegisterScreen.this, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserRegisterScreen.this, "Erro no registro: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(UserRegisterScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
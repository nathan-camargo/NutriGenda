package com.nutrienviroment.nutrigenda.screens.nutritionist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.models.nutritionist.Nutritionist;
import com.nutrienviroment.nutrigenda.services.nutritionist.NutritionistServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NutritionistRegisterScreen extends AppCompatActivity {
    private NutritionistServices apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutritionist_register_screen);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nutrigendaapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiService = retrofit.create(NutritionistServices.class);

        Button registerButton = findViewById(R.id.nutriRegister_registerButton);
        registerButton.setOnClickListener(view -> {
            String name = ((EditText) findViewById(R.id.nutriRegister_nameEditText)).getText().toString();
            String email = ((EditText) findViewById(R.id.nutriRegister_emailEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.nutriRegister_passwordEditText)).getText().toString();
            String crn = ((EditText) findViewById(R.id.nutriRegister_crnEditText)).getText().toString();

            Nutritionist nutritionist = new Nutritionist(name, email, password, crn);
            registerNutritionist(nutritionist);
        });

        Button alreadyAccountButton = findViewById(R.id.nutriRegister_alreadyAccountButton);

        alreadyAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(NutritionistRegisterScreen.this, NutritionistLoginScreen.class);
            startActivity(intent);
        });

    }

    private void registerNutritionist(Nutritionist nutritionist) {
        Call<Void> call = apiService.registerNutritionist(nutritionist);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NutritionistRegisterScreen.this, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NutritionistRegisterScreen.this, "Erro no registro: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("NutritionistRegister", "Falha na comunicação: " + t.getMessage(), t);
                Toast.makeText(NutritionistRegisterScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

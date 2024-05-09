package com.nutrienviroment.nutrigenda.screens.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.models.nutritionist.Nutritionist;
import com.nutrienviroment.nutrigenda.models.user.User;
import com.nutrienviroment.nutrigenda.services.nutritionist.NutritionistServices;
import com.nutrienviroment.nutrigenda.services.user.UserServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegisterScreen extends AppCompatActivity {
    private UserServices apiService;
    private NutritionistServices nutritionistService;
    private Spinner nutritionistSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_screen);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nutrigendaapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(UserServices.class);
        nutritionistService = retrofit.create(NutritionistServices.class);
        nutritionistSpinner = findViewById(R.id.spinnerNutritionist);

        loadNutritionists();

        Button registerButton = findViewById(R.id.button2);
        registerButton.setOnClickListener(view -> {
            String name = ((EditText) findViewById(R.id.editTextText7)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextText)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextText2)).getText().toString();
            Nutritionist selectedNutritionist = (Nutritionist) nutritionistSpinner.getSelectedItem();

            User user = new User(name, email, password, selectedNutritionist.getId());
            registerUser(user);
        });
    }

    private void loadNutritionists() {
        Call<List<Nutritionist>> call = nutritionistService.getNutritionists();
        call.enqueue(new Callback<List<Nutritionist>>() {
            @Override
            public void onResponse(Call<List<Nutritionist>> call, Response<List<Nutritionist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayAdapter<Nutritionist> adapter = new ArrayAdapter<>(UserRegisterScreen.this,
                            android.R.layout.simple_spinner_item, response.body());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    nutritionistSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(UserRegisterScreen.this, "Erro ao carregar nutricionistas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Nutritionist>> call, Throwable t) {
                Toast.makeText(UserRegisterScreen.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
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

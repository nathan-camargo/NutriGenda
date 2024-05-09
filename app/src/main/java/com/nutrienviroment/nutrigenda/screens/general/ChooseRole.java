package com.nutrienviroment.nutrigenda.screens.general;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutrienviroment.nutrigenda.R;
import com.nutrienviroment.nutrigenda.screens.nutritionist.NutritionistRegisterScreen;
import com.nutrienviroment.nutrigenda.screens.user.UserChooseScreen;

public class ChooseRole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.general_choose_role);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button patientButton = findViewById(R.id.button6);
        Button nutritionistButton = findViewById(R.id.button7);

        patientButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseRole.this, UserChooseScreen.class);
            startActivity(intent);
        });

        nutritionistButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseRole.this, NutritionistRegisterScreen.class);
            startActivity(intent);
        });
    }
}

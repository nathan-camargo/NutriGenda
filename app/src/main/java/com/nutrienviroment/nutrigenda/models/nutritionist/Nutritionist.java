package com.nutrienviroment.nutrigenda.models.nutritionist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Nutritionist {
    private String Name;
    private String email;
    private String password;
    private String crn;

    public Nutritionist(String name, String email, String password, String crn) {

    }

    public Nutritionist(String email, String password) {

    }
}



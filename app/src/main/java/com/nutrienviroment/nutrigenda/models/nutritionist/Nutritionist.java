package com.nutrienviroment.nutrigenda.models.nutritionist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Nutritionist {
    private String Name;
    private String Email;
    private String PasswordHash;
    private String Crn;

    public Nutritionist(String name, String email, String passwordHash, String crn) {
        this.Name = name;
        this.Email = email;
        this.PasswordHash = passwordHash;
        this.Crn = crn;
    }

    public Nutritionist(String email, String passwordHash) {
        this.Email = email;
        this.PasswordHash = passwordHash;
    }
}



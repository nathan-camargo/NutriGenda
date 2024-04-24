package com.nutrienviroment.nutrigenda.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password){

    }

    public User(String email, String password){

    }
}



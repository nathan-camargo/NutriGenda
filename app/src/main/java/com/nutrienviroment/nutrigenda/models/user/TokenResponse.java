package com.nutrienviroment.nutrigenda.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;

    public String getToken() {
        return token;
    }
}

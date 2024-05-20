package com.nutrienviroment.nutrigenda.models.user;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("userId")
    private String userId;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
}

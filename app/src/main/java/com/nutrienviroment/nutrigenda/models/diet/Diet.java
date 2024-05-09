package com.nutrienviroment.nutrigenda.models.diet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Diet {
    public String Id;
    public String UserId;
    public List<Meal> Meals;
}


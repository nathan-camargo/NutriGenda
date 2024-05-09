package com.nutrienviroment.nutrigenda.models.diet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meal {
    public String Id;
    public String Name;
    public List<FoodItem> FoodItems;
    public String DietId;
}

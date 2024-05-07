package com.nutrienviroment.nutrigenda.models.diet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Diet {
    public int Week;
    public List<String> Meals;
    public String Information;
}

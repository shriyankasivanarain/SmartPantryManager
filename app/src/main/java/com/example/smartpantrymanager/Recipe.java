package com.example.smartpantrymanager;

import java.util.List;

/**
 * Data model representing a Recipe with a name, required ingredients list, and preparation steps.
 */
public class Recipe {
    private String name;
    private List<String> ingredients;
    private String preparationSteps;

    public Recipe(String name, List<String> ingredients, String preparationSteps) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }
}
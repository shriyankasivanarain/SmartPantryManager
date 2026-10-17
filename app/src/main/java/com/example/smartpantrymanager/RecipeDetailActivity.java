package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity representing the Recipe Detail Screen.
 * Displays the full ingredient list and step-by-step cooking instructions for a selected recipe.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Bind UI text elements
        TextView textDetailTitle = findViewById(R.id.textDetailTitle);
        TextView textDetailIngredients = findViewById(R.id.textDetailIngredients);
        TextView textDetailMethod = findViewById(R.id.textDetailMethod);

        // Retrieve recipe details passed via Intent from RecipeActivity
        String recipeName = getIntent().getStringExtra("RECIPE_NAME");
        String recipeIngredients = getIntent().getStringExtra("RECIPE_INGREDIENTS");
        String recipeMethod = getIntent().getStringExtra("RECIPE_METHOD");

        // Set text values
        textDetailTitle.setText(recipeName != null ? recipeName : "Recipe Details");
        textDetailIngredients.setText(recipeIngredients != null ? recipeIngredients : "No ingredients listed.");
        textDetailMethod.setText(recipeMethod != null ? recipeMethod : "No instructions provided.");
    }
}

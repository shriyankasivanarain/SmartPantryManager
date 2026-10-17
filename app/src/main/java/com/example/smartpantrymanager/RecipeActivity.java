package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity responsible for executing the strict recipe-matching algorithm.
 * Evaluates available SQLite pantry items against the pre-defined recipe catalog.
 */
public class RecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecipes;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Initialize database reference
        databaseHelper = new DatabaseHelper(this);

        // Bind RecyclerView
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));

        // Execute recipe matching algorithm and display results
        matchRecipesWithPantry();
    }

    /**
     * Algorithm that fetches current pantry items and checks them against
     * the 15+ pre-defined recipes to find strict matches the user can currently cook.
     */
    private void matchRecipesWithPantry() {
        // 1. Fetch user's active inventory from SQLite database[cite: 3, 5]
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();
        List<String> pantryItemNames = new ArrayList<>();
        for (PantryItem item : pantryItems) {
            pantryItemNames.add(item.getName().toLowerCase().trim());
        }

        // 2. Load the pre-seeded recipes from the repository[cite: 4]
        List<Recipe> allRecipes = RecipeRepository.getAllRecipes();

        // 3. Strict-matching algorithm:
        // A recipe is included ONLY IF every required ingredient is present in the pantry[cite: 5].
        List<Recipe> matchedRecipes = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            boolean hasAllIngredients = true;
            for (String ingredient : recipe.getIngredients()) {
                if (!pantryItemNames.contains(ingredient.toLowerCase().trim())) {
                    hasAllIngredients = false;
                    break;
                }
            }
            if (hasAllIngredients) {
                matchedRecipes.add(recipe);
            }
        }

        // 4. Handle UI feedback if no recipes match[cite: 5]
        if (matchedRecipes.isEmpty()) {
            Toast.makeText(this, "No matching recipes found[cite: 5]. Add more ingredients!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Found " + matchedRecipes.size() + " matching recipe(s)[cite: 5]!", Toast.LENGTH_SHORT).show();
        }

        // Bind matched recipes to the RecyclerView adapter with click handling
        RecipeAdapter recipeAdapter = new RecipeAdapter(matchedRecipes, recipe -> {
            // Use Intent to pass recipe details to RecipeDetailActivity[cite: 6]
            Intent intent = new Intent(RecipeActivity.this, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_NAME", recipe.getName());
            intent.putExtra("RECIPE_INGREDIENTS", String.join(", ", recipe.getIngredients()));
            intent.putExtra("RECIPE_METHOD", recipe.getPreparationSteps());
            startActivity(intent);
        });

        recyclerViewRecipes.setAdapter(recipeAdapter);
    }

    /**
     * Custom Adapter for displaying matched recipes in the RecyclerView.
     */
    private static class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
        private final List<Recipe> recipeList;
        private final OnRecipeClickListener listener;

        public interface OnRecipeClickListener {
            void onRecipeClick(Recipe recipe);
        }

        public RecipeAdapter(List<Recipe> recipeList, OnRecipeClickListener listener) {
            this.recipeList = recipeList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new RecipeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
            Recipe recipe = recipeList.get(position);
            holder.text1.setText("🍳 " + recipe.getName());
            holder.text2.setText("Ingredients: " + String.join(", ", recipe.getIngredients()));

            holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
        }

        @Override
        public int getItemCount() {
            return recipeList.size();
        }

        static class RecipeViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;

            public RecipeViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
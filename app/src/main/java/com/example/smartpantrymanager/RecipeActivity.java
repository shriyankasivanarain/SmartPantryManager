package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
        // 1. Fetch user's active inventory from SQLite database
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();
        List<String> pantryItemNames = new ArrayList<>();
        for (PantryItem item : pantryItems) {
            pantryItemNames.add(item.getName().toLowerCase().trim());
        }

        // 2. Load the 15+ pre-seeded recipes from the repository
        List<Recipe> allRecipes = RecipeRepository.getAllRecipes();

        // 3. Strict-matching algorithm:
        // A recipe is included ONLY IF every required ingredient is present in the pantry.
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

        // 4. Prepare display list for UI feedback
        List<String> displayList = new ArrayList<>();
        if (matchedRecipes.isEmpty()) {
            displayList.add("No recipes match your pantry yet. Add items like 'eggs', 'milk', and 'butter'!");
            Toast.makeText(this, "No matching recipes found[cite: 5].", Toast.LENGTH_LONG).show();
        } else {
            for (Recipe r : matchedRecipes) {
                displayList.add("🍳 " + r.getName() + " (All ingredients available!)");
            }
            Toast.makeText(this, "Found " + matchedRecipes.size() + " matching recipe(s)[cite: 5]!", Toast.LENGTH_SHORT).show();
        }

        // Bind to adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        recyclerViewRecipes.setAdapter(new RecyclerViewAdapterWrapper(displayList));
    }

    // Simple inner adapter wrapper for string display in RecyclerView
    private static class RecyclerViewAdapterWrapper extends RecyclerView.Adapter<RecyclerViewAdapterWrapper.ViewHolder> {
        private final List<String> items;
        public RecyclerViewAdapterWrapper(List<String> items) { this.items = items; }

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View view = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ((android.widget.TextView) holder.itemView).setText(items.get(position));
        }

        @Override
        public int getItemCount() { return items.size(); }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(android.view.View itemView) { super(itemView); }
        }
    }
}
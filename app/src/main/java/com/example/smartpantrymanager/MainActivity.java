package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declare UI components and database helper references
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;
    private Button btnAddItem, btnViewRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the SQLite Database Helper instance
        databaseHelper = new DatabaseHelper(this);

        // Bind layout views from activity_main.xml to Java variables
        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnViewRecipes = findViewById(R.id.btnViewRecipes);

        // Configure RecyclerView to display items linearly (vertical list)
        recyclerViewPantry.setLayoutManager(new LinearLayoutManager(this));

        // Load current pantry items from the database into the UI list
        loadPantryData();

        // Handle click events for adding a new pantry item
        btnAddItem.setOnClickListener(v -> {
            // Placeholder: Will be linked to an Add Item screen or dialog shortly
            Toast.makeText(MainActivity.this, "Add Item feature coming up next!", Toast.LENGTH_SHORT).show();
        });

        // Handle click events for viewing matching recipes based on pantry inventory
        btnViewRecipes.setOnClickListener(v -> {
            // Placeholder: Will be linked to the Recipe Matching screen shortly
            Toast.makeText(MainActivity.this, "Recipe Matching feature coming up next!", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Helper method to fetch all pantry items from the local SQLite database
     * and bind or update them within the RecyclerView adapter.
     */
    private void loadPantryData() {
        List<PantryItem> pantryItemList = databaseHelper.getAllPantryItems();

        if (pantryAdapter == null) {
            // Initialize the custom adapter with our list and handle item deletion logic
            pantryAdapter = new PantryAdapter(pantryItemList, itemId -> {
                // Delete item from SQLite database using its unique ID
                databaseHelper.deletePantryItem(itemId);
                // Refresh the list data displayed on screen
                loadPantryData();
                Toast.makeText(MainActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
            });
            recyclerViewPantry.setAdapter(pantryAdapter);
        } else {
            // Update existing adapter dataset dynamically
            pantryAdapter.updateData(pantryItemList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh pantry data automatically whenever the activity resumes/focuses
        loadPantryData();
    }
}
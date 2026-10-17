package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Main Activity class representing the dashboard for the Smart Pantry Manager.
 * Handles displaying pantry inventory, adding new items via a Dialog, and deleting items.
 */
public class MainActivity extends AppCompatActivity {

    // UI elements and Database reference variables
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SQLite Database Helper instance
        databaseHelper = new DatabaseHelper(this);

        // Bind layout view components
        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);
        Button btnAddItem = findViewById(R.id.btnAddItem);
        Button btnViewRecipes = findViewById(R.id.btnViewRecipes);
        Button btnSettings = findViewById(R.id.btnSettings);

        // Set up vertical layout for the RecyclerView list
        recyclerViewPantry.setLayoutManager(new LinearLayoutManager(this));

        // Load existing pantry items from SQLite database on app start
        loadPantryData();

        // Listener to trigger the Add Item dialog popup
        btnAddItem.setOnClickListener(v -> showAddItemDialog());

        // Listener to launch the Suggested Recipes screen using an Intent
        btnViewRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
            startActivity(intent);
        });

        // Listener to launch the Settings screen using an Intent
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Builds and displays an AlertDialog containing input fields for adding
     * a new PantryItem into the SQLite database.
     */
    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Pantry Item");

        // Construct programmatically created layout for user input
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(50, 40, 50, 10);

        final EditText inputName = new EditText(this);
        inputName.setHint("Item Name (e.g., Eggs, Milk)");
        container.addView(inputName);

        final EditText inputQuantity = new EditText(this);
        inputQuantity.setHint("Quantity (e.g., 2, 5)");
        inputQuantity.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        container.addView(inputQuantity);

        builder.setView(container);

        // Set up Save button action
        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = inputName.getText().toString().trim();
            String quantityStr = inputQuantity.getText().toString().trim();

            // Validate inputs
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(quantityStr)) {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);

            // Insert new item into SQLite database using all 4 required arguments
            boolean isAdded = databaseHelper.addPantryItem(name, quantity, "General", "");

            if (isAdded) {
                Toast.makeText(MainActivity.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                loadPantryData(); // Refresh list display
            } else {
                Toast.makeText(MainActivity.this, "Error adding item to database", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up Cancel button action
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    /**
     * Fetches all pantry items from SQLite and binds or updates them in the RecyclerView.
     */
    private void loadPantryData() {
        List<PantryItem> pantryItemList = databaseHelper.getAllPantryItems();

        if (pantryAdapter == null) {
            // Initialize custom adapter with item list and delete callback handler
            pantryAdapter = new PantryAdapter(pantryItemList, itemId -> {
                // Delete item from SQLite database by unique ID
                databaseHelper.deletePantryItem(itemId);
                loadPantryData(); // Refresh UI after deletion
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            });
            recyclerViewPantry.setAdapter(pantryAdapter);
        } else {
            // Dynamically refresh adapter dataset
            pantryAdapter.updateData(pantryItemList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Auto-refresh list when returning to this activity
        loadPantryData();
    }
}
package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the SQLite database for the Smart Pantry Manager app.
 * Handles table creation, pantry CRUD operations, and pre-loading recipes.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_PANTRY = "pantry";
    public static final String TABLE_RECIPES = "recipes";

    // Pantry Table Columns
    public static final String COLUMN_PANTRY_ID = "id";
    public static final String COLUMN_PANTRY_NAME = "name";
    public static final String COLUMN_PANTRY_QUANTITY = "quantity";
    public static final String COLUMN_PANTRY_UNIT = "unit";
    public static final String COLUMN_PANTRY_EXPIRY = "expiry_date";

    // Recipes Table Columns
    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_RECIPE_INGREDIENTS = "ingredients";
    public static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Pantry Table
        String CREATE_PANTRY_TABLE = "CREATE TABLE " + TABLE_PANTRY + "("
                + COLUMN_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PANTRY_NAME + " TEXT, "
                + COLUMN_PANTRY_QUANTITY + " REAL, "
                + COLUMN_PANTRY_UNIT + " TEXT, "
                + COLUMN_PANTRY_EXPIRY + " TEXT" + ")";
        db.execSQL(CREATE_PANTRY_TABLE);

        // Create Recipes Table
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECIPE_NAME + " TEXT, "
                + COLUMN_RECIPE_INGREDIENTS + " TEXT, "
                + COLUMN_RECIPE_INSTRUCTIONS + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);

        // Pre-load default recipes (Seeding the database)
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    // ================= PANTRY CRUD OPERATIONS =================

    // CREATE: Add a new pantry item
    public boolean addPantryItem(String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PANTRY_NAME, name);
        values.put(COLUMN_PANTRY_QUANTITY, quantity);
        values.put(COLUMN_PANTRY_UNIT, unit);
        values.put(COLUMN_PANTRY_EXPIRY, expiryDate);

        long result = db.insert(TABLE_PANTRY, null, values);
        db.close();
        return result != -1; // returns true if insertion was successful
    }

    // READ: Get all pantry items
    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> pantryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PANTRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_UNIT));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_EXPIRY));

                PantryItem item = new PantryItem(id, name, quantity, unit, expiry);
                pantryList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pantryList;
    }

    // UPDATE: Update an existing pantry item
    public boolean updatePantryItem(int id, String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PANTRY_NAME, name);
        values.put(COLUMN_PANTRY_QUANTITY, quantity);
        values.put(COLUMN_PANTRY_UNIT, unit);
        values.put(COLUMN_PANTRY_EXPIRY, expiryDate);

        int rowsAffected = db.update(TABLE_PANTRY, values, COLUMN_PANTRY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    // DELETE: Delete a pantry item by ID
    public void deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, COLUMN_PANTRY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ================= RECIPE METHODS =================

    // READ: Get all recipes from database
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));
                String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENTS));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INSTRUCTIONS));

                Recipe recipe = new Recipe(id, name, ingredients, instructions);
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipeList;
    }

    // SEED: Pre-load 15-20 recipes on first database creation
    private void seedRecipes(SQLiteDatabase db) {
        String[][] sampleRecipes = {
                {"Tomato Pasta", "pasta:200:grams,tomato:3:units,garlic:2:cloves", "Boil pasta. Sauté garlic and chopped tomatoes. Mix together and serve."},
                {"Garlic Rice", "rice:150:grams,garlic:3:cloves,butter:1:units", "Cook rice. Melt butter in pan, fry minced garlic, toss with cooked rice."},
                {"Boiled Eggs", "egg:2:units", "Place eggs in boiling water for 8 minutes. Peel and serve."},
                {"Simple Omelette", "egg:3:units,butter:1:units,salt:1:pinch", "Beat eggs with salt. Melt butter in pan, pour eggs, cook until firm."},
                {"Tomato Soup", "tomato:5:units,garlic:1:cloves,onion:1:units", "Boil tomatoes, onions, and garlic. Blend until smooth and serve warm."},
                {"Butter Rice", "rice:200:grams,butter:2:units", "Cook rice. Stir in butter until melted and fragrant."},
                {"Garlic Bread", "bread:2:slices,butter:1:units,garlic:1:cloves", "Spread butter and minced garlic on bread. Toast until golden brown."},
                {"Scrambled Eggs", "egg:2:units,butter:1:units", "Whisk eggs. Melt butter in pan, scramble eggs on low heat."},
                {"Fried Pasta", "pasta:150:grams,butter:1:units,garlic:2:cloves", "Boil pasta, then pan-fry with butter and garlic."},
                {"Tomato Salad", "tomato:4:units,salt:1:pinch", "Slice tomatoes, sprinkle with salt, and serve fresh."},
                {"Steamed Rice", "rice:200:grams", "Boil water, add rice, and simmer until tender."},
                {"Egg Sandwich", "bread:2:slices,egg:1:units,butter:1:units", "Toast bread with butter. Fry an egg and place between slices."},
                {"Garlic Tomato Sauce", "tomato:4:units,garlic:3:cloves,olive oil:2:tbsp", "Simmer chopped tomatoes and garlic in olive oil until thick."},
                {"Buttered Toast", "bread:2:slices,butter:2:units", "Toast bread and spread butter evenly while warm."},
                {"Boiled Pasta", "pasta:250:grams,salt:1:pinch", "Boil pasta in salted water until al dente, then drain."},
                {"Egg Fried Rice", "rice:200:grams,egg:2:units,butter:1:units", "Fry cooked eggs and rice together in a pan with butter."},
                {"Tomato Garlic Rice", "rice:150:grams,tomato:2:units,garlic:2:cloves", "Cook rice. Sauté garlic and diced tomatoes, then mix into rice."}
        };

        for (String[] r : sampleRecipes) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_RECIPE_NAME, r[0]);
            values.put(COLUMN_RECIPE_INGREDIENTS, r[1]);
            values.put(COLUMN_RECIPE_INSTRUCTIONS, r[2]);
            db.insert(TABLE_RECIPES, null, values);
        }
    }
}
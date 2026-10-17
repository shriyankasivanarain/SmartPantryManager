package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper manages the local SQLite database for the Smart Pantry Manager,
 * handling table creation, data insertion, retrieval, and deletion operations.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database metadata constants
    private static final String DATABASE_NAME = "pantry.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_NAME = "pantry_table";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_QUANTITY = "QUANTITY";
    private static final String COLUMN_CATEGORY = "CATEGORY";
    private static final String COLUMN_EXPIRY = "EXPIRY";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the SQLite pantry inventory table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_EXPIRY + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if version changes and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserts a new pantry item into the SQLite database with 4 arguments.
     *
     * @param name     Name of the item
     * @param quantity Quantity available
     * @param category Category type (e.g., General, Dairy)
     * @param expiry   Expiry details
     * @return boolean True if insertion succeeded, false otherwise
     */
    public boolean addPantryItem(String name, int quantity, String category, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_EXPIRY, expiry);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        // Returns true if insertion was successful
        return result != -1;
    }

    /**
     * Retrieves all stored pantry items from the SQLite database.
     *
     * @return List of PantryItem objects
     */
    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> pantryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY));

                PantryItem item = new PantryItem(id, name, quantity, category, expiry);
                pantryList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pantryList;
    }

    /**
     * Deletes a specific pantry item from the database by its unique ID.
     *
     * @param id Unique database row ID
     * @return int Number of rows affected
     */
    public int deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}
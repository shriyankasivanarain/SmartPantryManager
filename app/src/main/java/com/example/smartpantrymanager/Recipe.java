package com.example.smartpantrymanager;

/**
 * Represents a single recipe in the app's recipe collection.
 * Stores the recipe name, required ingredients list, and preparation steps.
 */
public class Recipe {

    // Member variables for the recipe
    private int id;                     // Unique database identifier for the recipe
    private String name;                // Name of the dish (e.g., "Tomato Rice Soup")
    private String ingredientsRequired; // List of required ingredients formatted for matching
    private String instructions;        // Step-by-step preparation instructions

    /**
     * Constructor to initialize a Recipe object.
     *
     * @param id                  Unique database ID
     * @param name                Name of the recipe
     * @param ingredientsRequired Required ingredients
     * @param instructions        Preparation instructions
     */
    public Recipe(int id, String name, String ingredientsRequired, String instructions) {
        this.id = id;
        this.name = name;
        this.ingredientsRequired = ingredientsRequired;
        this.instructions = instructions;
    }

    // ================= Getters and Setters =================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredientsRequired() {
        return ingredientsRequired;
    }

    public void setIngredientsRequired(String ingredientsRequired) {
        this.ingredientsRequired = ingredientsRequired;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
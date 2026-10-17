package com.example.smartpantrymanager;

/**
 * Represents a single ingredient item in the user's pantry.
 * Stores information such as name, quantity, unit, and optional expiry date.
 */
public class PantryItem {

    // Member variables for the pantry item
    private int id;                 // Unique database identifier for the item
    private String name;            // Name of the ingredient (e.g., "Tomato", "Rice")
    private double quantity;        // Numerical quantity available
    private String unit;            // Measurement unit (e.g., "kg", "grams", "litres", "units")
    private String expiryDate;      // Optional expiry date string

    /**
     * Constructor to initialize a PantryItem object.
     *
     * @param id          Unique database ID
     * @param name        Name of the ingredient
     * @param quantity    Quantity available
     * @param unit        Measurement unit
     * @param expiryDate  Expiry date of the item
     */
    public PantryItem(int id, String name, double quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}

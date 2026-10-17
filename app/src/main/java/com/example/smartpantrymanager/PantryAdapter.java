package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Custom RecyclerView Adapter responsible for binding PantryItem data models
 * to the individual item rows defined in item_pantry.xml.
 */
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    // Holds the dataset of pantry items retrieved from the database
    private List<PantryItem> pantryItemList;

    // Callback interface reference for handling delete clicks in the parent Activity
    private OnItemDeleteListener deleteListener;

    /**
     * Interface contract to pass item deletion events back to MainActivity
     */
    public interface OnItemDeleteListener {
        void onDeleteClick(int itemId);
    }

    /**
     * Constructor to initialize the adapter with the data list and event listener.
     *
     * @param pantryItemList List of PantryItem objects to render
     * @param deleteListener Callback listener to execute when the delete button is clicked
     */
    public PantryAdapter(List<PantryItem> pantryItemList, OnItemDeleteListener deleteListener) {
        this.pantryItemList = pantryItemList;
        this.deleteListener = deleteListener;
    }

    /**
     * Inflates the XML layout file (item_pantry.xml) and creates the ViewHolder instance.
     */
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate individual row UI layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    /**
     * Binds the specific PantryItem data model at a given position to the UI elements.
     */
    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        // Retrieve current PantryItem model
        PantryItem item = pantryItemList.get(position);

        // Populate TextViews with database values
        holder.textItemName.setText(item.getName());
        holder.textItemQuantity.setText("Quantity: " + item.getQuantity());

        // Set listener on the delete button to invoke the callback with item ID
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(item.getId());
            }
        });
    }

    /**
     * Returns the total count of items in the pantry list.
     */
    @Override
    public int getItemCount() {
        return pantryItemList != null ? pantryItemList.size() : 0;
    }

    /**
     * Updates the adapter's dataset dynamically when data changes in SQLite and refreshes UI.
     *
     * @param newItems Updated list of PantryItem objects
     */
    public void updateData(List<PantryItem> newItems) {
        this.pantryItemList = newItems;
        notifyDataSetChanged(); // Notify RecyclerView to refresh all views
    }

    /**
     * ViewHolder class that caches view references for faster lookup during scrolling.
     */
    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        // UI references for row items
        TextView textItemName, textItemQuantity;
        Button btnDelete;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Bind view references from item_pantry.xml layout
            textItemName = itemView.findViewById(R.id.textItemName);
            textItemQuantity = itemView.findViewById(R.id.textItemQuantity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
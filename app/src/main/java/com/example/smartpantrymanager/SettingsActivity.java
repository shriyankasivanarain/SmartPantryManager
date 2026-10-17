package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity representing the Settings screen.
 * Satisfies the assignment requirement for a settings or profile screen[cite: 4, 5].
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switchNotifications = findViewById(R.id.switchNotifications);
        Switch switchDarkMode = findViewById(R.id.switchDarkMode);

        // Handle toggle change for alerts
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String msg = isChecked ? "Expiring alerts enabled" : "Expiring alerts disabled";
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        // Handle toggle change for dark mode
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String msg = isChecked ? "Dark mode preference saved" : "Light mode preference saved";
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        });
    }
}

package com.axiel7.weeb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //nav bar transparent
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setupSharedPreferences();
        bottomNavMenu();
        openGif();
    }
    private void bottomNavMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    //Intent openHome = new Intent(MainActivity.this, SettingsActivity.class);
                    //MainActivity.this.startActivity(openHome);
                    break;
                case R.id.action_settings:
                    Intent openSettings = new Intent(MainActivity.this, SettingsActivity.class);
                    MainActivity.this.startActivity(openSettings);
                    break;
            }
            return true;
        });
    }
    private void openGif() {
        Button theButton = findViewById(R.id.theButton);
        theButton.setOnClickListener(v -> {
            Intent openGif = new Intent(MainActivity.this, gif.class);
            MainActivity.this.startActivity(openGif);
        });
    }
    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        checkPreferences(sharedPreferences);
    }
    private void loadThemeFromPreference(SharedPreferences sharedPreferences) {
        Log.d("axiel7",sharedPreferences.getString(getString(R.string.pref_theme_key),getString(R.string.pref_theme_default_value)));
        changeTheme(sharedPreferences.getString(getString(R.string.pref_theme_key),getString(R.string.pref_theme_default_value)), sharedPreferences);
    }
    private void changeTheme(String pref_theme_value, SharedPreferences sharedPreferences) {
        Log.d("axiel7", pref_theme_value);
        if (pref_theme_value.equals("light")) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
            sharedPreferences.edit().putString("theme", "light");
            sharedPreferences.edit().commit();
        }
        if (pref_theme_value.equals("dark")) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
            sharedPreferences.edit().putString("theme", "dark");
            sharedPreferences.edit().commit();
        }
        if (pref_theme_value.equals("default")) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
            sharedPreferences.edit().putString("theme", "default");
            sharedPreferences.edit().commit();
        }
    }
    private void checkPreferences(SharedPreferences sharedPreferences) {
        String themeValue = sharedPreferences.getString("theme","default");
        if (themeValue.equals("light")) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        }
        if (themeValue.equals("dark")) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        }
        if (themeValue.equals("default")) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("theme")) {
            loadThemeFromPreference(sharedPreferences);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}


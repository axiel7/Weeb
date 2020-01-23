package com.axiel7.weeb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;


public class MainActivity extends AppCompatActivity {
    SharedPref sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState() == 2) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        }
        if (sharedpref.loadNightModeState() == 1) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        }
        if (sharedpref.loadNightModeState() == -1) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button theButton = findViewById(R.id.theButton);
        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens the gif activity:
                Intent myIntent = new Intent(MainActivity.this, gif.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        Button themeButton = findViewById(R.id.themeButton);
        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked(v);
            }
        });
    }

    public void showAlertDialogButtonClicked(final View view) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // add a list
        String[] themeOptions = {getString(R.string.light), getString(R.string.dark), getString(R.string.sys_default)};
        int checkedItem = 2;
        if (sharedpref.loadNightModeState() == 1) {
            checkedItem = 0;
        }
        if (sharedpref.loadNightModeState() == 2) {
            checkedItem = 1;
        }
        builder.setSingleChoiceItems(themeOptions, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO); // 1
                        sharedpref.setNightModeState(1);
                        recreate();
                        break;
                    case 1:
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES); // 2
                        sharedpref.setNightModeState(2);
                        recreate();
                        break;
                    case 2:
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM); // -1
                        sharedpref.setNightModeState(-1);
                        recreate();
                }
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


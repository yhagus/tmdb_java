package net.ariflaksito.tmdbapp_new.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import net.ariflaksito.tmdbapp_new.R;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        setTitle(R.string.about_me);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
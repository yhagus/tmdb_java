package net.ariflaksito.tmdbapp_new;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        setTitle(R.string.about_me);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
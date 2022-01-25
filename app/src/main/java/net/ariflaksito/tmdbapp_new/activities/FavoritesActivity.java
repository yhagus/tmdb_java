package net.ariflaksito.tmdbapp_new.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ariflaksito.tmdbapp_new.R;
import net.ariflaksito.tmdbapp_new.adapters.FavoriteAdapter;
import net.ariflaksito.tmdbapp_new.db.DbHelper;
import net.ariflaksito.tmdbapp_new.models.Movie;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private ArrayList<Movie> movieArrayList;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DbHelper(this);
        adapter = new FavoriteAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.rvFavorite);
        movieArrayList = dbHelper.getAllMovies();
        adapter.setListMovies(movieArrayList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FavoritesActivity.this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(FavoritesActivity.this, MainActivity.class);
        startActivity(i);
    }
}
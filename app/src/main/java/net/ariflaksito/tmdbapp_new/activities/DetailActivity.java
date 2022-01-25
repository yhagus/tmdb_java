package net.ariflaksito.tmdbapp_new.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import net.ariflaksito.tmdbapp_new.R;
import net.ariflaksito.tmdbapp_new.db.DbHelper;

public class DetailActivity extends AppCompatActivity {

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DbHelper(this);

        Bundle extras = getIntent().getExtras();

        ImageView ivBackground = (ImageView) findViewById(R.id.ivBackground);
        ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvRating = (TextView) findViewById(R.id.tvRating);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);

        //get intent extras
        int id = extras.getInt("id");
        String title = extras.getString("title");
        String overview = extras.getString("overview");
        String release_date = extras.getString("release_date");
        String poster_path = extras.getString("poster_path");
        int vote_average = extras.getInt("vote_average");
        String backdrop_path = extras.getString("backdrop_path");

        Button btnAdd = (Button) findViewById(R.id.addToFavorites);
        Button btnDelete = (Button) findViewById(R.id.removeFromFavorites);

        Picasso.get().load(backdrop_path).into(ivBackground);
        Picasso.get().load(poster_path).into(ivPoster);
        tvTitle.setText(title);
        tvRating.setText(String.valueOf(vote_average));
        tvReleaseDate.setText(getString(R.string.release_date) + " : " + release_date);
        tvOverview.setText(overview);

        if (dbHelper.checkMovie(id)){
            btnAdd.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(v -> {
            if (String.valueOf(id).isEmpty()){
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.id_err), Toast.LENGTH_SHORT).show();
            } else if (title.isEmpty()){
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.title_err), Toast.LENGTH_SHORT).show();
            } else if (overview.isEmpty()){
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.overview_err), Toast.LENGTH_SHORT).show();
            } else if (release_date.isEmpty()){
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.rdate_err), Toast.LENGTH_SHORT).show();
            } else if(poster_path.isEmpty()){
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.poster_err), Toast.LENGTH_SHORT).show();
            } else if(String.valueOf(vote_average).isEmpty()){
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.vote_err), Toast.LENGTH_SHORT).show();
            } else if (backdrop_path.isEmpty()){
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.backdrop_err), Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.addMovieDetail(String.valueOf(id),title, overview, release_date, poster_path, String.valueOf(vote_average), backdrop_path);
                Toast.makeText(DetailActivity.this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
                btnAdd.setVisibility(View.GONE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        });

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteMovie(id);
            Toast.makeText(DetailActivity.this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
            btnAdd.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        switch (extras.getString("context")){
            case "favorites":
                Intent fav = new Intent(DetailActivity.this, FavoritesActivity.class);
                startActivity(fav);
                break;
            default:
                Intent main = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(main);
                break;
        }
    }
}
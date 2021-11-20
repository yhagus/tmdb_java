package net.ariflaksito.tmdbapp_new;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        ImageView ivBackground = (ImageView) findViewById(R.id.ivBackground);
        ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvRating = (TextView) findViewById(R.id.tvRating);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);

        Picasso.get().load(extras.getString("backdrop_path")).into(ivBackground);
        Picasso.get().load(extras.getString("poster_path")).into(ivPoster);
        tvTitle.setText(extras.getString("title"));
        tvRating.setText(String.valueOf(extras.getInt("vote_average")));
        tvReleaseDate.setText(getString(R.string.release_date) + " : " + extras.getString("release_date"));
        tvOverview.setText(extras.getString("overview"));

    }
}
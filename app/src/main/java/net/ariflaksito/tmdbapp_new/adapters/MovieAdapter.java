package net.ariflaksito.tmdbapp_new.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.ariflaksito.tmdbapp_new.activities.DetailActivity;
import net.ariflaksito.tmdbapp_new.models.MovieModels;
import net.ariflaksito.tmdbapp_new.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private ArrayList<MovieModels> dataList;

    public MovieAdapter(ArrayList<MovieModels> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card, viewGroup, false);
        return new MovieViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHolder, int i){
        movieViewHolder.tvTitle.setText(dataList.get(i).getTitle());
        Picasso.get().load(dataList.get(i).getPoster_path()).into(movieViewHolder.ivPoster);

        movieViewHolder.ivPoster.setOnClickListener(view -> {
            Intent intent = new Intent(movieViewHolder.ivPoster.getContext(), DetailActivity.class);
            intent.putExtra("id", dataList.get(i).getId());
            intent.putExtra("title", dataList.get(i).getTitle());
            intent.putExtra("overview", dataList.get(i).getOverview());
            intent.putExtra("release_date", dataList.get(i).getRelease_date());
            intent.putExtra("poster_path", dataList.get(i).getPoster_path());
            intent.putExtra("vote_average", dataList.get(i).getVote_average());
            intent.putExtra("backdrop_path", dataList.get(i).getBackdrop_path());

            intent.putExtra("context", "main");
            movieViewHolder.ivPoster.getContext().startActivity(intent);
        });
        
    }

    @Override
    public int getItemCount(){
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private ImageView ivPoster;

        public  MovieViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
        }
    }
}

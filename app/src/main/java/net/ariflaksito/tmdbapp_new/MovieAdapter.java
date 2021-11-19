package net.ariflaksito.tmdbapp_new;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

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
        movieViewHolder.tvReleaseDate.setText(dataList.get(i).getRelease_date());
        movieViewHolder.tvOverview.setText(dataList.get(i).getOverview());
        Picasso.get().load(dataList.get(i).getPoster_path()).into(movieViewHolder.ivPoster);
    }

    @Override
    public int getItemCount(){
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvReleaseDate, tvOverview;
        private ImageView ivPoster;

        public  MovieViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tvReleaseDate);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);

        }
    }
}

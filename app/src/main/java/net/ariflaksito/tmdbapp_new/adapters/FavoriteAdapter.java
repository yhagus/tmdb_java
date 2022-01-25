package net.ariflaksito.tmdbapp_new.adapters;

import android.app.Activity;
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
import net.ariflaksito.tmdbapp_new.R;
import net.ariflaksito.tmdbapp_new.db.DbHelper;
import net.ariflaksito.tmdbapp_new.models.Movie;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.holderItem> {

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public FavoriteAdapter(Activity activity){
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<Movie> getListMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<Movie> listNotes) {
        if (listNotes.size() > 0){
            this.listMovies.clear();
        }
        this.listMovies.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.holderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new holderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.holderItem holder, int i) {
        holder.tvTitle.setText(listMovies.get(i).getTitle());
        Picasso.get().load(listMovies.get(i).getPoster_path()).into(holder.ivPoster);

        holder.ivPoster.setOnClickListener(v -> {
            Intent intent = new Intent(holder.ivPoster.getContext(), DetailActivity.class);
            intent.putExtra("id", listMovies.get(i).getId());
            intent.putExtra("title", listMovies.get(i).getTitle());
            intent.putExtra("overview", listMovies.get(i).getOverview());
            intent.putExtra("release_date", listMovies.get(i).getRelease_date());
            intent.putExtra("poster_path", listMovies.get(i).getPoster_path());
            intent.putExtra("vote_average", listMovies.get(i).getVote_average());
            intent.putExtra("backdrop_path", listMovies.get(i).getBackdrop_path());

            intent.putExtra("context", "favorites");
            holder.ivPoster.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class holderItem extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private ImageView ivPoster;

        public  holderItem(@NonNull View itemView){
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
        }
    }
}

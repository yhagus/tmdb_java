package net.ariflaksito.tmdbapp_new;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.HolderItem> {

    private ArrayList<Favorite> dataList;

    public FavoriteAdapter(ArrayList<Favorite> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public FavoriteAdapter.HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card, viewGroup, false);
        return new HolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.HolderItem holderItem, int i) {
        holderItem.tvTitle.setText(dataList.get(i).getTitle());
        Picasso.get().load(dataList.get(i).getPoster_path()).into(holderItem.ivPoster);

        holderItem.ivPoster.setOnClickListener(v -> {
            Intent intent = new Intent(holderItem.ivPoster.getContext(), DetailActivity.class);
            intent.putExtra("id", dataList.get(i).getId());
            intent.putExtra("title", dataList.get(i).getTitle());
            intent.putExtra("overview", dataList.get(i).getOverview());
            intent.putExtra("release_date", dataList.get(i).getRelease_date());
            intent.putExtra("vote_average", dataList.get(i).getVote_average());
            intent.putExtra("poster_path", dataList.get(i).getPoster_path());
            intent.putExtra("backdrop_path", dataList.get(i).getBackdrop_path());
            holderItem.ivPoster.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class HolderItem extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivPoster;

        public HolderItem(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
        }
    }
}

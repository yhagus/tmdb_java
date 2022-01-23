package net.ariflaksito.tmdbapp_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoritesActivity extends AppCompatActivity {

    private final String URL = "http://192.168.100.28/api-dbmovie/";
    private String api_path = URL + "show.php";
    private String responseString;

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private ArrayList<Favorite> movieDataList;

    private JSONArray rsJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(api_path).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FavoritesActivity.this, "Connect failed", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    response = client.newCall(request).execute();
                    responseString = response.body().string();
                    JSONObject resultJsonObj = new JSONObject(responseString);
                    rsJsonArray = resultJsonObj.getJSONArray("results");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData(rsJsonArray.toString());

                            recyclerView = (RecyclerView) FavoritesActivity.this.findViewById(R.id.rvFavorite);
                            adapter = new FavoriteAdapter(movieDataList);

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FavoritesActivity.this, 3);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void setData(String jsonString){
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            movieDataList = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Favorite player = new Favorite(
                        jsonObject.getInt("id"),
                        jsonObject.getString("title"),
                        jsonObject.getString("overview"),
                        jsonObject.getString("release_date"),
                        jsonObject.getString("poster_path"),
                        jsonObject.getInt("vote_average"),
                        jsonObject.getString("backdrop_path")
                );

                movieDataList.add(player);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
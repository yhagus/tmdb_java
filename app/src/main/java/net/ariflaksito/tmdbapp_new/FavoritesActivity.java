package net.ariflaksito.tmdbapp_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class FavoritesActivity extends AppCompatActivity {

    private final String URL = "http://192.168.100.28/api-dbmovie/";
    private String api_path = URL + "show.php";
    private ProgressDialog progressDialog;
    private JSONArray rsJsonArray;
    private String responseString;

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private ArrayList<Movie> movieDataList;

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

//        new ApiGetAll(this).execute();
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
                Movie player = new Movie(
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

    private class ApiGetAll extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private String responseString;
        private int success = 0;

        ApiGetAll (Context cn){
            mContext = cn;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(getString(R.string.loading_info));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(api_path).build();

            try {
                success = 1;
                Response response = client.newCall(request).execute();
                responseString = response.body().string();
                JSONObject resultJsonObj = new JSONObject(responseString);
                rsJsonArray = resultJsonObj.getJSONArray("results");

            } catch (IOException | JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            progressDialog.dismiss();

            if (success == 1){
                if (null != rsJsonArray){
                    final ArrayList<Map<String, Object>> dataList = new ArrayList<>();

                    for (int i=0; i<rsJsonArray.length();i++){
                        try {
                            JSONObject jsonObject = rsJsonArray.getJSONObject(i);
                            Map<String, Object> itemMap = new HashMap<>();

                            itemMap.put("id", jsonObject.get("id"));
                            itemMap.put("poster_path", jsonObject.get("poster_path"));
                            itemMap.put("title", jsonObject.get("title"));
                            itemMap.put("overview", jsonObject.get("overview"));
                            itemMap.put("release_date", jsonObject.get("release_date"));
                            itemMap.put("vote_average", jsonObject.get("vote_average"));
                            itemMap.put("backdrop_path", jsonObject.get("backdrop_path"));

                            dataList.add(itemMap);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


    }
}
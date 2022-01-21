package net.ariflaksito.tmdbapp_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
    private ProgressDialog progressDialog;
    private JSONArray rsJsonArray;

    private ListView lvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvView = findViewById(R.id.lvItems);

        new ApiGetAll(this).execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                rsJsonArray = resultJsonObj.getJSONArray("result");

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

                            dataList.add(itemMap);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    SimpleAdapter adapter = new SimpleAdapter(mContext, dataList, android.R.layout.simple_list_item_2,
                            new String[]{"title","id"}, new int[]{android.R.id.text1, android.R.id.text2});
                    lvView.setAdapter(adapter);
                }
            }
        }


    }
}
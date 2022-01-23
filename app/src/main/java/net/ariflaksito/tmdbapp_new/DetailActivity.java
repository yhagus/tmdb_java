package net.ariflaksito.tmdbapp_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DetailActivity extends AppCompatActivity {

    private final String URL = "http://192.168.100.28/api-dbmovie/";
    private String api_post = URL + "store.php";

    private ProgressDialog progressDialog;

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
        Button btnAdd = (Button) findViewById(R.id.addToFavorites);

        Picasso.get().load(extras.getString("backdrop_path")).into(ivBackground);
        Picasso.get().load(extras.getString("poster_path")).into(ivPoster);
        tvTitle.setText(extras.getString("title"));
        tvRating.setText(String.valueOf(extras.getInt("vote_average")));
        tvReleaseDate.setText(getString(R.string.release_date) + " : " + extras.getString("release_date"));
        tvOverview.setText(extras.getString("overview"));

        btnAdd.setOnClickListener(v -> {
            Map<String, String> in = new HashMap<>();
            in.put("id", String.valueOf(extras.getInt("id")));
            in.put("title", extras.getString("title"));
            in.put("overview", extras.getString("overview"));
            in.put("release_date", extras.getString("release_date"));
            in.put("poster_path", extras.getString("poster_path"));
            in.put("vote_average", String.valueOf(extras.getInt("vote_average")));
            in.put("backdrop_path", extras.getString("backdrop_path"));

            new ApiAddData(this, in).execute();
        });

    }

    private class ApiAddData extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private String responseString;
        private Map<String, String > mInput;
        private int success = 0;

        ApiAddData(Context context, Map<String,String> input){
            mContext = context;
            mInput = input;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(getString(R.string.loading_post));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", mInput.get("id"))
                    .addFormDataPart("title", mInput.get("title"))
                    .addFormDataPart("overview", mInput.get("overview"))
                    .addFormDataPart("release_date", mInput.get("release_date"))
                    .addFormDataPart("poster_path", mInput.get("poster_path"))
                    .addFormDataPart("vote_average", mInput.get("vote_average"))
                    .addFormDataPart("backdrop_path", mInput.get("backdrop_path"))
                    .build();

            Request request = new Request.Builder()
                    .url(api_post)
                    .post(requestBody)
                    .build();

            Call call = client.newCall(request);
            try {
                responseString = call.execute().body().string();
                success = 1;
            } catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            if (progressDialog.isShowing()) progressDialog.dismiss();
            if (success == 1) Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
        }
    }
}
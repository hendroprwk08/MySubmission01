package com.example.hendropurwoko.mysubmission01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvData;
    EditText edtCAri;
    Button btnCari;
    public String URL;
    ProgressDialog pDialog;

    private ArrayList<MovieClass> movieList = new ArrayList<>();
    private MovieClass movieClass;

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCAri = (EditText) findViewById(R.id.edt_cari);
        btnCari = (Button) findViewById(R.id.btn_cari);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String API = "86b7abdb2cb37ac9c3c148021f6724e5";
                String CARI = edtCAri.getText().toString().trim();
                URL = "https://api.themoviedb.org/3/search/movie?api_key=" + API + "&language=en-US&query=" + CARI;

                MoviesAsync mAsync = new MoviesAsync();
                mAsync.execute(URL);
            }
        });

        if (movieList.size() != 0 ) {
            displayList();
        }

        rvData = (RecyclerView)findViewById(R.id.rv_data);
        rvData.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), rvData, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("judul", movieList.get(position).getTitle().toString().trim());
                bundle.putString("desc", movieList.get(position).getDesc().toString().trim());
                bundle.putString("date", movieList.get(position).getDate().toString().trim());
                bundle.putString("vote", movieList.get(position).getVote().toString().trim());
                bundle.putString("popularity", movieList.get(position).getPopularity().toString().trim());
                bundle.putString("image", movieList.get(position).getImage().toString().trim());

                intent.putExtras(bundle);
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), movieList.get(position).getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), movieList.get(position).getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void displayList() {
        Log.d("SiZE", String.valueOf(movieList.size()));
        rvData.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this);//di buat pada DataAdapter.java
        movieAdapter.setListPresident(movieList);
        rvData.setAdapter(movieAdapter);
    }

    private class MoviesAsync extends AsyncTask<String, Void, ArrayList<MovieClass>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<MovieClass> doInBackground(String... strings) {
            SyncHttpClient client = new SyncHttpClient();
            movieList = new ArrayList<>();

            Log.d("URL", URL);

            client.get(URL, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    setUseSynchronousMode(true);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);

                    //if (URL != null){
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            JSONArray jsonArray = jsonObj.getJSONArray("results");

                            String image, title, overview, date, popularity, vote;

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                movieClass = new MovieClass();

                                image = data.getString("poster_path").toString().trim();
                                title = data.getString("title").toString().trim();
                                overview = data.getString("overview").toString().trim();
                                date = data.getString("release_date").toString().trim();
                                popularity = data.getString("popularity").toString().trim();
                                vote = data.getString("vote_average").toString().trim();

                                movieClass.setTitle(title);
                                movieClass.setDesc(overview);
                                movieClass.setDate(date);
                                movieClass.setPopularity(popularity);
                                movieClass.setVote(vote);
                                movieClass.setImage("http://image.tmdb.org/t/p/w185"+image);

                                movieList.add(movieClass);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                //}

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("PESAN ", "Gagal");
                }
            });
            return movieList;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieClass> movies) {
            super.onPostExecute(movies);

            if (pDialog.isShowing())
                pDialog.dismiss();

            displayList();
        }
    }


}

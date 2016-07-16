package com.example.kfarst.flicks;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {
    ArrayList<Movie> movies = new ArrayList<Movie>();
    MovieItemsAdapter moviesAdapter;
    RecyclerView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        lvMovies = (RecyclerView) findViewById(R.id.lvMovies);
        lvMovies.setLayoutManager(new LinearLayoutManager(this));

        MoviesApiClient.get("now_playing", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject movieList) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    movies = Movie.mapObjectsFromJSON(movieList.getJSONArray("results"));
                    moviesAdapter = new MovieItemsAdapter(movies);
                    lvMovies.setAdapter(moviesAdapter);
                    //moviesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("ERROR", t.toString());
            }
        });
    }
}

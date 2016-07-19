package com.example.kfarst.flicks.activities;

import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.kfarst.flicks.models.Movie;
import com.example.kfarst.flicks.adapters.MovieItemsAdapter;
import com.example.kfarst.flicks.api.MoviesApiClient;
import com.example.kfarst.flicks.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private MovieItemsAdapter moviesAdapter;
    private RecyclerView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        lvMovies = (RecyclerView) findViewById(R.id.lvMovies);
        lvMovies.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchMovies();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fetchMovies();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        moviesAdapter.setOrientation(newConfig.orientation);
    }

    private void fetchMovies () {
        MoviesApiClient.get("now_playing", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject movieList) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    if (movies.size() > 0) {
                        moviesAdapter.clear();
                        moviesAdapter.addAll(Movie.mapObjectsFromJSON(movieList.getJSONArray("results")));
                        swipeContainer.setRefreshing(false);
                    } else {
                        movies = Movie.mapObjectsFromJSON(movieList.getJSONArray("results"));
                        moviesAdapter = new MovieItemsAdapter(movies, getWindow().getDecorView().getWidth());
                        moviesAdapter.setOrientation(getResources().getConfiguration().orientation);
                        lvMovies.setAdapter(moviesAdapter);
                    }
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

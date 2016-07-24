package com.example.kfarst.flicks.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.kfarst.flicks.models.Movie;
import com.example.kfarst.flicks.adapters.MovieItemsAdapter;
import com.example.kfarst.flicks.api.MoviesApiClient;
import com.example.kfarst.flicks.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {
    @BindView(R.id.refresh_layout) CircleRefreshLayout swipeContainer;
    @BindView(R.id.lvMovies) RecyclerView lvMovies;

    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private MovieItemsAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        lvMovies.setLayoutManager(new LinearLayoutManager(this));

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {
            }

            @Override
            public void refreshing() {
                fetchMovies();
            }
        });

        fetchMovies();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        moviesAdapter.setOrientation(newConfig.orientation);
    }

    private void fetchMovies () {
        final Activity listActivity = this;

        MoviesApiClient.getNowPlaying(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject movieList) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    if (movies.size() > 0) {
                        moviesAdapter.clear();
                        moviesAdapter.addAll(Movie.fromJSON(movieList.getJSONArray("results")));
                    } else {
                        movies = Movie.fromJSON(movieList.getJSONArray("results"));
                        moviesAdapter = new MovieItemsAdapter(listActivity, movies);
                        moviesAdapter.setOrientation(getResources().getConfiguration().orientation);
                        lvMovies.setAdapter(moviesAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", throwable.toString());
                String errorString = getString(R.string.error_fetching_movies);

                if (movies.size() > 0) {
                    Toast.makeText(getBaseContext(), errorString + ' ' + getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), errorString + ' ' + getString(R.string.please_restart_app), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFinish() {
                swipeContainer.finishRefreshing();
            }
        });
    }
}

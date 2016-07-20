package com.example.kfarst.flicks.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kfarst.flicks.R;
import com.example.kfarst.flicks.api.MoviesApiClient;
import com.example.kfarst.flicks.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kfarst on 7/19/16.
 */
public class MovieTrailerActivity extends YouTubeBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_trailer_activity);

        final YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);


        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        MoviesApiClient.get(movie.id() + "/videos", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    final String videoKey = response.getJSONArray("results").getJSONObject(0).getString("key");

                    // do any work here to cue video, play video, etc.
                    youTubePlayerView.initialize("AIzaSyAFRZDTBCULbDeVXlofvdUw1PDWp9WKSh4", new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            final YouTubePlayer youTubePlayer, boolean b) {

                            youTubePlayer.loadVideo(videoKey);
                        }
                        @SuppressLint("ShowToast")
                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {
                            Toast.makeText(getBaseContext(), youTubeInitializationResult.toString(), Toast.LENGTH_LONG);
                        }
                    });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        });
    }
}

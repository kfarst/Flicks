package com.example.kfarst.flicks.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
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
                    final String videoKey = Movie.getVideoTrailerKey(response.getJSONArray("results"));

                    // do any work here to cue video, play video, etc.
                    youTubePlayerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            final YouTubePlayer youTubePlayer, boolean b) {

                            if (videoKey.length() > 0) {
                                youTubePlayer.loadVideo(videoKey);
                            } else {
                                finish();
                               Toast.makeText(getBaseContext(), R.string.no_trailer_found, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {
                            finish();
                            Toast.makeText(getBaseContext(), R.string.error_loading_trailer, Toast.LENGTH_LONG).show();
                        }
                    });
            } catch (JSONException e) {
                e.printStackTrace();
                finish();
                Toast.makeText(getBaseContext(), R.string.error_loading_trailer, Toast.LENGTH_LONG).show();
            }
        }
        });
    }

    public void closeMovieTrailer (View view) {
        finish();
    }
}

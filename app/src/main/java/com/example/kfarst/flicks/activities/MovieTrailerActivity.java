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

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by kfarst on 7/19/16.
 */
public class MovieTrailerActivity extends YouTubeBaseActivity {
    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_trailer_activity);

        ButterKnife.bind(this);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        MoviesApiClient.getVideos(movie.id(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    final String videoKey = Movie.getVideoTrailerKey(response.getJSONArray("results"));

                    // do any work here to cue video, play video, etc.
                    youTubePlayerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            final YouTubePlayer youTubePlayer, boolean b) {
                            // If a YouTube trailer is found, load video, otherwise close activity and show clarifying error
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
                            // Close the activity and show clarifying error
                            finish();
                            Toast.makeText(getBaseContext(), R.string.error_loading_trailer, Toast.LENGTH_LONG).show();
                        }
                    });
            } catch (JSONException e) {
                e.printStackTrace();

                // Close the activity and show clarifying error
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

package com.example.kfarst.flicks.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kfarst.flicks.R;
import com.example.kfarst.flicks.models.Movie;
import com.example.kfarst.flicks.support.DeviceDimensionsHelper;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private Movie movie;
    private ImageView ivPoster;
    private TextView tvTitle;
    private RatingBar rbRating;
    private TextView tvPopularity;
    private TextView tvSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_movie_detail);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        setupViews();
    }

    private void setupViews () {
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        rbRating = (RatingBar) findViewById(R.id.rbRating);
        tvPopularity = (TextView) findViewById(R.id.tvPopularity);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);

        Picasso
                .with(ivPoster.getContext())
                .load(imageUrl(movie.getBackdropPath()))
                .placeholder(R.drawable.landscape_placeholder)
                .resize(DeviceDimensionsHelper.getDisplayWidth(getBaseContext()), 0)
                .into(ivPoster);

        tvTitle.setText(movie.getTitle());
        tvTitle.setText(movie.getTitle());

        rbRating.setNumStars(5);
        rbRating.setStepSize((float) 2.0);
        rbRating.setRating((float) movie.getVoteAverage());

        tvPopularity.setText(String.valueOf(movie.getPopularity()));
        tvSynopsis.setText(movie.getOverview());
    }

    public void onCloseActivity (View view) {
        finish();
    }

    private String imageUrl (String imageName) {
        return "https://image.tmdb.org/t/p/w342" + imageName;
    }
}

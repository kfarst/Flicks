package com.example.kfarst.flicks.models;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kfarst on 7/13/16.
 */
public class Movie implements Serializable {
    public int id() { return id; }

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    private int id;
    private String posterPath;
    private boolean adult;
    private String overview;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private String releaseDate;
    private double popularity;
    private double voteAverage;

    public Movie(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        posterPath = jsonObject.getString("poster_path");
        adult = jsonObject.getBoolean("adult");
        overview = jsonObject.getString("overview");
        releaseDate = jsonObject.getString("release_date");
        originalTitle = jsonObject.getString("original_title");
        originalLanguage = jsonObject.getString("original_language");
        title = jsonObject.getString("title");
        backdropPath = jsonObject.getString("backdrop_path");
        popularity = jsonObject.getDouble("popularity");
        voteAverage = jsonObject.getDouble("vote_average");
    }

    public static ArrayList<Movie> fromJSON(JSONArray movieList) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (int i = 0; i < movieList.length(); i++) {
           movies.add(new Movie(movieList.getJSONObject(i)));
        }

        return movies;
    }

    public static String getVideoTrailerKey(JSONArray results) throws JSONException {
        for (int i = 0; i < results.length(); i++) {
            JSONObject video = results.getJSONObject(i);
            // Video must both be a trailer and from YouTube in order to work properly
            if (video.getString("site").equalsIgnoreCase("youtube") && video.getString("type").equalsIgnoreCase("trailer")) {
                return video.getString("key");
            }
        }
        return "";
    }
}

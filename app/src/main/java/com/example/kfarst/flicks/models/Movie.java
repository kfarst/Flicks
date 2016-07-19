package com.example.kfarst.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kfarst on 7/13/16.
 */
public class Movie implements Serializable {
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

    public boolean isVideo() {
        return video;
    }

    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private boolean video;

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        adult = jsonObject.getBoolean("adult");
        overview = jsonObject.getString("overview");
        releaseDate = jsonObject.getString("release_date");
        originalTitle = jsonObject.getString("original_title");
        originalLanguage = jsonObject.getString("original_language");
        title = jsonObject.getString("title");
        backdropPath = jsonObject.getString("backdrop_path");
        popularity = jsonObject.getDouble("popularity");
        video = jsonObject.getBoolean("video");
    }

    public static ArrayList<Movie> mapObjectsFromJSON(JSONArray movieList) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (int i = 0; i < movieList.length(); i++) {
           movies.add(new Movie(movieList.getJSONObject(i)));
        }

        return movies;
    }
}

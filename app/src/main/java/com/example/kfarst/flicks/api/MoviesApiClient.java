package com.example.kfarst.flicks.api;
import com.loopj.android.http.*;

/**
 * Created by kfarst on 7/13/16.
 */
public class MoviesApiClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final RequestParams params = new RequestParams();

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getNowPlaying(JsonHttpResponseHandler responseHandler) {
        params.put("api_key", API_KEY);
        client.get(getAbsolutelUrl("now_playing"), params, responseHandler);
    }

    public static void getVideos(int id, JsonHttpResponseHandler responseHandler) {
        params.put("api_key", API_KEY);
        client.get(getAbsolutelUrl(id + "/videos"), params, responseHandler);
    }

    public static String getAbsolutelUrl(String url) {
        return BASE_URL + url;
    }
}

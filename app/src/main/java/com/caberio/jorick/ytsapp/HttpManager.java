package com.caberio.jorick.ytsapp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HttpManager {

    public static String getMoviesFeed(String uri){
        String result = null;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(uri)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Movie> parseMoviesFeed(String json){
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject data = object.getJSONObject("data");
            JSONArray movies = data.getJSONArray("movies");

            for(int i=0; i<movies.length(); i++){
                JSONObject movieObj = movies.getJSONObject(i);
                Movie movie = new Movie();
                movie.setId(movieObj.getInt("id"));
                movie.setTitle(movieObj.getString("title"));
                movie.setTitleLong(movieObj.getString("title_long"));
                movie.setYear(movieObj.getInt("year"));
                movie.setRating(movieObj.getDouble("rating"));
                movie.setCoverimage(movieObj.getString("medium_cover_image"));
                movieList.add(movie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;

    }
}

package com.caberio.jorick.ytsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpManager {

    public static String getMoviesFeed(String uri){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader;
        String line;

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line.concat("\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

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

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
                Movie movie = new Movie();
                List<Torrent> torrents = new ArrayList<>();
                JSONObject movieObj = movies.getJSONObject(i);
                JSONArray torrentArr = movieObj.getJSONArray("torrents");
                for(int j=0; j<torrentArr.length(); j++){
                    Torrent torrent = new Torrent();
                    JSONObject torrentObj = torrentArr.getJSONObject(j);
                    torrent.setQuality(torrentObj.getString("quality"));
                    torrent.setPeers(torrentObj.getInt("peers"));
                    torrent.setSeeds(torrentObj.getInt("seeds"));
                    torrent.setSize(torrentObj.getString("size"));
                    torrents.add(torrent);
                }

                movie.setId(movieObj.getInt("id"));
                movie.setTitle(movieObj.getString("title"));
                movie.setTitleLong(movieObj.getString("title_long"));
                movie.setCoverimage(movieObj.getString("medium_cover_image"));
                movie.setTorrents(torrents);
                movieList.add(movie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;

    }
}

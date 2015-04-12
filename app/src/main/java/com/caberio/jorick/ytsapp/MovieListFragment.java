package com.caberio.jorick.ytsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestData();
    }

    private class MovieAdapter extends ArrayAdapter<Movie>{
        public MovieAdapter(List<Movie> movies){
            super(getActivity(), 0, movies);
        }

        @Override
        public  View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_movies, null);
            }
            Movie movie = getItem(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageItem);
            Picasso.with(getActivity()).load(movie.getCoverImage())
                    .resize(50, 50).centerCrop().into(imageView);

            TextView textView = (TextView) convertView.findViewById(R.id.textItem);
            textView.setText(movie.getTitleLong());

            return convertView;

        }
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        Movie movie = (Movie)getListView().getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MovieFragment.DATA, movie);
        startActivity(intent);
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
    }

    private void requestData() {
        if (isOnline()) {
            String listMoviesEndPoint = "https://yts.to/api/v2/list_movies.json";
            MovieTask movieTask = new MovieTask();
            movieTask.execute(listMoviesEndPoint);
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private class MovieTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(), "Loading latest movie torrents", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return HttpManager.getMoviesFeed(strings[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            List<Movie> movies = HttpManager.parseMoviesFeed(json);
            MovieAdapter movieAdapter = new MovieAdapter(movies);
            setListAdapter(movieAdapter);

        }
    }
}




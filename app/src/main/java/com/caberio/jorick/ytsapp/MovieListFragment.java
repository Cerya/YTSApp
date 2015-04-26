package com.caberio.jorick.ytsapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieListFragment extends ListFragment implements AbsListView.OnScrollListener {

    private static int mCurrentPage = 1;
    MovieAdapter mMovieAdapter;
    List<Movie> mMovies;
    String TAG = MovieListFragment.class.getSimpleName();
    ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovies = new ArrayList<>();
        requestData(mCurrentPage);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pb = new ProgressBar(getActivity());
        pb.setIndeterminate(true);
        pb.setVisibility(View.INVISIBLE);
        getListView().addFooterView(pb);
        getListView().setOnScrollListener(MovieListFragment.this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d(TAG, "left unimplemented");
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount
                           && (totalItemCount!=0);
        Log.d(TAG, "item counts before loadMore - begin");
        Log.d(TAG, "firstVisibleItem: "+firstVisibleItem);
        Log.d(TAG, "visibleItemCount: "+visibleItemCount+"");
        Log.d(TAG, "totalItemCount: "+totalItemCount+"");
        Log.d(TAG, "item counts before loadMore - end");
        if(loadMore) {
            pb.setVisibility(View.VISIBLE);
            Log.d(TAG, "loadMore");
            requestData(mCurrentPage);
            Log.d(TAG, "item counts after loadMore - begin");
            Log.d(TAG, "firstVisibleItem: "+firstVisibleItem);
            Log.d(TAG, "visibleItemCount: "+visibleItemCount+"");
            Log.d(TAG, "totalItemCount: "+totalItemCount+"");
            Log.d(TAG, "item counts after loadMore - end");
        }

    }


    private class MovieAdapter extends ArrayAdapter<Movie>{

        public MovieAdapter(List<Movie> movies){
            super(getActivity(), 0, movies);
        }

        @Override
        public  View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_movies, parent, false);
            }
            Movie movie = getItem(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageItem);
            Picasso.with(getActivity()).load(movie.getCoverImage())
                    .resize(70, 70).centerCrop().into(imageView);

            TextView textView = (TextView) convertView.findViewById(R.id.textItem);
            textView.setText(movie.getTitleLong());

            List<Torrent> torrents = movie.getTorrents();
            for(int i=0; i<torrents.size(); i++){
                Log.d(TAG, "getting torrents: "+torrents.get(i).toString());
            }

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

    private void requestData(int page) {
        if (isOnline()) {
            Log.d(TAG, "requestData called");
            Log.d(TAG, "page: "+page);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("yts.to")
                    .appendPath("api")
                    .appendPath("v2")
                    .appendPath("list_movies.json")
                    .appendQueryParameter("limit", "10")
                    .appendQueryParameter("page", Integer.toString(page));
            String myUrl = builder.build().toString();
            MovieTask movieTask = new MovieTask();
            movieTask.execute(myUrl);
            mCurrentPage+=1;
            Log.d(TAG, "mCurrentPage: " + mCurrentPage);
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private class MovieTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String result = HttpManager.getMoviesFeed(strings[0]);
            Log.d(TAG, "result: "+result);
            return result;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(String json) {
            List<Movie> tmp = HttpManager.parseMoviesFeed(json);
            Log.d(TAG, "should be 10, tmp.size: "+tmp.size());
            Log.d(TAG, "mMovies.size: "+mMovies.size());
            if(mMovieAdapter==null){
                Log.d(TAG, "instantiating adapter");
                mMovies.addAll(tmp);
                mMovieAdapter = new MovieAdapter(mMovies);
                setListAdapter(mMovieAdapter);
            }
            else{

                mMovieAdapter.addAll(tmp);

            }
            Log.d(TAG, " mMovieAdapter.getCount() : "+mMovieAdapter.getCount()+"");
            mMovieAdapter.notifyDataSetChanged();
        }
    }
}




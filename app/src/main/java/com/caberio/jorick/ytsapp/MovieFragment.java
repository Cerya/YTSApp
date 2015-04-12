package com.caberio.jorick.ytsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class MovieFragment extends Fragment {

    public static final String DATA = "com.caberio.jorick.ytsapp.DATA";

    private Movie mMovie;
    private TextView mMovieTitle;
    private TextView mMovieYear;
    private TextView mMovieRating;
    private ImageView mCoverImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
        mMovie = (Movie)getActivity().getIntent().getSerializableExtra(DATA);
        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, parent, false);

        mMovieTitle = (TextView) view.findViewById(R.id.movie_title);
        mMovieTitle.setText(mMovie.getTitle());

        mMovieYear = (TextView) view.findViewById(R.id.movie_year);
        mMovieYear.setText("Year: " + Integer.toString(mMovie.getYear()));

        mMovieRating = (TextView) view.findViewById(R.id.movie_rating);
        mMovieRating.setText("Rating: " + Double.toString(mMovie.getRating()));

        mCoverImage= (ImageView) view.findViewById(R.id.movie_image);

        return  view;
    }

    private class ImageDownloader extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected  void onPreExecute(){
            Toast.makeText(getActivity(),"Loading image",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try {
                String imageUrl = mMovie.getCoverImage();
                InputStream inputStream =(InputStream) new URL(imageUrl).getContent();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            mCoverImage.setImageBitmap(bitmap);
        }




    }

}

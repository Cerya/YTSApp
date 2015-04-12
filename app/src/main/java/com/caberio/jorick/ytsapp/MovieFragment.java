package com.caberio.jorick.ytsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


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
        Picasso.with(getActivity()).load(mMovie.getCoverImage()).into(mCoverImage);

        return  view;
    }



}

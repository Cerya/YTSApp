package com.caberio.jorick.ytsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieFragment extends Fragment {

    public static final String DATA = "com.caberio.jorick.ytsapp.DATA";
    private Movie mMovie;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = (Movie)getActivity().getIntent().getSerializableExtra(DATA);
        getActivity().setTitle(mMovie.getTitle());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, parent, false);
        TextView mMovieQuality;
        mMovieQuality = (TextView) view.findViewById(R.id.movie_quality);
        mMovieQuality.setText("Available Quality");

        List<Torrent> torrents = mMovie.getTorrents();

        for(Torrent torrent: torrents){

            if (torrent.getQuality().equals("720p")) {
                torrentDetails(view, torrent, R.id.movie_quality_720, R.id.movie_quality_720_size,
                        R.id.movie_quality_720_seeds, R.id.movie_quality_720_peers);
            }

            if (torrent.getQuality().equals("1080p")) {

                torrentDetails(view, torrent, R.id.movie_quality_1080, R.id.movie_quality_1080_size,
                        R.id.movie_quality_1080_seeds, R.id.movie_quality_1080_peers);
            }

        }

        ImageView mCoverImage= (ImageView) view.findViewById(R.id.movie_image);
        Picasso.with(getActivity()).load(mMovie.getCoverImage()).into(mCoverImage);

        return  view;
    }

    private void torrentDetails(View view, Torrent torrent, int qualityResId, int sizeResId, int seedsResId, int peersResId) {
        TextView textViewQuality = (TextView) view.findViewById(qualityResId);
        textViewQuality.setText("Quality: " + torrent.getQuality());
        TextView textViewSize = (TextView) view.findViewById(sizeResId);
        textViewSize.setText("Size: " + torrent.getSize());
        TextView textViewSeeds = (TextView) view.findViewById(seedsResId);
        textViewSeeds.setText("Seeds: " + torrent.getSeeds());
        TextView textViewPeers = (TextView) view.findViewById(peersResId);
        textViewPeers.setText("Peers: " + torrent.getPeers());
    }


}

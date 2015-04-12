package com.caberio.jorick.ytsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.parentLinearLayout);
            int indexOffset = 0;

            if (torrent.getQuality().equals("720p")){

                torrentDetails(linearLayout, R.id.movie_quality_720,
                        "Quality: "+torrent.getQuality(), ++indexOffset);

                torrentDetails(linearLayout, R.id.movie_quality_720_size,
                        "Size: "+torrent.getSize(), ++indexOffset);

                torrentDetails(linearLayout, R.id.movie_quality_720_seeds,
                        "Seeds: "+torrent.getSeeds(), ++indexOffset);

                torrentDetails(linearLayout, R.id.movie_quality_720_peers,
                        "Peers: "+torrent.getPeers(), ++indexOffset);

            }
            if(torrent.getQuality().equals("1080p")){

                torrentDetails(linearLayout, R.id.movie_quality_1080,
                        "Quality: "+torrent.getQuality(), ++indexOffset);

                torrentDetails(linearLayout, R.id.movie_quality_1080_size,
                        "Size: "+torrent.getSize(), ++indexOffset);

                torrentDetails(linearLayout, R.id.movie_quality_1080_seeds,
                        "Seeds: "+torrent.getSeeds(), ++indexOffset);

                torrentDetails(linearLayout, R.id.movie_quality_1080_peers,
                        "Peers: "+torrent.getPeers(),  ++indexOffset);
            }


        }

        ImageView mCoverImage= (ImageView) view.findViewById(R.id.movie_image);
        Picasso.with(getActivity()).load(mMovie.getCoverImage()).into(mCoverImage);

        return  view;
    }

    private void torrentDetails(LinearLayout linearLayout, int resId, String msg, int offset) {
        TextView tv = new TextView(getActivity());
        tv.setId(resId);
        tv.setText(msg);
        tv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(tv , offset);

    }

}

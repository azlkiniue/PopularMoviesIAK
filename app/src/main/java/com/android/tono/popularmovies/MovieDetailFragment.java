package com.android.tono.popularmovies;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tono.popularmovies.base.BaseFragment;
import com.android.tono.popularmovies.object.ListMovieVideos;
import com.android.tono.popularmovies.object.Result;
import com.android.tono.popularmovies.object.Video;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends BaseFragment implements VideoListAdapter.OnItemClicked {
    public String MOVIE_ID = "id";
    static private String YOUTUBE_URL = "http://www.youtube.com/watch?v=";
    private int movieId;
    private Result movie;
    private String title = "Movie Details";
    private List<Video> videos = new ArrayList<>();
    private VideoListAdapter mAdapter = new VideoListAdapter();

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle arguments = getArguments();
        if (null != arguments){
            movieId = arguments.getInt(MOVIE_ID);
            getMovie(rootView);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected String getTitle() {
        return this.title;
    }

    void displayDetail(View rootView){
        setHasOptionsMenu(true);
        final Realm realm = Realm.getDefaultInstance();
        TextView mTextView = rootView.findViewById(R.id.tv_detail_title);
        TextView mReleaseDate = rootView.findViewById(R.id.tv_detail_release_date);
        TextView mOverview = rootView.findViewById(R.id.tv_detail_synopsis);
        ImageView mImageView = rootView.findViewById(R.id.img_detail);
        String text = movie.getTitle();
        mTextView.setText(text);
        mReleaseDate.setText(movie.getReleaseDate());
        mOverview.setText(movie.getOverview());
        Glide.with(rootView)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(mImageView);

        final Button mButtonFav = rootView.findViewById(R.id.bt_fav);
        final Button mButtonUnfav = rootView.findViewById(R.id.bt_unfav);
        buttonHandler(mButtonFav, mButtonUnfav, realm);
    }

    void buttonHandler(final Button mButtonFav, final Button mButtonUnfav, final Realm realm){
        Result test = realm.where(Result.class).equalTo("id", movieId).findFirst();
        if (test == null) {
            mButtonFav.setVisibility(View.VISIBLE);
            mButtonUnfav.setVisibility(View.GONE);
        } else {
            mButtonFav.setVisibility(View.GONE);
            mButtonUnfav.setVisibility(View.VISIBLE);
        }
        mButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertOnClick(mButtonFav, mButtonUnfav, realm);
            }
        });
        mButtonUnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOnClick(mButtonFav, mButtonUnfav, realm);
            }
        });
    }

    void insertOnClick(Button mButtonFav, Button mButtonUnfav, Realm realm){
        Toast.makeText(getContext(), "Yay!", Toast.LENGTH_LONG).show();
        mButtonFav.setVisibility(View.GONE);
        mButtonUnfav.setVisibility(View.VISIBLE);
        realm.beginTransaction();
        Result result = realm.createObject(Result.class, movieId);
        result.setResult(movie);
        realm.commitTransaction();
    }

    void deleteOnClick(Button mButtonFav, Button mButtonUnfav, Realm realm){
        Toast.makeText(getContext(), "Nay!", Toast.LENGTH_LONG).show();
        mButtonFav.setVisibility(View.VISIBLE);
        mButtonUnfav.setVisibility(View.GONE);
        realm.beginTransaction();
        Result result = realm.where(Result.class).equalTo("id", movieId).findFirst();
        if (result != null) {
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    void getMovie(final View rootView){

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Const.getUrlPrefix())
                .build();

        //TODO : Buat api
        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
        tmdbApi.getDetailMovie(movieId, Const.API_KEY).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body() == null) {
                    Log.d("Error", "Null");
                } else {
                    movie = response.body();
                    displayDetail(rootView);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
        if (movieId != 0){
            videos.add(new Video("0", "No Trailer Available"));

            RecyclerView recyclerView = rootView.findViewById(R.id.recycle_list_video);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter.setVideos(videos);
            recyclerView.setAdapter(mAdapter);

            tmdbApi.getVideo(movieId, Const.API_KEY).enqueue(new Callback<ListMovieVideos>() {
                @Override
                public void onResponse(Call<ListMovieVideos> call, Response<ListMovieVideos> response) {
                    String titles = "";
                    if (response.body().getVideos() != null && response.body().getVideos().size() > 0){
                        for (int i = 0; i < response.body().getVideos().size(); i++) {
                            Video result = response.body().getVideos().get(i);
                            titles += result.getName() + "\n";

                        }

                        videos = response.body().getVideos();
                        mAdapter.setVideos(videos);
                        mAdapter.notifyDataSetChanged();
                        Log.d("result: ", String.valueOf(mAdapter.getItemCount()));
                    }
                }

                @Override
                public void onFailure(Call<ListMovieVideos> call, Throwable t) {
                    Log.e("Error: ", "Error");
                }
            });

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter.setOnClick(this);
        }
    }

    @Override
    public void onItemClick(int position) {
        Video selectedVideo = videos.get(position);
        Toast.makeText(getContext(), "You clicked "+selectedVideo.getName()+"! Yay!", Toast.LENGTH_LONG).show();
        watchYoutubeVideo(getContext(), selectedVideo.getKey());
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_URL + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            StringBuilder strBody = new StringBuilder("Trailer Video Links for " + movie.getTitle() + "\n");
            for (Video element:videos) {
                String row = "\n" + element.getName() + ": " + YOUTUBE_URL + element.getKey();
                strBody.append(row);
            }
            String shareBody = strBody.toString();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Trailer Video Links");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_video)));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

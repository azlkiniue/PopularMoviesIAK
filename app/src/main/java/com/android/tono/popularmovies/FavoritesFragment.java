package com.android.tono.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.tono.popularmovies.base.BaseFragment;
import com.android.tono.popularmovies.object.ListMovieResponse;
import com.android.tono.popularmovies.object.Result;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends BaseFragment implements MovieListAdapter.OnItemClicked {
    private String title = "Favorite Movies";
    List<Result> movies;
    MovieListAdapter mAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);

        View rootView = inflater.inflate(R.layout.content_main, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        movies = new ArrayList<>();
        movies.add(new Result(0, "No favorite movies, yet", "Mark your favourite movies to dismiss this message."));

        mAdapter = new MovieListAdapter();

        Realm realm = Realm.getDefaultInstance();
        RealmResults realmResults = realm.where(Result.class).findAll();
//        Log.d("test", "onDB: " + realmResults.size());
        if (realmResults.size() > 0)
        movies = realm.copyFromRealm(realmResults);

        mAdapter.setMovies(movies);

        recyclerView.setAdapter(mAdapter);


        //mAdapter.notifyDataSetChanged();

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnClick(this);

        return rootView;
    }

    @Override
    public void onItemClick(int position) {
        Result selectedMovie = movies.get(position);
//        Toast.makeText(getContext(), "You clicked "+selectedMovie.getName()+"! Yay!", Toast.LENGTH_LONG).show();

        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(fragment.MOVIE_ID, selectedMovie.getId());
        fragment.setArguments(args);

//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        add(fragment);
    }

    @Override
    protected String getTitle() {
        return this.title;
    }
}

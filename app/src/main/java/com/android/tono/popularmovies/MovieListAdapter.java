package com.android.tono.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tono.popularmovies.object.Movie;
import com.android.tono.popularmovies.object.Result;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ahmada Yusril on 30/11/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {

    private List<Result> movies;
    private Context context;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

//    public MovieListAdapter(List<Result> movies) {
//        this.movies = movies;
//    }

    public void setMovies(List<Result> movies) {
        this.movies = movies;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, final int position) {
        Result movie = movies.get(position);
        holder.vMovieList.setText(movie.getTitle());
        holder.vCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
        if (movie.getReleaseDate() == null) {
            holder.vMovieYear.setText("Unknown");
        } else if (movie.getId() == 0) {
            holder.vMovieYear.setText(movie.getReleaseDate());
        } else {
            holder.vMovieYear.setText(movie.getReleaseDate().substring(0, 4));
        }

        String rate = Double.toString(movie.getVoteAverage()) + "/10.0";
        holder.vMovieRate.setText(rate);
        if (movie.getPosterPath() == null){
            Glide.with(context)
                    .load("https://dummyimage.com/100x150/000/ffffff&text=Null")
                    .into(holder.vImageView);
        } else {
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(holder.vImageView);
        }

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}

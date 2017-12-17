package com.android.tono.popularmovies;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ahmada Yusril on 30/11/2017.
 */

public class MovieListViewHolder extends RecyclerView.ViewHolder {
    protected TextView vMovieList;
    protected CardView vCardView;
    protected ImageView vImageView;
    protected TextView vMovieYear;
    protected TextView vMovieRate;


    public MovieListViewHolder(View itemView) {
        super(itemView);
        this.vMovieList = itemView.findViewById(R.id.tv_movie_name);
        this.vCardView = itemView.findViewById(R.id.card_view);
        this.vImageView = itemView.findViewById(R.id.img_list_movie);
        this.vMovieYear = itemView.findViewById(R.id.tv_movie_date);
        this.vMovieRate = itemView.findViewById(R.id.tv_movie_rate);
    }
}

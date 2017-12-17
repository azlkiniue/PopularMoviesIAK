package com.android.tono.popularmovies;

import com.android.tono.popularmovies.object.ListMovieResponse;
import com.android.tono.popularmovies.object.ListMovieVideos;
import com.android.tono.popularmovies.object.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TmdbApi {
    @GET("3/movie/{id}")
    Call<Result> getDetailMovie(@Path("id") int id, @Query("api_key") String apiKey);

    //TODO : Tambahkan kontrak untuk mengambill list movie
    @GET("3/movie/popular")
    Call<ListMovieResponse> getListMovie(@Query("api_key") String apiKey);

    //https://api.themoviedb.org/3/movie/top_rated?api_key=1d710c7ee533cd8a026a80f3f824584a
    @GET("3/movie/top_rated")
    Call<ListMovieResponse> getListTopRatedMovie(@Query("api_key") String apiKey);

    //https://api.themoviedb.org/3/movie/157336/videos?api_key=1d710c7ee533cd8a026a80f3f824584a
    @GET("3/movie/{id}/videos")
    Call<ListMovieVideos> getVideo(@Path("id") int id, @Query("api_key") String apiKey);
}


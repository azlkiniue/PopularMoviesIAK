package com.android.tono.popularmovies;

/**
 * Created by andazlan on 11/4/17.
 */

public class Const {
    public static final String URL_PREFIX = "http://";
    public static final String BASE_URL = "api.themoviedb.org";
    public static final String API_KEY = "1d710c7ee533cd8a026a80f3f824584a";

    public static String getUrlPrefix(){
        return URL_PREFIX + BASE_URL;
    }
}

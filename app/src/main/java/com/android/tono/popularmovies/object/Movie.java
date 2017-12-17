package com.android.tono.popularmovies.object;

/**
 * Created by Ahmada Yusril on 30/11/2017.
 */

public class Movie {
    private int id;
    private String Name;

    public Movie(int id, String name) {
        this.id = id;
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

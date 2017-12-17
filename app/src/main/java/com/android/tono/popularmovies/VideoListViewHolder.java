package com.android.tono.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Ahmada Yusril on 15/12/2017.
 */

public class VideoListViewHolder extends RecyclerView.ViewHolder {
    TextView vVideoList;
    RelativeLayout vRelativeLayout;

    public VideoListViewHolder(View itemView) {
        super(itemView);
        this.vVideoList = itemView.findViewById(R.id.tv_detail_video);
        this.vRelativeLayout = itemView.findViewById(R.id.list_movie_view);
    }
}

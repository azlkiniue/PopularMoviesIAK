package com.android.tono.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tono.popularmovies.object.Video;

import java.util.List;

/**
 * Created by Ahmada Yusril on 15/12/2017.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListViewHolder> {

    List<Video> videos;
    Context context;
    OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public void setVideos(List<Video> videos){
        this.videos = videos;
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_movie_view, parent, false);
        return new VideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoListViewHolder holder, final int position) {
        Video video = videos.get(position);
        holder.vVideoList.setText(video.getName());
        holder.vRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
        holder.vVideoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick=onClick;
    }
}

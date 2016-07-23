package com.papi.player.ui.adapter.list;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.papi.player.R;
import com.papi.player.bean.ChannelMediaEntity;
import com.papi.player.localvideo.LoadedImage;
import com.papi.player.localvideo.Video;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class LocalVideoAdapter extends AbsRecyclerViewAdapter {

    private List<Video> listVideos;
    private int local_postion = 0;
    private boolean imageChage = false;
    private LayoutInflater mLayoutInflater;
    private ArrayList<LoadedImage> photos = new ArrayList<LoadedImage>();

    public LocalVideoAdapter(RecyclerView recyclerView, List<Video> listVideos) {
        super(recyclerView);
        this.listVideos = listVideos;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.card_item_horizontal, parent, false);
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder cvh, int pos) {
        super.onBindViewHolder(cvh, pos);
        if (cvh instanceof CardHolder) {
            CardHolder holder = (CardHolder) cvh;

            holder.mTitleView.setText(listVideos.get(pos).getTitle());//ms
            holder.mTitleView1.setVisibility(View.GONE);

            long min = listVideos.get(pos).getDuration() / 1000 / 60;
            long sec = listVideos.get(pos).getDuration() / 1000 % 60;
            holder.mUpdateTv.setText(min + ":" + sec);
            holder.mPreviewImage.setImageBitmap(photos.get(pos).getBitmap());
        }
    }

    public void addPhoto(LoadedImage image) {
        photos.add(image);
    }


    public Video getItem(int position) {
        return listVideos.get(position);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class CardHolder extends ClickableViewHolder {

        public ImageView mPreviewImage;
        public AppCompatTextView mTitleView;
        public AppCompatTextView mTitleView1;

        public TextView mUpdateTv;

        public CardHolder(View itemView) {
            super(itemView);
            mPreviewImage = $(R.id.video_preview);
            mUpdateTv = $(R.id.video_update);
            mTitleView = $(R.id.video_title);
            mTitleView1 = $(R.id.video_title1);
        }

    }

}
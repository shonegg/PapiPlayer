package com.papi.player.ui.adapter.list;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.papi.player.R;
import com.papi.player.dao.MultMediaBean;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public class HistoryAdapter extends AbsRecyclerViewAdapter {

    private List<MultMediaBean> listVideos;
    private int local_postion = 0;
    private boolean imageChage = false;
    private LayoutInflater mLayoutInflater;

    public HistoryAdapter(RecyclerView recyclerView) {
        super(recyclerView);
        listVideos = new ArrayList<>();
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

            holder.mTitleView.setText(getItem(pos).getName());//ms
            holder.mTitleView1.setVisibility(View.GONE);
            holder.mUpdateTv.setText(getItem(pos).getTime());
            SimpleImageLoader.display(getItem(pos).getCover_url(), holder.mPreviewImage);
        }
    }

    public void update(List<MultMediaBean> lists) {
        listVideos.addAll(lists);
        notifyDataSetChanged();
    }


    public MultMediaBean getItem(int position) {
        return listVideos.get(position);
    }

    @Override
    public int getItemCount() {
        return listVideos.size();
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

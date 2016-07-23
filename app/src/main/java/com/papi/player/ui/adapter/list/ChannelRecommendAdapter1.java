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
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelRecommendAdapter1 extends AbsRecyclerViewAdapter {


    private List<ChannelMediaEntity.MediaBean> mList = new ArrayList<>();

    public ChannelRecommendAdapter1(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.card_item_fragment_recommend, parent, false);
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder cvh, int position) {
        super.onBindViewHolder(cvh, position);
        if (cvh instanceof CardHolder) {
            CardHolder holder = (CardHolder) cvh;

            holder.mTitleView.setText(getItem(position).name);
            holder.mTitleView1.setText(getItem(position).aword);

            SimpleImageLoader.display(getItem(position).still,
                    holder.mPreviewImage);
            holder.mUpdateTv.setText(getItem(position).update);

        }
    }

    public ChannelMediaEntity.MediaBean getItem(int pos) {
        return mList.get(pos);
    }

    public void addContent(List<ChannelMediaEntity.MediaBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public List<ChannelMediaEntity.MediaBean> getList (){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
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


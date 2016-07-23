package com.papi.player.ui.adapter.list;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.papi.player.R;
import com.papi.player.bean.ChannelsEntity;
import com.papi.player.bean.TopicBean;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelRecyclerAdapter extends AbsRecyclerViewAdapter {


    private List<ChannelsEntity.ChannelBean> mList = new ArrayList<>();

    public ChannelRecyclerAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.card_item_channels, parent, false);
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder cvh, int position) {
        super.onBindViewHolder(cvh, position);
        if (cvh instanceof CardHolder) {
            CardHolder holder = (CardHolder) cvh;

            holder.mTitleView.setText(getItem(position).name);
//          SimpleImageLoader.display(getItem(position).icon1,
//                    holder.mPreviewImage);
//
            RandomColor randomColor = new RandomColor();
            int color = randomColor.randomColor (0x100000, RandomColor.SaturationType.RANDOM,
                    RandomColor.Luminosity.DARK);
            holder.mPreviewImage.setBackgroundColor(color);
        }
    }

    public ChannelsEntity.ChannelBean getItem(int pos) {
        return mList.get(pos);
    }

    public void addContent(List<ChannelsEntity.ChannelBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CardHolder extends ClickableViewHolder {

        public ImageView mPreviewImage;
        public AppCompatTextView mTitleView;

        public CardHolder(View itemView) {
            super(itemView);
            mPreviewImage = $(R.id.video_preview);
            mTitleView = $(R.id.video_title);
        }

    }
}

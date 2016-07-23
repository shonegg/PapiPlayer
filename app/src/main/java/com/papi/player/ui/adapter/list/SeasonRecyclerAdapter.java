package com.papi.player.ui.adapter.list;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.papi.player.R;
import com.papi.player.bean.MediaEpisodeEntity;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     19/06/16.
 * Github   https://github.com/shonegg
 */
public class SeasonRecyclerAdapter extends AbsRecyclerViewAdapter {

    private List<MediaEpisodeEntity.Episode> mList = new ArrayList<>();

    public SeasonRecyclerAdapter(RecyclerView recyclerView) {
        super(recyclerView);

    }

    public void addContent(List<MediaEpisodeEntity.Episode> list) {
        mList.addAll(list);
        notifyItemRangeInserted(0, list.size());
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.card_item_season, parent, false);
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder cvh, int position) {
        super.onBindViewHolder(cvh, position);
        if (cvh instanceof CardHolder) {
            CardHolder holder = (CardHolder) cvh;
            holder.mTitleView.setText("" + (position + 1));
        }
    }

    public MediaEpisodeEntity.Episode getVideoItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CardHolder extends ClickableViewHolder {

        public AppCompatTextView mTitleView;

        public CardHolder(View itemView) {
            super(itemView);
            mTitleView = $(R.id.season_tag);
        }

    }

}

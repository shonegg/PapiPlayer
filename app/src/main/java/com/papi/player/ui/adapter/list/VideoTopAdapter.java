package com.papi.player.ui.adapter.list;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.papi.player.R;
import com.papi.player.bean.BaseEntity2;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     03/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoTopAdapter extends AbsRecyclerViewAdapter {


    private final Context mContext;
    private List<BaseEntity2.Content> mList = new ArrayList<>();

    public VideoTopAdapter(Context context, RecyclerView recyclerView) {
        super(recyclerView);
        this.mContext = context;
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
            holder.mRankTv.setText("" + (position + 1));

            if (position > 2){
                holder.mRankTv.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.mRankTv.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
            }
            holder.mRankTv.setVisibility(View.VISIBLE);


        }
    }

    public BaseEntity2.Content getItem(int pos) {
        return mList.get(pos);
    }

    public void addContent(List<BaseEntity2.Content> list) {
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
        public AppCompatTextView mTitleView1;

        public TextView mUpdateTv;
        public TextView mRankTv;

        public CardHolder(View itemView) {
            super(itemView);
            mPreviewImage = $(R.id.video_preview);
            mUpdateTv = $(R.id.video_update);
            mTitleView = $(R.id.video_title);
            mTitleView1 = $(R.id.video_title1);
            mRankTv = $(R.id.video_num);
            mRankTv.setVisibility(View.VISIBLE);
        }

    }
}
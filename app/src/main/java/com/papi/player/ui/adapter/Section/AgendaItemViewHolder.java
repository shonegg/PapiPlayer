package com.papi.player.ui.adapter.Section;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.papi.player.R;

/**
 * Author   Shone
 * Date     03/07/16.
 * Github   https://github.com/shonegg
 */
public class AgendaItemViewHolder extends RecyclerView.ViewHolder{
    private View parentView;

    public ImageView mPreviewImage;
    public AppCompatTextView mTitleView;
    public AppCompatTextView mTitleView1;
    public TextView mUpdateTv;

    public AgendaItemViewHolder(View itemView) {
        super(itemView);
        this.parentView = itemView;

        mPreviewImage = (ImageView) itemView.findViewById(R.id.video_preview);
        mUpdateTv = (TextView) itemView.findViewById(R.id.video_update);
        mTitleView = (AppCompatTextView) itemView.findViewById(R.id.video_title);
        mTitleView1 = (AppCompatTextView) itemView.findViewById(R.id.video_title1);
    }

    public View getParentView() {
        return parentView;
    }

}

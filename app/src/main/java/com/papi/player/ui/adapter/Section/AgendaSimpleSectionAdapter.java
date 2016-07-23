package com.papi.player.ui.adapter.Section;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.papi.player.R;
import com.papi.player.bean.BaseEntity2;
import com.papi.player.util.SimpleImageLoader;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     03/07/16.
 * Github   https://github.com/shonegg
 */
public class AgendaSimpleSectionAdapter extends SimpleSectionedAdapter<AgendaItemViewHolder> {


    private List<BaseEntity2.Block> mList = new ArrayList<>();

    public void update(List<BaseEntity2.Block> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    protected String getSectionHeaderTitle(int section) {
        return mList.get(section).name;
    }

    @Override
    protected int getSectionCount() {
        return mList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return getItem(section).contentList.size();
    }

    @Override
    protected AgendaItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_item_fragment_recommend, parent, false);
        return new AgendaItemViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(final AgendaItemViewHolder holder, int section, int position) {
        holder.mTitleView.setText(getItemSub(section, position).name);
        holder.mTitleView1.setText(getItemSub(section, position).aword);
        SimpleImageLoader.display(getItemSub(section, position).still,
                holder.mPreviewImage);
        holder.mUpdateTv.setText(getItemSub(section, position).update);

        final int tmpSection = section;
        final int tmpPosition = position;

        holder.getParentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(getItemSub(tmpSection, tmpPosition), holder);
                }
            }
        });
    }
    protected @LayoutRes int getLayoutResource(){
        return R.layout.card_view_header;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(BaseEntity2.Content content, AgendaItemViewHolder holder);
    }
    public BaseEntity2.Content getItemSub(int section, int pos) {
        BaseEntity2.Block item = mList.get(section);
        return item.contentList.get(pos);
    }

    public BaseEntity2.Block getItem(int section) {
        return mList.get(section);
    }
}
package com.papi.player.ui.adapter.list;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.papi.player.R;
import com.papi.player.bean.HomePageBean;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.SimpleImageLoader;

import java.util.ArrayList;

public class RecommendRecyclerAdapter extends AbsRecyclerViewAdapter {

	private ArrayList<HomePageBean.BlockBean.ContentBean> mList;

	public RecommendRecyclerAdapter(RecyclerView recyclerView,
									ArrayList<HomePageBean.BlockBean.ContentBean> list) {
		super(recyclerView);
		this.mList = list;
	}

	@Override
	public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		bindContext(parent.getContext());
		View v = LayoutInflater.from(getContext())
				.inflate(R.layout.card_item_home_recommend, parent, false);
		return new CardHolder(v);
	}

	@Override
	public void onBindViewHolder(ClickableViewHolder cvh, int position) {
		super.onBindViewHolder(cvh, position);
		if (cvh instanceof CardHolder) {
			CardHolder holder = (CardHolder) cvh;
			holder.mTitleView.setText(getVideoItem(position).name);
			SimpleImageLoader.display(getVideoItem(position).still,
					holder.mPreviewImage);
		}
	}

	public HomePageBean.BlockBean.ContentBean getVideoItem(int pos) {
		return mList.get(pos);
	}

	@Override
	public int getItemCount() {
		return mList != null ? mList.size() : 0;
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

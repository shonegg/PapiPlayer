package com.papi.player.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ScrollView;

import com.papi.player.R;
import com.papi.player.bean.HomePageBean;

@SuppressLint("ValidFragment")
public class PlaceholderFragment extends BaseHomeFragment {

	private ScrollView mScrollView;

	public static PlaceholderFragment newInstance() {
		PlaceholderFragment fragment = new PlaceholderFragment();
		return fragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_tab_placeholder;
	}

	@Override
	public void finishCreateView(Bundle state) {
		mScrollView = $(R.id.scrollable);

	}

	@Override
	public void scrollToTop() {
		mScrollView.smoothScrollTo(mScrollView.getScrollX(), 0);
	}

	@Override
	public boolean canScrollVertically(int direction) {
		return mScrollView != null && mScrollView.canScrollVertically(direction);
	}

	@Override
	public void notifyIndexDataUpdate(HomePageBean data) {

	}

}

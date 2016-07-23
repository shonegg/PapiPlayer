package com.papi.player.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;

import java.util.ArrayList;

import com.papi.player.R;
import com.papi.player.api.HomePageAPi;
import com.papi.player.bean.HomePageBean;
import com.papi.player.model.BasicMessage;
import com.papi.player.ui.adapter.pager.BannerPagerAdapter;
import com.papi.player.ui.adapter.pager.HomePagerAdapter;
import com.papi.player.ui.common.LazyFragment;
import com.papi.player.util.AsyncTask;
import com.papi.player.util.Utility;
import com.papi.player.view.CircleIndicator;
import com.papi.player.view.CircleProgressView;
import com.papi.player.view.SlidingTabLayout;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;
import tv.danmaku.bili.widget.LoadingImageView;

public class HomeFragment extends LazyFragment {

	private ViewPager mBannerPager, mTabPager;
	private BannerPagerAdapter mBannerAdapter;
	private HomePagerAdapter mHomeAdapter;
	private CircleIndicator mBannerIndicator;
	private SlidingTabLayout mSlidingTab;
	private ScrollableLayout mScrollableLayout;

	private View mAppBarLayout, mAppBarBackground;

	private HomePageBean mIndexData;

	private int APP_BAR_HEIGHT, TOOLBAR_HEIGHT, TAB_HEIGHT, STATUS_BAR_HEIGHT = 0;
	private LoadingImageView mLoadingView;

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_section_home;
	}

	@Override
	public void finishCreateView(Bundle state) {
		APP_BAR_HEIGHT = getResources().getDimensionPixelSize(R.dimen.appbar_parallax_max_height);
		TAB_HEIGHT = getResources().getDimensionPixelSize(R.dimen.appbar_tab_height);
		TOOLBAR_HEIGHT = getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
		if (Build.VERSION.SDK_INT >= 19) {
			STATUS_BAR_HEIGHT = Utility.getStatusBarHeight(getApplicationContext());
		}

		mBannerPager = $(R.id.banner_pager);
		mTabPager = $(R.id.tab_pager);
		mBannerIndicator = $(R.id.pager_indicator);
		mSlidingTab = $(R.id.sliding_tabs);
		mAppBarLayout = $(R.id.appbar_layout);
		mAppBarBackground = $(R.id.appbar_background);
		mScrollableLayout = $(R.id.scrollable);

		mLoadingView = (LoadingImageView) $(R.id.loading);
		mLoadingView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				startHomeGetTask();
				return false;
			}
		});
		ViewCompat.setElevation(mAppBarLayout, getResources().getDimensionPixelSize(R.dimen.toolbar_elevation));
		startHomeGetTask();
	}

	private void initPageAdapter(String[] titles) {

		mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(),
				getApplicationContext(), titles);
		mTabPager.setAdapter(mHomeAdapter);

		mSlidingTab.setCustomTabView(R.layout.tab_indicator, R.id.tab_text);
		mSlidingTab.setSelectedIndicatorColors(Color.parseColor("#FFFFFF"));
//		mSlidingTab.setSelectedIndicatorColors(getResources().getColor(android.R.color.white));
		mSlidingTab.setDistributeEvenly(true);
		mSlidingTab.setOnTabItemClickListener(new SlidingTabLayout.OnTabItemClickListener() {
			@Override
			public void onTabItemClick(int pos) {
				if (pos != mTabPager.getCurrentItem()) return;
				mHomeAdapter.scrollToTop(pos);
			}
		});
		mSlidingTab.setViewPager(mTabPager);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		lp.setMargins(0, STATUS_BAR_HEIGHT + TOOLBAR_HEIGHT + TAB_HEIGHT, 0, 0);
		mTabPager.setLayoutParams(lp);
		mScrollableLayout.setMaxScrollY(APP_BAR_HEIGHT - STATUS_BAR_HEIGHT - TOOLBAR_HEIGHT - TAB_HEIGHT);
		mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
			@Override
			public boolean canScrollVertically(int i) {
				return mHomeAdapter.canScrollVertically(mTabPager.getCurrentItem(), i);
			}
		});
		mScrollableLayout.setOnScrollChangedListener(new OnScrollChangedListener() {
			@Override
			public void onScrollChanged(int y, int oldY, int maxY) {
				float tabsTransitionY = y < maxY ? 0 : y - maxY;
				mSlidingTab.setTranslationY(tabsTransitionY);
				if (mBannerAdapter != null) {
					mBannerAdapter.setBannerImageTransitionY(tabsTransitionY * 0.7f);
				}

				float alpha = ((float) y) / (float) maxY;
				mAppBarBackground.setAlpha(alpha);
			}
		});
	}



	private void startHomeGetTask() {
		new HomePageGetTask().execute();
		mLoadingView.showProgress();
		mTabPager.setVisibility(View.GONE);
	}

	public HomePageBean getIndexData() {
		return this.mIndexData;
	}

	private class HomePageGetTask extends AsyncTask<Void, Void, BasicMessage<HomePageBean>> {

		@Override
		protected BasicMessage<HomePageBean> doInBackground(Void... params) {
			return HomePageAPi.getHomePageInfo();
		}

		@Override
		protected void onPostExecute(BasicMessage<HomePageBean> msg) {
			mLoadingView.hideAll();
			mTabPager.setVisibility(View.GONE);

			if (msg != null && msg.getCode() == BasicMessage.CODE_SUCCEED) {
				mBannerIndicator.setVisibility(View.VISIBLE);
				mTabPager.setVisibility(View.VISIBLE);

				HomePageBean homePageBean = msg.getObject();
                if (homePageBean == null){
					return;
				}
				parseHomePage(homePageBean);
			} else {
				if (getSupportActivity() == null){
					return;
				}
				mLoadingView.showError();
				Snackbar.with(getApplicationContext())
						.text(R.string.tips_cannot_get_banner)
						.actionLabel(R.string.snackbar_try_again)
						.actionColorResource(R.color.pink_dark)
						.duration(1200)
						.actionListener(new ActionClickListener() {
							@Override
							public void onActionClicked(Snackbar snackbar) {
								startHomeGetTask();//再次加载
								snackbar.dismiss();
							}
						})
						.show(getSupportActivity());
			}
		}

	}

	private void parseHomePage(HomePageBean homePageBean) {
        /*初始化banner*/
		ArrayList<HomePageBean.FocusBean> bannerBeanArrayList = new ArrayList<>();
		bannerBeanArrayList.addAll(homePageBean.focusBeanList);

		mBannerAdapter = new BannerPagerAdapter(getChildFragmentManager(), bannerBeanArrayList);
		mBannerPager.setAdapter(mBannerAdapter);
		mBannerIndicator.setViewPager(mBannerPager);

		/*初始化viewpage adapter*/
		if (homePageBean.blockBeanList == null){
			return;
		};
		int length = homePageBean.blockBeanList.size();
		if (length <= 0){
			return;
		}
		String[] titles = new String[length];
		for (int i = 0; i < length; i++){
            titles[i] = homePageBean.blockBeanList.get(i).name;
		}
		initPageAdapter (titles);

		mIndexData = homePageBean;
		mHomeAdapter.notifyIndexDataUpdateAll(mIndexData);
	}

}

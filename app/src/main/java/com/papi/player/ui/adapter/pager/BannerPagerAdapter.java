package com.papi.player.ui.adapter.pager;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import com.papi.player.bean.HomePageBean;
import com.papi.player.ui.fragment.HomeBannerFragment;

public class BannerPagerAdapter extends FragmentPagerAdapter {

	private HomeBannerFragment[] fragments;
	private ArrayList<HomePageBean.FocusBean> items;

	public BannerPagerAdapter(FragmentManager fm, ArrayList<HomePageBean.FocusBean> items) {
		super(fm);
		fragments = new HomeBannerFragment[items.size()];
		this.items = items;
	}

	@Override
	public Fragment getItem(int position) {
		if (fragments[position] == null) {
			fragments[position] = HomeBannerFragment.newInstance(items.get(position));
		}
		return fragments[position];
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void setBannerImageTransitionY(float y) {
		for (HomeBannerFragment fragment : fragments) {
			if (fragment != null) {
				fragment.setImageTransitionY(y);
			}
		}
	}

}

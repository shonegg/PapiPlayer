package com.papi.player.ui.fragment.home;

import com.papi.player.bean.HomePageBean;
import com.papi.player.ui.common.LazyFragment;

public abstract class BaseHomeFragment extends LazyFragment {

	public abstract void scrollToTop();

	public abstract boolean canScrollVertically(int direction);

	public abstract void notifyIndexDataUpdate(HomePageBean data);

}

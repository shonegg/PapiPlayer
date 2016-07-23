package com.papi.player.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.papi.player.R;

import com.papi.player.bean.HomePageBean;
import com.papi.player.ui.common.BrowserActivity;
import com.papi.player.ui.common.LazyFragment;
import com.papi.player.util.SimpleImageLoader;

public class HomeBannerFragment extends LazyFragment {

	private ImageView mImageView;
	private TextView mTitleText;

	private HomePageBean.FocusBean item;

	private static final String ARG_BANNER_JSON = "args_banner_json";

	public static HomeBannerFragment newInstance(HomePageBean.FocusBean banner) {
		HomeBannerFragment fragment = new HomeBannerFragment();
		Bundle data = new Bundle();
		data.putString(ARG_BANNER_JSON, new Gson().toJson(banner));
		fragment.setArguments(data);
		return fragment;
	}


	@Override
	public int getLayoutResId() {
		return R.layout.fragment_home_banner;
	}

	@Override
	public void finishCreateView(Bundle state) {
		item = new Gson().fromJson(getArguments().getString(ARG_BANNER_JSON),
				HomePageBean.FocusBean.class);

		mImageView = $(R.id.banner_image);
		mTitleText = $(R.id.banner_title);

		$(R.id.banner_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/**ChromeTabCompat tab = new ChromeTabCompat.Builder(item.link)
						.toolbarColor(getResources().getColor(R.color.pink_500))
						.setShowTitle(true)
						.setCloseButtonStyle(ChromeTabCompat.CLOSE_BUTTON_ARROW)
						.build();
				tab.start(getApplicationContext());*/
//				if (UrlHelper.isVideoUrl(item.banner)) {
//					VideoViewActivity.launch(getSupportActivity(), UrlHelper.getAVfromVideoUrl(item.banner));
//				} else {
					BrowserActivity.launch(getSupportActivity(), item.banner, item.aword);
//				}
			}
		});

		int paddingBottom = getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
		paddingBottom += getResources().getDimensionPixelSize(R.dimen.circle_indicator_default_height);

		int paddingStart = mTitleText.getPaddingStart();
		int paddingEnd = mTitleText.getPaddingEnd();
		int paddingTop = mTitleText.getPaddingTop();

		mTitleText.setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom);

		mTitleText.setText(item.name);
		SimpleImageLoader.display(item.banner, mImageView);
	}

	public void setImageTransitionY(float y) {
		mImageView.setTranslationY(y);
	}

}

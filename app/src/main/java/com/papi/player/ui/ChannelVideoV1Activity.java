package com.papi.player.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.ui.common.ChannelBaseActivity;
import com.papi.player.ui.fragment.video.VideoRankFragment;
import com.papi.player.ui.fragment.video.VideoRecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   Shone
 * Date     02/07/16.
 * Github   https://github.com/shonegg
 */
public class ChannelVideoV1Activity extends ChannelBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Constants.color_primary);
        setContentView(R.layout.activity_channel_sub);
    }

    private Bundle b;
    private String mChannelCode;
    private String mChannelID;
    private String mChannelName;

    private void initBundleData() {
        this.b = new Bundle();
        mChannelID = this.getChannelId();
        mChannelCode = this.getChannelCode();
        mChannelName = this.getChannelName();
        this.b.putString("CHANNEL_ID", this.getChannelId());
        this.b.putString("CHANNEL_CODE", this.getChannelCode());
        this.b.putString("CHANNEL_NAME", this.getChannelName());
    }

    @Override
    protected void setUpViews() {
        initBundleData();

        mToolbar.setTitle("" + getChannelName());
        setSupportActionBar(mToolbar);

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static void start(Context ctx, String code, String id, String name) {
        ChannelVideoV1Activity.start(ctx, ChannelVideoV1Activity.class, code, id, name);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(VideoRecommendFragment.newInstance(this.b), "推荐");

        adapter.addFragment(VideoRankFragment.newInstance(this.b), "排行");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


}

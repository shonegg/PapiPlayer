package com.papi.player.ui;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.ui.fragment.channel.ChannelRankFragment;
import com.papi.player.ui.fragment.channel.ChannelRecommendFragment;
import com.papi.player.ui.internal.OpenChannel;
import com.papi.player.util.log.ILog;

import java.util.ArrayList;
import java.util.List;

public class ChannelMediaActivity extends AbsActivity {


    public static String KEY_CHANNEL_ID = "CHANNEL_ID";
    public static String KEY_CHANNEL_NAME = "CHANNEL_NAME";
    public static String KEY_CHANNEL_CODE = "CHANNEL_CODE";
    public static String KEY_CHANNEL_TEMPLATE = "TEMPLATE";
    private String mChannelCode;
    private String mChannelID;
    private String mChannelName;
    private String mChannelTemplate;

    public static void launch(Context ctx, String code, String id, String name, OpenChannel.Template template) {
        Intent intent = new Intent(ctx, ChannelMediaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(KEY_CHANNEL_CODE, code);
        intent.putExtra(KEY_CHANNEL_ID, id);
        intent.putExtra(KEY_CHANNEL_NAME, name);
        intent.putExtra(KEY_CHANNEL_TEMPLATE, template.name);

        ctx.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Constants.color_primary);
        setContentView(R.layout.activity_channel_sub);
    }


    @Override
    protected void setUpViews() {
        Intent intent = getIntent();
        mChannelID = intent.getStringExtra(KEY_CHANNEL_ID);
        mChannelName = intent.getStringExtra(KEY_CHANNEL_NAME);
        mChannelCode = intent.getStringExtra(KEY_CHANNEL_CODE);
        mChannelTemplate = intent.getStringExtra(KEY_CHANNEL_TEMPLATE);

        if (TextUtils.isEmpty(mChannelID)) {
            finish();
            return;
        }

        mToolbar.setTitle("" + mChannelName);
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


    private void setupViewPager(ViewPager viewPager) {
        ILog.e(ILog.TAG_DEFAULT, getClass().getName() + " channelID:" + mChannelID);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(ChannelRecommendFragment.newInstance(
                mChannelID, mChannelCode, mChannelName, mChannelTemplate), "推荐");
//      adapter.addFragment(new CheeseListFragment(), "专辑");
        adapter.addFragment(ChannelRankFragment.newInstance(
                mChannelID, mChannelCode, mChannelName, mChannelTemplate), "排行");
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


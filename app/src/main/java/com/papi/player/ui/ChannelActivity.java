package com.papi.player.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.papi.player.AppContext;
import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.bean.Channel;
import com.papi.player.bean.ChannelsEntity;
import com.papi.player.presenter.ChannelPresenter;
import com.papi.player.presenter.ChannelTaskContract;
import com.papi.player.ui.adapter.list.ChannelRecyclerAdapter;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.ui.common.BrowserActivity;
import com.papi.player.ui.internal.OpenChannel;
import com.papi.player.util.RandomUtils;
import com.papi.player.util.Toastor;

import java.util.List;

import tv.danmaku.bili.widget.LoadingImageView;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelActivity extends AbsActivity implements ChannelTaskContract.View {

    private RecyclerView mGrid;
    private ChannelTaskContract.Presenter mPresenter;
    private ChannelRecyclerAdapter mAdapter;
    private LoadingImageView mLoadingView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static void launch(AppCompatActivity activity) {
        Intent intent = new Intent(activity, ChannelActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Constants.color_primary);
        setContentView(R.layout.activity_channel);
    }


    @Override
    protected void setUpViews() {
        /***toolbar setting***/
        mToolbar.setTitle("频道");
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
        /*********************/
        mSwipeRefreshLayout = (SwipeRefreshLayout) $(R.id.swipeRefreshLayout);
        mGrid = (RecyclerView) $(R.id.grid);

        initRefreshLayout();
        ChannelPresenter.bindPresenter(this);

        mSwipeRefreshLayout.setVisibility(View.GONE);
        mPresenter.start();
    }

    private void initRefreshLayout (){
        mSwipeRefreshLayout.setColorScheme(R.color.pink,
                R.color.blue,
                R.color.black,
                R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AppContext.getMainHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout != null){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, RandomUtils.delay());

            }
        });
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mGrid.setNestedScrollingEnabled(false);
        mGrid.setHasFixedSize(false);


        mLoadingView = (LoadingImageView) $(R.id.loading);
        mLoadingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPresenter.start();
                return false;
            }
        });
        int spanCount = 3;
        mGrid.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                spanCount));

        initAdapter();
    }

    private void initAdapter() {

        mAdapter = new ChannelRecyclerAdapter(mGrid);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                //// TODO: 19/06/16
                ChannelsEntity.ChannelBean item = mAdapter.getItem(position);

                Channel channel = new Channel();
                channel.setCode(item.code);
                channel.setIcon1(item.icon1);
                channel.setIcon2(item.icon2);
                channel.setIcon3(item.icon3);
                channel.setIcon4(item.icon4);
                channel.setId(item.id);
                channel.setName(item.name);
                channel.setTemplate(item.template);
                channel.setUrl(item.url);

                OpenChannel.getInstance().open(ChannelActivity.this, channel);

            }
        });
        mGrid.setAdapter(mAdapter);
    }

    @Override
    public void startLoading() {
        mGrid.setVisibility(View.GONE);
        mLoadingView.showProgress();
    }


    @Override
    public void onSuccess(List<ChannelsEntity.ChannelBean> bean) {
        mGrid.setVisibility(View.VISIBLE);
        mLoadingView.hideAll();
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mAdapter.addContent(bean);
    }

    @Override
    public void onFail() {
        AppContext.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mGrid.setVisibility(View.GONE);
                mLoadingView.showError();
            }
        }, 500);

    }

    @Override
    public void setPresenter(ChannelTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

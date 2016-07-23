package com.papi.player.ui.fragment.video;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.papi.player.AppContext;
import com.papi.player.R;
import com.papi.player.api.VideoRecommendApi;
import com.papi.player.bean.BaseEntity2;
import com.papi.player.bean.VideoRecommendEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.ui.adapter.Section.AgendaItemViewHolder;
import com.papi.player.ui.adapter.Section.AgendaSimpleSectionAdapter;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.ui.common.ChannelBaseActivity;
import com.papi.player.ui.internal.OpenMovie;
import com.papi.player.util.AsyncTask;

import java.util.Random;

import tv.danmaku.bili.widget.LoadingImageView;

/**
 * Author   Shone
 * Date     02/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoRecommendFragment extends VideoTemplateBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private AgendaSimpleSectionAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;

    public static Fragment newInstance(Bundle b) {
        VideoRecommendFragment fragment = new VideoRecommendFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_channel_recommend;
    }

    private RecyclerView mRecyclerView;

    private void initAdapter(RecyclerView recyclerView) {
        mAdapter = new AgendaSimpleSectionAdapter();
        mAdapter.setOnItemClickListener(new AgendaSimpleSectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseEntity2.Content content, AgendaItemViewHolder holder) {
                if (content != null) {
                    String mID = content.id;
                    if (TextUtils.isEmpty(mID)) {
                        mID = content.mid;
                    }
                    Activity activity = getSupportActivity();
                    String channelId = ((ChannelBaseActivity) activity).getChannelId();
                    String channelName = ((ChannelBaseActivity) activity).getChannelName();
                    String channelCode = ((ChannelBaseActivity) activity).getChannelCode();

                    OpenMovie.getIntance().open(getSupportActivity(), content.template,
                            mID, channelId, channelCode, content.url, content.still);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        mRecyclerView = $(R.id.grid);
        mSwipeRefreshWidget = (SwipeRefreshLayout) $(R.id.swipe_refresh_widget);
        initRefreshLayout();

        mSwipeRefreshWidget.setRefreshing(true);
        onRefresh();

        new InnerTask().execute(mChannelId);
    }
    private void initRefreshLayout (){
        mSwipeRefreshWidget.setColorScheme(R.color.pink,
                R.color.black,
                R.color.blue,
                R.color.green);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        initAdapter(mRecyclerView);
        mSwipeRefreshWidget.setVisibility(View.VISIBLE);
    }

    @Override
    protected boolean loadData() {
        return false;
    }

    @Override
    public void onNetAvailable() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setRefreshOrLoadMoreFlag() {

    }

    private void stopRefresh(){
        if (mSwipeRefreshWidget != null){
            mSwipeRefreshWidget.setRefreshing(false);
        }
    }

    private int delay(){
        int max = 2000;
        int min = 800;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    @Override
    public void onRefresh() {
        AppContext.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefresh();
            }
        }, delay());
    }

    private class InnerTask extends AsyncTask<String, Void, BasicMessage<VideoRecommendEntity>> {

        @Override
        protected BasicMessage<VideoRecommendEntity> doInBackground(String... params) {
            return VideoRecommendApi.get(params[0]);
        }

        @Override
        protected void onPostExecute(BasicMessage<VideoRecommendEntity> msg) {
            if (msg != null) {
                VideoRecommendEntity entity = msg.getObject();
                if (entity != null && entity.isOK()) {
                    mAdapter.update(entity.blockList);
                }
            }
        }
    }
}

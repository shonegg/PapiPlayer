package com.papi.player.ui.fragment.video;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.papi.player.AppContext;
import com.papi.player.R;
import com.papi.player.api.VideoTopApi;
import com.papi.player.bean.BaseEntity2;
import com.papi.player.bean.VideoRankEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.ui.adapter.list.VideoTopAdapter;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.ui.internal.OpenMovie;
import com.papi.player.util.AsyncTask;
import com.papi.player.util.RandomUtils;

import java.util.List;
import java.util.Random;

import tv.danmaku.bili.widget.LoadingImageView;

/**
 * Author   Shone
 * Date     03/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoRankFragment extends VideoTemplateBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private VideoTopAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;

    public static Fragment newInstance(Bundle b) {
        VideoRankFragment fragment = new VideoRankFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_channel_recommend;
    }

    private RecyclerView mRecyclerView;

    @Override
    protected void initView() {
        mRecyclerView = $(R.id.grid);
        mSwipeRefreshWidget = (SwipeRefreshLayout) $(R.id.swipe_refresh_widget);
        initRefreshLayout();

        mSwipeRefreshWidget.setRefreshing(true);
        onRefresh();
        startTask(1);//排行是没有页数的哦
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
        int spanCount = 1;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                spanCount));
        initAdapter(mRecyclerView);
    }

    private void startTask(int page) {
        new InnerTask().execute();
    }

    private void initAdapter(RecyclerView recyclerView) {
        mAdapter = new VideoTopAdapter(getSupportActivity(), recyclerView);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                BaseEntity2.Content content = mAdapter.getItem(position);
                OpenMovie.getIntance().open(getSupportActivity(), OpenMovie.Template.VPLAY,
                        content.id,  mChannelId,  mChannelCode, null, content.still);

            }
        });
        recyclerView.setAdapter(mAdapter);
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


    private void updateAdapter(List<BaseEntity2.Content> object) {
        mAdapter.addContent(object);
    }

    @Override
    public void onRefresh() {
        AppContext.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefresh();
            }
        }, RandomUtils.delay());
    }

    private void stopRefresh(){
        if (mSwipeRefreshWidget != null){
            mSwipeRefreshWidget.setRefreshing(false);
        }
    }

    class InnerTask extends AsyncTask<Void, Void, BasicMessage<VideoRankEntity>> {

        @Override
        protected BasicMessage<VideoRankEntity> doInBackground(Void... params) {
            return VideoTopApi.get(mChannelId);
        }

        @Override
        protected void onPostExecute(BasicMessage<VideoRankEntity> entity) {

            if (entity.getCode() == BasicMessage.CODE_ERROR) {
                mRecyclerView.setVisibility(View.GONE);
            } else {
                updateAdapter(entity.getObject().getContentList());
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

}

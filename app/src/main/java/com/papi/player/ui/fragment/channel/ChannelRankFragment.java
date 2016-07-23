package com.papi.player.ui.fragment.channel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.papi.player.AppContext;
import com.papi.player.R;
import com.papi.player.api.ChannelMediaApi;
import com.papi.player.bean.ChannelMediaEntity;
import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.ui.MediaInfoActivity;
import com.papi.player.ui.adapter.list.ChannelRankAdapter;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.AsyncTask;
import com.papi.player.util.log.ILog;

import java.util.List;
import java.util.Random;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelRankFragment extends ChannelBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_TYPE = "arg_type";
    private static final String ARG_CHANNEL = "arg_channel";


    private ChannelRankAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;

    public static Fragment newInstance(String channelID, String channelCode,
                                       String channelName, String channelTemplate) {
        ChannelRankFragment fragment = new ChannelRankFragment();
        Bundle args = new Bundle();
        args.putString("CHANNEL_ID", channelID);
        args.putString("CHANNEL_CODE", channelCode);
        args.putString("TEMPLATE", channelTemplate);
        args.putString("CHANNEL_NAME", channelName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_channel_recommend;
    }

    private RecyclerView mRecyclerView;

    private void initAdapter(RecyclerView recyclerView) {
        mAdapter = new ChannelRankAdapter(getSupportActivity(), recyclerView);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                MediaInfoActivity.start(ChannelRankFragment.this.getActivity(), new EnterMediaEntity(
                        mAdapter.getItem(position).id, null, null, 0, ChannelRankFragment.this.mChannelId, ChannelRankFragment
                        .this.mChannelCode, null));

            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    private void startTask(int page) {
        new InnerTask().execute();
    }

    private void updateAdapter(List<ChannelMediaEntity.MediaBean> object) {
        mAdapter.addContent(object);
    }

    @Override
    protected void initView() {
        mRecyclerView = $(R.id.grid);
        mSwipeRefreshWidget = (SwipeRefreshLayout) $(R.id.swipe_refresh_widget);

        initRefreshLayout();

        mSwipeRefreshWidget.setRefreshing(true);
        onRefresh();
        startTask(1);
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

    private void stopRefresh(){
        if (mSwipeRefreshWidget != null){
            mSwipeRefreshWidget.setRefreshing(false);
        }
    }

    class InnerTask extends AsyncTask<Void, Void, BasicMessage<ChannelMediaEntity>> {

        @Override
        protected BasicMessage<ChannelMediaEntity> doInBackground(Void... params) {
            return ChannelMediaApi.getRankEntity(mChannelId);
        }

        @Override
        protected void onPostExecute(BasicMessage<ChannelMediaEntity> entity) {
            if (entity.getCode() == BasicMessage.CODE_ERROR) {
            } else {
                ILog.e(ILog.TAG_DEFAULT, "啦数据成功: " + entity.getObject().mediaBeanList.size());
                updateAdapter(entity.getObject().mediaBeanList);
            }
        }
    }

}

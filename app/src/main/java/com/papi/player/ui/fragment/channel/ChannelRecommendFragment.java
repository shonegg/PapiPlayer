package com.papi.player.ui.fragment.channel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.papi.player.AppContext;
import com.papi.player.R;
import com.papi.player.api.ChannelMediaApi;
import com.papi.player.bean.ChannelMediaEntity;
import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.ui.MediaInfoActivity;
import com.papi.player.ui.adapter.list.ChannelRecommendAdapter1;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.AsyncTask;
import com.papi.player.net.NetworkUtils;
import com.papi.player.util.RecyclerViewStateUtils;
import com.papi.player.util.log.ILog;
import com.papi.player.view.refresh.LoadingFooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelRecommendFragment extends ChannelBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ChannelRecommendAdapter1 mAdapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    public static Fragment newInstance(String channelID, String channelCode,
                                       String channelName, String channelTemplate) {
        ChannelRecommendFragment fragment = new ChannelRecommendFragment();
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

    private void initAdapter(RecyclerView recyclerView) {


        mAdapter = new ChannelRecommendAdapter1(recyclerView);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                MediaInfoActivity.start(getSupportActivity(), new EnterMediaEntity(mAdapter.getItem(position).id,
                        null, null, 0, ChannelRecommendFragment.this.mChannelId,
                        ChannelRecommendFragment.this.mChannelCode, null));

            }
        });
        recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if (state == LoadingFooter.State.Loading) {
                Log.e(ILog.TAG_DEFAULT, "the state is Loading, just wait..");
                return;
            }
            boolean isLoadingOnePage = (mCurrentPage == 1);//是否加载第一页
            if (mCurrentPage < 5) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(getSupportActivity(), mRecyclerView,
                        isLoadingOnePage, LoadingFooter.State.Loading, null);
                requestData();//请求数据
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(getSupportActivity(), mRecyclerView,
                        isLoadingOnePage, LoadingFooter.State.TheEnd, null);
            }
        }
    };


    private void requestData() {
        new InnerTask().execute(mCurrentPage);
    }

    private void updateAdapter(List<ChannelMediaEntity.MediaBean> object) {
        if (object == null) {
            return;
        }
        List<ChannelMediaEntity.MediaBean> mList = mAdapter.getList();
        if (mList.size() <= 0) {
            mAdapter.addContent(object);
            for (int i = 0; i < object.size(); i++) {
                Log.e(ILog.TAG_DEFAULT, "加载:" + object.get(i).name);
            }
            return;
        }
        List<ChannelMediaEntity.MediaBean> tmp = new ArrayList<>();

        for (int i = 0; i < object.size(); i++) {
            String mid = object.get(i).name;
            boolean isIn = false;
            for (int k = 0; k < mList.size(); k++) {
                String id = mList.get(k).name;
                if (id.equals(mid)) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                tmp.add(object.get(i));
                Log.e(ILog.TAG_DEFAULT, "加载:" + object.get(i).name);
            }
        }
        if (tmp.size() > 0) {
            mAdapter.addContent(tmp);
        }
    }

    @Override
    protected void initView() {
        mRecyclerView = $(R.id.grid);
        mSwipeRefreshWidget = (SwipeRefreshLayout) $(R.id.swipe_refresh_widget);
        initRefreshLayout();

        mSwipeRefreshWidget.setRefreshing(true);
        requestData();
    }

    private void initRefreshLayout() {
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

        mLayoutManager = new LinearLayoutManager(getSupportActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


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

    private int delay() {
        int max = 2000;
        int min = 800;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    private boolean isLoadingMore = false;

    @Override
    public void onRefresh() {
        if (mCurrentPage == 1){
            requestData();
            return;
        }
        AppContext.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefresh();
            }
        }, delay());
    }

    private void stopRefresh() {
        if (mSwipeRefreshWidget != null) {
            mSwipeRefreshWidget.setRefreshing(false);
        }
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getSupportActivity(),
                    mRecyclerView, mCurrentPage == 1, LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    class InnerTask extends AsyncTask<Integer, Void, BasicMessage<ChannelMediaEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoadingMore = true;
        }

        @Override
        protected BasicMessage<ChannelMediaEntity> doInBackground(Integer... params) {
            int page = params[0];
            return ChannelMediaApi.getRecommendEntity(mChannelId, page);
        }

        @Override
        protected void onPostExecute(BasicMessage<ChannelMediaEntity> entity) {
            stopRefresh();
            isLoadingMore = false;

            if (entity != null && entity.getCode() == BasicMessage.CODE_SUCCEED) {
                mSwipeRefreshWidget.setVisibility(View.VISIBLE);
                mCurrentPage++;//成功后+1
                updateAdapter(entity.getObject().mediaBeanList);
                RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
            } else {
                boolean isNetOk = NetworkUtils.isNetAvailable(getApplicationContext());
                if (isNetOk){
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                } else {
                    RecyclerViewStateUtils.setFooterViewState(getSupportActivity(), mRecyclerView,
                            mCurrentPage == 1, LoadingFooter.State.NetWorkError, mFooterClick);
                }
            }
        }
    }

}

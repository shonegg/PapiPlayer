package com.papi.player.ui.fragment.channel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.papi.player.R;
import com.papi.player.api.ChannelMediaApi;
import com.papi.player.bean.ChannelMediaEntity;
import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.ui.MediaInfoActivity;
import com.papi.player.ui.adapter.list.ChannelRecommendAdapter1;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.AsyncTask;

import java.util.List;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
@Deprecated
public class ChannelAlbumFragment extends ChannelBaseFragment {

    private ChannelRecommendAdapter1 mAdapter;

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

    private RecyclerView mRecyclerView;

    private void initAdapter(RecyclerView recyclerView) {
        mAdapter = new ChannelRecommendAdapter1(recyclerView);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                MediaInfoActivity.start(getSupportActivity(), new EnterMediaEntity(mAdapter.getItem(position).id,
                        null, null, 0, ChannelAlbumFragment.this.mChannelId,
                        ChannelAlbumFragment.this.mChannelCode, null));

            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    private void startTask(int page) {
        new InnerTask().execute(page);
    }

    private void updateAdapter(List<ChannelMediaEntity.MediaBean> object) {
        mAdapter.addContent(object);
    }

    @Override
    protected void initView() {
        mRecyclerView = $(R.id.grid);
        mRecyclerView.setHasFixedSize(true);
        int spanCount = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                spanCount));
        initAdapter(mRecyclerView);

        startTask(1);
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


    class InnerTask extends AsyncTask<Integer, Void, BasicMessage<ChannelMediaEntity>> {

        @Override
        protected BasicMessage<ChannelMediaEntity> doInBackground(Integer... params) {
            int page = params[0];
            return ChannelMediaApi.getRecommendEntity(mChannelId, page);
        }

        @Override
        protected void onPostExecute(BasicMessage<ChannelMediaEntity> entity) {
            if (entity != null && entity.getCode() == BasicMessage.CODE_SUCCEED) {
                updateAdapter(entity.getObject().mediaBeanList);
            } else {
                //加载失败


            }
        }
    }

}

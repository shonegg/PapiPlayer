package com.papi.player.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import com.papi.player.R;
import com.papi.player.bean.HomePageBean;
import com.papi.player.ui.adapter.list.RecommendRecyclerAdapter;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.ui.fragment.HomeFragment;
import com.papi.player.ui.internal.OpenMovie;
import com.papi.player.util.log.ILog;

public class SimpleListFragment extends BaseHomeFragment {

    private RecyclerView mRecyclerView;
    private RecommendRecyclerAdapter mAdapter;
    private HomePageBean mIndexData;
    private int indexType;
    private ArrayList<HomePageBean.BlockBean.ContentBean> items;
    private HomePageBean.BlockBean mBlockBean;
    private static final String ARG_TYPE = "arg_type";

    public static SimpleListFragment newInstance(int position) {
        SimpleListFragment fragment = new SimpleListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_tab_recycler;
    }

    @Override
    public void finishCreateView(Bundle state) {
        indexType = getArguments().getInt(ARG_TYPE);

        mRecyclerView = $(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        int spanCount = 1;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                spanCount));

        if (getParentFragment() != null && getParentFragment() instanceof HomeFragment) {
            HomePageBean indexData = ((HomeFragment) getParentFragment()).getIndexData();
            if (indexData != null) {
                mIndexData = indexData;
                makeUpItems();
            }

        }

        mAdapter = new RecommendRecyclerAdapter(mRecyclerView, items);
        setUpRecyclerAdapter(mAdapter);
    }


    private void makeUpItems() {
        if (mIndexData != null) {
            mBlockBean = mIndexData.blockBeanList.get(indexType);
            if (mBlockBean == null) {
                items = new ArrayList<>();
                return;
            }
            items = mBlockBean.contentBeanList;
        } else {
            items = new ArrayList<>();
        }

    }

    private void setUpRecyclerAdapter(RecommendRecyclerAdapter adapter) {
        adapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if (mBlockBean == null){
                    return;
                }
                HomePageBean.BlockBean.ContentBean content = items.get(position);
                HomePageBean.BlockBean.ChannelBean channel = mBlockBean.channelBean;
                String id = "";
                String code = "";
                if(channel != null) {
                    id = channel.id; //渠道ID
                    code = channel.code;//渠道code
                }
                OpenMovie.getIntance().open(getActivity(), content.template,
                        content.mid, id, code, content.url, content.still);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return mRecyclerView != null && mRecyclerView.canScrollVertically(direction);
    }

    @Override
    public void notifyIndexDataUpdate(HomePageBean data) {

        if (data == null) return;
        ILog.e(ILog.TAG_DEFAULT, "indexType:" + indexType
                + "  / " + data.blockBeanList.size());
        mIndexData = data;
        makeUpItems();
        mAdapter = new RecommendRecyclerAdapter(mRecyclerView, items);
        setUpRecyclerAdapter(mAdapter);
    }

}

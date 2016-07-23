package com.papi.player.ui.fragment.channel;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import com.papi.player.ui.ChannelMediaActivity;
import com.papi.player.ui.common.LazyFragment;
import com.papi.player.ui.internal.OpenChannel;

/**
 * Author   Shone
 * Date     27/06/16.
 * Github   https://github.com/shonegg
 */

public abstract class ChannelBaseFragment extends LazyFragment {

    public enum STATE {
        LOADING,
        REFRESH,
        LOADMORE;
    }

    protected static final String TAG = "ChannelBaseFragment";
    private boolean isSuccess;
    protected String mChannelCode;
    protected String mChannelId;
    protected String mChannelName;
    protected int mCurrentPage;
    protected boolean mIsLoadingMore;
    protected boolean mIsRefreshing;
    protected int mLastRequestPage;
    protected OpenChannel.Template mTemplate;
    protected STATE mState;

    public ChannelBaseFragment() {
        super();
        this.mCurrentPage = 1;
        this.mLastRequestPage = 1;
        this.mChannelId = null;
        this.mChannelCode = null;
        this.mIsRefreshing = false;
        this.mIsLoadingMore = false;
        this.mTemplate = OpenChannel.Template.MEDIA;
        this.isSuccess = false;
    }

    public void getmArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.mChannelId = bundle.getString("CHANNEL_ID");
            this.mChannelCode = bundle.getString("CHANNEL_CODE");
            this.mChannelName = bundle.getString("CHANNEL_NAME");
            String template = bundle.getString("TEMPLATE");
            if (template != null) {
                this.mTemplate = OpenChannel.Template.getTemplate(template);
            }
        }

    }

    public abstract
    @LayoutRes
    int getLayoutResId();

    protected abstract void initView();

    protected abstract boolean loadData();

    protected void loadMore() {
        if (!this.mIsLoadingMore) {
            this.mIsLoadingMore = true;
            this.mState = STATE.LOADMORE;
            this.onRequestData();
        }
    }

    @Override
    public void finishCreateView(Bundle state) {
        //do nothing
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getmArguments();
        this.initView();
        this.initCommontView();
        this.setListener();
    }

    public abstract void onNetAvailable();

    protected void onRequestData() {
        if (this.mChannelId != null && (this.loadData())) {
            this.showLoading(true);
        }
    }

    private void initCommontView() {
//        this.mFSErrorView.hide();
//        this.mFSErrorView.setOnRetryClick(new OnRetryClick() {
//            public void retry(FSErrorView view) {
//                view.hide();
//                ChannelBaseFragment.this.mState = STATE.LOADING;
//                ChannelBaseFragment.this.onRequestData();
//            }
//        });
    }
    protected void reFresh() {
        if (!this.mIsRefreshing) {
            this.mIsRefreshing = true;
            this.mState = STATE.REFRESH;
            this.mCurrentPage = 1;
            this.onRequestData();
        }
    }

    protected abstract void setListener();

    protected abstract void setRefreshOrLoadMoreFlag();

    private void showLoading(boolean show) {
        if (this.mState == STATE.LOADMORE) {
//            this.showLoading(this.mLoadMoreContainer, show);
        } else if (this.mState != STATE.REFRESH) {
//            this.showLoading(this.mLoadingContainer, show);
        }
    }

}
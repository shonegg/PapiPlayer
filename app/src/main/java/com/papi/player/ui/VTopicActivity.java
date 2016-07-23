package com.papi.player.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.bean.TopicBean;
import com.papi.player.presenter.TopicTaskPresenter;
import com.papi.player.presenter.TopicTasksContract;
import com.papi.player.ui.adapter.helper.SpacesItemDecoration;
import com.papi.player.ui.adapter.list.TopicRecyclerAdapter;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;

import java.util.List;

import tv.danmaku.bili.widget.LoadingImageView;

/**
 * Author   Shone
 * Date     23/06/16.
 * Github   https://github.com/shonegg
 */
public class VTopicActivity extends AbsActivity implements TopicTasksContract.View {

    private LinearLayout mContentLayout;
    private LoadingImageView mLoadingView;
    private TopicTasksContract.Presenter mTaskPresenter;
    private RecyclerView mGridRecyclerView;
    private TopicRecyclerAdapter mAdapter;

    public static void launch(Context ctx, String topicId, String cCode, String cId) {
        Intent intent = new Intent(ctx, VTopicActivity.class);
        intent.putExtra("id", topicId);
        intent.putExtra("cid", cId);
        intent.putExtra("ccode", cCode);
        ctx.startActivity(intent);
    }

    private String mTopicId;
    private String mTopicName;
    private String mChannelCode;
    private String mChannelId;

    private void getDataFromIntent() {
        Intent intent = this.getIntent();
        if (intent != null) {
            this.mTopicId = intent.getStringExtra("id");
            this.mTopicName = intent.getStringExtra("name");
            this.mChannelCode = intent.getStringExtra("ccode");
            this.mChannelId = intent.getStringExtra("cid");
        }
        if (TextUtils.isEmpty(mTopicId)) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Constants.color_primary);
        setContentView(R.layout.activity_topic);
    }

    @Override
    protected void setUpViews() {
        /***toolbar setting***/
        mToolbar.setTitle("专题");
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

        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
        mLoadingView = (LoadingImageView) findViewById(R.id.loading);

        mGridRecyclerView = (RecyclerView) $(R.id.grid);
        mGridRecyclerView.setNestedScrollingEnabled(false);
        mGridRecyclerView.setHasFixedSize(false);

        int spanCount = 2;
//      mGridRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
//                spanCount));

        mGridRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        getDataFromIntent();

        initAdapter();

        TopicTaskPresenter.bindPresenter(this);
        showLoading();
        mTaskPresenter.startTask(mTopicId);
    }

    private void initAdapter() {
        mAdapter = new TopicRecyclerAdapter(mGridRecyclerView);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                //// TODO: 19/06/16
                TopicBean.VideoInfo videoInfo = mAdapter.getItem(position);
                String playId = videoInfo.id;
                VideoInfoActivity.start(VTopicActivity.this, new EnterMediaEntity(playId, null, null, 0,
                        mChannelId, mChannelCode, null), videoInfo.still);
            }
        });
        mGridRecyclerView.setAdapter(mAdapter);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mGridRecyclerView.addItemDecoration(decoration);
    }

    private void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.showProgress();
        mContentLayout.setVisibility(View.GONE);
    }

    private void showContent() {
        mLoadingView.hideAll();
        mContentLayout.setVisibility(View.VISIBLE);
    }

    private void updateAdapter(List<TopicBean.VideoInfo> t) {
        mAdapter.addContent(t);
    }

    @Override
    public void finishGetTopic(TopicBean bean) {
        if (bean == null) {
            return;
        }
//      mToolbar.setTitle(bean.name);
        updateAdapter(bean.videoInfoList);
        showContent();
    }

    @Override
    public void setPresenter(TopicTasksContract.Presenter presenter) {
        mTaskPresenter = presenter;
    }
}

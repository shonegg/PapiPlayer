package com.papi.player.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.papi.player.R;
import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.bean.MediaEpisodeEntity;
import com.papi.player.bean.Mp4StreamBean;
import com.papi.player.bean.ProfileBean;
import com.papi.player.dao.HistoryManager;
import com.papi.player.dao.MediaBeanDaoHelper;
import com.papi.player.engine.VideoActivity;
import com.papi.player.javabean.MediaBean;
import com.papi.player.play.PlayManager;
import com.papi.player.play.PlayUtil;
import com.papi.player.play.VideoParam;
import com.papi.player.presenter.MediaTaskPresenter;
import com.papi.player.presenter.MediaTasksContract;
import com.papi.player.ui.adapter.list.SeasonRecyclerAdapter;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;
import com.papi.player.util.DensityUtil;
import com.papi.player.util.ImageUtils;
import com.papi.player.util.SimpleImageLoader;
import com.papi.player.util.Toastor;
import com.papi.player.util.log.ILog;

import java.util.List;

import tv.danmaku.bili.widget.LoadingImageView;

/**
 * author   Shone
 * date     14/06/16.
 * github   https://github.com/shonegg
 */
public class MediaInfoActivity extends AbsActivity implements MediaTasksContract.View {

    private static String EXTRA_ITEM_ENTITY = "extra_enterEntity";
    private MediaTasksContract.Presenter mTaskPresenter;
    private ProfileBean mProfileBean;
    private MediaEpisodeEntity mMediaEpisodeEntity;
    private ImageView mHeaderIcon;
    private TextView mTitle;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;

    private FrameLayout mBackgroud;
    private ImageView mPosterIv;
    private RecyclerView mGridRecyclerView;
    private SeasonRecyclerAdapter mSeasonAdapter;
    private TextView mDescText;
    private LinearLayout mContentLayout;
    private LoadingImageView mLoadingView;
    private View mAlphaView;
    private EnterMediaEntity mEnterEntity;

//    public static void launch(Context ctx, String _id) {
//        Intent intent = new Intent(ctx, MediaInfoActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(EXTRA_ITEM_ID, _id);
//        ctx.startActivity(intent);
//    }

    public static void start(Context ctx, EnterMediaEntity enterMediaEntity) {
        Intent intent = new Intent(ctx, MediaInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_ITEM_ENTITY, enterMediaEntity);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_details);
    }


    @Override
    protected void onDestroy() {
        ImageUtils.recycleImageView(mPosterIv);
        super.onDestroy();
    }

    @Override
    protected void setUpViews() {

        /***toolbar setting***/
        mToolbar.setTitle("详情");
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


        mBackgroud = (FrameLayout) $(R.id.background);
        mPosterIv = (ImageView) $(R.id.poster);

        mHeaderIcon = (ImageView) $(R.id.cover);
        mTitle = (TextView) $(R.id.title);
        mText1 = (TextView) $(R.id.text1);
        mText2 = (TextView) $(R.id.text2);
        mText3 = (TextView) $(R.id.text3);

        mAlphaView = (View) $(R.id.alpha_view);
        mAlphaView.setAlpha(0);
        NestedScrollView mScrollView = (NestedScrollView) $(R.id.scroll_view);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0) {
                    mAlphaView.setAlpha(0);
                } else if (scrollY == DensityUtil.dip2px(MediaInfoActivity.this, 100)) {
                    mAlphaView.setAlpha(1.0f);
                } else if (0 < scrollY && scrollY < DensityUtil.dip2px(MediaInfoActivity.this, 100)) {
                    float alpha = scrollY * 1.0f / DensityUtil.dip2px(MediaInfoActivity.this, 100);
                    mAlphaView.setAlpha(alpha);
                }
            }
        });

        mGridRecyclerView = (RecyclerView) $(R.id.grid);
        mGridRecyclerView.setNestedScrollingEnabled(false);
        mGridRecyclerView.setHasFixedSize(false);

        int spanCount = 4;
        mGridRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                spanCount));

        mDescText = (TextView) $(R.id.description);
        initAdapter();

        getDataFromIntent(getIntent());

        MediaTaskPresenter.bindPresenter(this);
        showLoading();
        mTaskPresenter.startTask1(mEnterEntity.getId());
    }

    private void getDataFromIntent(Intent intent) {
        if (intent != null) {
            this.mEnterEntity = (EnterMediaEntity) intent.getSerializableExtra(EXTRA_ITEM_ENTITY);
        }
        if (mEnterEntity == null) {
            finish();
        }
    }

    public int dp2px(int dpValue) {
        return (int) (dpValue * getResources().getDisplayMetrics().density);
    }

    private void finishGetTask() {
        if (mProfileBean == null) {
            return;
        }
        mTaskPresenter.startTask2(mEnterEntity.getId());

        SimpleImageLoader.display(mProfileBean.still, mHeaderIcon);
        mTaskPresenter.startTask5(mProfileBean.poster);

        // 加载信息
        mTitle.setText(mProfileBean.name);
        mText1.setText(mProfileBean.update);
        mText3.setText(String.format(getString(R.string.info_play_score_format),
                mProfileBean.score));//播放次数
        mText2.setText(mProfileBean.category);
        mDescText.setText(mProfileBean.description);

    }

    @Override
    public void finishGetTask(ProfileBean t) {
        mProfileBean = t;
        finishGetTask();
    }


    private void initAdapter() {
        mSeasonAdapter = new SeasonRecyclerAdapter(mGridRecyclerView);
        mSeasonAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                //// TODO: 19/06/16
                MediaEpisodeEntity.Episode videoInfo = mSeasonAdapter.getVideoItem(position);

                VideoParam param = PlayUtil.episodeToVideoParam(videoInfo, mMediaEpisodeEntity
                        .getName(), mMediaEpisodeEntity.getMedia(), 0, mEnterEntity
                        .getcCode());

                PlayManager playManager = new PlayManager(new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 1000: {
                                Mp4StreamBean.PlayListInfo playInfo = (Mp4StreamBean.PlayListInfo) msg.obj;

                                HistoryManager.saveMedia(mEnterEntity, mProfileBean);

                                VideoActivity.intentTo(MediaInfoActivity.this,
                                        playInfo.urls[0], playInfo.tname);
                                break;
                            }
                            case 1001: {
                                new Toastor(MediaInfoActivity.this).showToast("点播出问题了哦...");
                                break;
                            }
                        }

                    }
                });
                try {
                    playManager.playMedia(param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mGridRecyclerView.setAdapter(mSeasonAdapter);
    }

    private void updateAdapter(final List<MediaEpisodeEntity.Episode> t) {
        mSeasonAdapter.addContent(t);
    }

    @Override
    public void finishPartsGetTask(MediaEpisodeEntity msg) {
        mMediaEpisodeEntity = msg;
        if (msg == null) {
            return;
        }
        updateAdapter(msg.getEpisodeList());
        showContent();
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

    @Override
    public void finishBlurBitmp(Bitmap bmp) {
        if (mPosterIv != null && bmp != null) {
            mPosterIv.setImageBitmap(bmp);
        }
    }

    @Override
    public void setPresenter(MediaTasksContract.Presenter presenter) {
        mTaskPresenter = presenter;
    }

}

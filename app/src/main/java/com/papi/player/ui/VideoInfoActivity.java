package com.papi.player.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.papi.player.R;
import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.bean.Mp4StreamBean;
import com.papi.player.bean.VideoEntity;
import com.papi.player.bean.VideoPlayEntity;
import com.papi.player.dao.HistoryManager;
import com.papi.player.engine.widget.media.AndroidMediaController;
import com.papi.player.engine.widget.media.IjkVideoView;
import com.papi.player.play.CompareUtils;
import com.papi.player.presenter.VideoViewContract;
import com.papi.player.presenter.VideoViewPresenter;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.util.SimpleImageLoader;
import com.papi.player.util.Toastor;
import com.papi.player.util.Utility;
import com.papi.player.view.CircleProgressView;
import com.papi.player.view.ObservableScrollView;

import java.io.Serializable;
import java.util.Random;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoInfoActivity extends AbsActivity implements ObservableScrollView.OnScrollChangeListener, VideoViewContract.View {

    private ObservableScrollView mScrollView;
    //    private ImageView mPreviewView;
    private FrameLayout mAppBarContainer;
    private FloatingActionButton mFAB;
    private CircleProgressView mCircleProgress;
    private LinearLayout mContainer;
    private AppCompatTextView mTitleText, mDescText, mTimeText;


    private boolean isPlayingFABAnimation = false;
    private int APP_BAR_HEIGHT, TOOLBAR_HEIGHT, STATUS_BAR_HEIGHT = 0, minHeight = 0;


    private VideoViewContract.Presenter mTaskPresenter;
    private EnterMediaEntity mEnterEntity;
    private VideoEntity mVideoEntity;
    private IjkVideoView mVideoView;
    private AndroidMediaController mMediaController;
    private boolean mBackPressed;
    private String mCoverUrl;
    private AppCompatImageView mCoverImageView;

    public static void start(Context ctx, EnterMediaEntity enterEntity, String cover) {
        Intent intent = new Intent(ctx, VideoInfoActivity.class);
        intent.putExtra("enterEntity", ((Serializable) enterEntity));
        intent.putExtra("cover", cover);
        ctx.startActivity(intent);
    }

    private void getDataFromIntent(Intent intent) {
        if (intent != null) {
            this.mEnterEntity = (EnterMediaEntity) intent.getSerializableExtra("enterEntity");
            this.mCoverUrl = intent.getStringExtra("cover");
        }
        if (mEnterEntity == null) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**********高度调整***************/
        APP_BAR_HEIGHT = getResources().getDimensionPixelSize(R.dimen.appbar_parallax_max_height);
        TOOLBAR_HEIGHT = getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
        if (Build.VERSION.SDK_INT >= 19) {
            STATUS_BAR_HEIGHT = Utility.getStatusBarHeight(getApplicationContext());
        }
        minHeight = APP_BAR_HEIGHT - TOOLBAR_HEIGHT - STATUS_BAR_HEIGHT;
        /*******************************/

        getDataFromIntent(this.getIntent());//解析Intent数据
        setContentView(R.layout.activity_video_view);

        VideoViewPresenter.bindPresenter(this);
        startGetTask();
    }

    @Override
    protected void setUpViews() {

        mToolbar.setTitle("");
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

        mAppBarContainer = $(R.id.appbar_container);
        mCoverImageView = (AppCompatImageView) $(R.id.cover);

        mScrollView = $(R.id.scroll_view);
        mFAB = $(R.id.fab);
        mCircleProgress = $(R.id.circle_progress);
        mContainer = $(R.id.container_view);
        mTitleText = $(R.id.tv_title);
        mDescText = $(R.id.tv_description);
        mTimeText = (AppCompatTextView) $(R.id.tv_time);
        mVideoView = (IjkVideoView) $(R.id.video_preview);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mMediaController = new AndroidMediaController(this, false);
        mVideoView.setMediaController(mMediaController);

        mFAB.setTranslationY(-getResources().getDimension(R.dimen.floating_action_button_size_half));
        mFAB.setEnabled(false);
        mScrollView.addOnScrollChangeListener(this);


        showCover();
    }

    private void showCover(){
        mVideoView.setVisibility(View.GONE);
        mCoverImageView.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(mCoverUrl)){
            SimpleImageLoader.display(mCoverUrl, mCoverImageView);
        }
    }

    private void showVideo (final String url){
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mVideoView.start();//开始播放
                initScreenVideo();
            }
        });
        mFAB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mVideoView.setVisibility(View.VISIBLE);
                mCoverImageView.setVisibility(View.GONE);
                mVideoView.setVideoPath(url);//设置播放路径
                mFAB.setVisibility(View.INVISIBLE);

                HistoryManager.saveVideo (mEnterEntity, mVideoEntity);// 存储到记录
            }
        });
        mFAB.setEnabled(true);
    }

    private static int SIZE_DEFAULT = 0;
    private static int SIZE_4_3 = 1;
    private static int SIZE_16_9 = 2;
    private int currentSize = SIZE_16_9;


    /*初始化尺寸为match*/
    private void initScreenVideo(){
        int height = getResources().getDimensionPixelSize(R.dimen.video_play_max_height);
//      int height = dip_height + Utility.getStatusBarHeight(this);

        int width = Utility.getScreenWidth(this);

        mVideoView.adjustRenderView(width, height);
        mVideoView.adjustVideoSize(width, height);
    }

    /*调整屏幕播放比例*/
    private void adjustScreenRate(int rate) {
        int screenWidth = Utility.getScreenWidth(this);
        int width = 0;
        int height = 0;
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            if (rate == SIZE_DEFAULT) {
                width = mVideoView.getVideoWidth();
                height = mVideoView.getVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = screenWidth / 3 * 4;
                height = screenWidth;
            } else if (rate == SIZE_16_9) {
                width = screenWidth / 9 * 16;
                height = screenWidth;
            }
        } else {
            if (rate == SIZE_DEFAULT) {
                width = mVideoView.getVideoWidth();
                height = mVideoView.getVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = screenWidth;
                height = screenWidth * 3 / 4;
            } else if (rate == SIZE_16_9) {
                width = screenWidth;
                height = screenWidth * 9 / 16;
            }
        }
        if (width > 0 && height > 0) {
            mVideoView.adjustRenderView(width, height);
        }
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    private void startGetTask() {
        mCircleProgress.setVisibility(View.VISIBLE);
        mCircleProgress.spin();
        mContainer.setVisibility(View.GONE);

        mTaskPresenter.start1(mEnterEntity.getId());
    }

    private void finishGetTask() {
        mCircleProgress.setVisibility(View.GONE);
        mCircleProgress.stopSpinning();
        mContainer.setVisibility(View.VISIBLE);

        // 加载信息
        mTitleText.setText(mVideoEntity.getCategory());
        mDescText.setText(mVideoEntity.getName());
        mTimeText.setText(mVideoEntity.getRelease());

        mTaskPresenter.start2(mEnterEntity.getId());
    }

    @Override
    public void onScrollChanged(ObservableScrollView view, int x, int y, int oldx, int oldy) {
    }

    @Override
    public void setPresenter(VideoViewContract.Presenter presenter) {
        mTaskPresenter = presenter;
    }

    @Override
    public void onSuccess1(VideoEntity entity) {
        this.mVideoEntity = entity;
        if (this.mVideoEntity == null) {
            Toast.makeText(VideoInfoActivity.this, "获取信息失败...", Toast.LENGTH_SHORT).show();
            return;
        }
        finishGetTask();
    }

    @Override
    public void onFail1() {
    }


    public static String subString(String sSource, int iLength) {
        if (TextUtils.isEmpty(sSource)){
            return "233512";
        }
        if (iLength >= sSource.length()) {
            return sSource;
        }
        return sSource.substring(0, sSource.length() - iLength);
    }
    @Override
    public void onSuccess2(VideoPlayEntity entity) {
        if (entity == null || !"200".equals(entity.retcode)) {
            return;
        }
        Random ra =new Random();
        int random = ra.nextInt(100)+1;

        mToolbar.setTitle("av" + random + subString (entity.video, 2));
        mTaskPresenter.start3(entity.mp4BeanList.get(0).http);
    }

    @Override
    public void onFail2() {
    }

    @Override
    public void onSuccess3(Mp4StreamBean bean, String requestUrl) {
        if (bean == null || bean.playListInfoList == null) {
            return;
        }
        String url = bean.playListInfoList.get(0).urls[0];
        if (CompareUtils.compare1(url, requestUrl)){
            showVideo (url);//显示视频
        } else {
            new Toastor(VideoInfoActivity.this).showToast("哦哦,点播出问题了!");
        }
    }

    @Override
    public void onFail3() {

    }
}

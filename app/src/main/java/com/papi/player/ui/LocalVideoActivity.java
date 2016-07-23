package com.papi.player.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.engine.VideoActivity;
import com.papi.player.localvideo.AbstructProvider;
import com.papi.player.localvideo.LoadedImage;
import com.papi.player.localvideo.Video;
import com.papi.player.localvideo.VideoProvider;
import com.papi.player.ui.adapter.list.LocalVideoAdapter;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;

import java.util.List;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class LocalVideoActivity extends AbsActivity {


    private List<Video> listVideos;
    private int videoSize;
    private RecyclerView mRecyclerView;
    private LocalVideoAdapter mAdapter;


    public static void launch(AppCompatActivity activity) {
        Intent intent = new Intent(activity, LocalVideoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Constants.color_primary);
        setContentView(R.layout.activity_local_video);
    }

    @Override
    protected void setUpViews() {
        mToolbar.setTitle("本地干货");
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

        mRecyclerView = (RecyclerView) $(R.id.grid);
        mRecyclerView.setHasFixedSize(true);
//      mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AbstructProvider provider = new VideoProvider(getApplicationContext());
        listVideos = provider.getList();
        videoSize = listVideos.size();

        mAdapter = new LocalVideoAdapter(mRecyclerView, listVideos);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Video video = mAdapter.getItem(position);
                VideoActivity.intentTo(LocalVideoActivity.this, video.getPath(), video.getTitle());
            }
        });
        loadImages();
    }

    /**
     * Load images.
     */
    private void loadImages() {
        @SuppressWarnings("deprecation")
        final Object data = getLastNonConfigurationInstance();
        if (data == null) {
            new LoadImagesFromSDCard().execute();
        } else {
            final LoadedImage[] photos = (LoadedImage[]) data;
            if (photos.length == 0) {
                new LoadImagesFromSDCard().execute();
            }
            for (LoadedImage photo : photos) {
                addImage(photo);
            }
        }
    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath
     * @param width
     * @param height
     * @param kind
     * @return
     */
    private Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    class LoadImagesFromSDCard extends AsyncTask<Object, LoadedImage, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            Bitmap bitmap = null;
            for (int i = 0; i < videoSize; i++) {
                bitmap = getVideoThumbnail(listVideos.get(i).getPath(), 120, 120, MediaStore.Video.Thumbnails.MINI_KIND);
                if (bitmap != null) {
                    publishProgress(new LoadedImage(bitmap));
                }
            }
            return null;
        }

        @Override
        public void onProgressUpdate(LoadedImage... value) {
            addImage(value);
        }
    }

    private void addImage(LoadedImage... value) {
        for (LoadedImage image : value) {
//            mJieVideoListViewAdapter.addPhoto(image);
//            mJieVideoListViewAdapter.notifyDataSetChanged();
            mAdapter.addPhoto(image);
        }
        mAdapter.notifyDataSetChanged();
    }

}

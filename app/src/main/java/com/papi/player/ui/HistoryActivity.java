package com.papi.player.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.bean.BaseEntity2;
import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.dao.MediaBeanDaoHelper;
import com.papi.player.dao.MultMediaBean;
import com.papi.player.dao.VideoBeanDaoHelper;
import com.papi.player.engine.VideoActivity;
import com.papi.player.javabean.MediaBean;
import com.papi.player.javabean.VideoBean;
import com.papi.player.localvideo.AbstructProvider;
import com.papi.player.localvideo.LoadedImage;
import com.papi.player.localvideo.Video;
import com.papi.player.localvideo.VideoProvider;
import com.papi.player.ui.adapter.list.HistoryAdapter;
import com.papi.player.ui.adapter.list.LocalVideoAdapter;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.bili.widget.LoadingImageView;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public class HistoryActivity extends AbsActivity {


    private List<Video> listVideos;
    private int videoSize;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private LoadingImageView mLoadingImageView;


    public static void launch(AppCompatActivity activity) {
        Intent intent = new Intent(activity, HistoryActivity.class);
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
        mToolbar.setTitle("历史记录");
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

        mLoadingImageView = (LoadingImageView) $(R.id.loading);
        mRecyclerView = (RecyclerView) $(R.id.grid);
        mRecyclerView.setHasFixedSize(true);
//      mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AbstructProvider provider = new VideoProvider(getApplicationContext());
        listVideos = provider.getList();
        videoSize = listVideos.size();

        mAdapter = new HistoryAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                MultMediaBean video = mAdapter.getItem(position);
                EnterMediaEntity entity = new EnterMediaEntity();
                entity.setName(video.getName());
                entity.setId(video.getEnter_id());
                entity.setcId(video.getCId());
                entity.setcCode(video.getCCode());

                if (video.getType() == 1){
                    VideoInfoActivity.start(HistoryActivity.this, entity, video.getCover_url());
                } else {
                    MediaInfoActivity.start(HistoryActivity.this, entity);
                }
            }
        });

        new InnerTask().execute();
    }



   private class InnerTask extends AsyncTask<Void, Void, List<MultMediaBean>> {

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           mRecyclerView.setVisibility(View.GONE);
           mLoadingImageView.showProgress();
       }

       @Override
        protected List<MultMediaBean> doInBackground(Void... params) {
            List<MediaBean> mediaBeanList = MediaBeanDaoHelper.getInstance().getAllData();
            List<VideoBean> videoBeanList = VideoBeanDaoHelper.getInstance().getAllData();
            List<MultMediaBean> all = new ArrayList<>();
            if (mediaBeanList != null){
                for (int i = 0; i < mediaBeanList.size(); i++){
                    MediaBean bean = mediaBeanList.get(i);
                    MultMediaBean object = new MultMediaBean();
                    object.setName(bean.getName());
                    object.setTime(bean.getTime());
                    object.setCover_url(bean.getCover_url());
                    object.setCCode(bean.getCCode());
                    object.setCId(bean.getCId());
                    object.setType(2);
                    object.setEnter_id(bean.getEnter_id());
                    all.add(object);
                }
            }
            if (videoBeanList != null){
                for (int i = 0; i < videoBeanList.size(); i++){
                    VideoBean bean = videoBeanList.get(i);
                    MultMediaBean object = new MultMediaBean();
                    object.setName(bean.getName());
                    object.setTime(bean.getTime());
                    object.setCover_url(bean.getCover_url());
                    object.setCCode(bean.getCCode());
                    object.setCId(bean.getCId());
                    object.setType(1);
                    object.setEnter_id(bean.getEnter_id());
                    all.add(object);
                }
            }
            return all;
        }

        @Override
        protected void onPostExecute(List<MultMediaBean> multMediaBeen) {
            mLoadingImageView.hideAll();
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.update(multMediaBeen);
        }
    }

}

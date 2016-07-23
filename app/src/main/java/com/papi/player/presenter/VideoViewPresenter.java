package com.papi.player.presenter;

import android.support.annotation.NonNull;

import com.papi.player.api.BuildOpt;
import com.papi.player.api.Mp4StreamApi;
import com.papi.player.api.VideoInfoApi;
import com.papi.player.api.VideoPlayAPi;
import com.papi.player.bean.Mp4StreamBean;
import com.papi.player.bean.VideoEntity;
import com.papi.player.bean.VideoPlayEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.AsyncTask;

/**
 * Author   Shone
 * Date     01/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoViewPresenter implements VideoViewContract.Presenter{

    private VideoViewContract.View mTasksView;

    public  VideoViewPresenter (VideoViewContract.View view){
        mTasksView = view;
        mTasksView.setPresenter(this);

    }

    public static void bindPresenter(@NonNull VideoViewContract.View tasksView) {
        new VideoViewPresenter(tasksView);
    }

    @Override
    public void start() {
    }

    @Override
    public void start1(String taskId) {
        new InnerTask1().execute(taskId);
    }

    @Override
    public void start2(String taskId) {
        new InnerTask2().execute(taskId);
    }

    @Override
    public void start3(String url) {
        new InnerTask3().execute(url);
    }


    class InnerTask1 extends AsyncTask<String, Void, BasicMessage<VideoEntity>>{

        @Override
        protected BasicMessage<VideoEntity> doInBackground(String... params) {
            String id = params[0];
            return VideoInfoApi.get(id);
        }

        @Override
        protected void onPostExecute(BasicMessage<VideoEntity> msg) {
            if (mTasksView != null){
                if (msg.getCode() == BasicMessage.CODE_ERROR){
                    mTasksView.onFail1();
                    return;
                }
                mTasksView.onSuccess1(msg.getObject());
            }
        }
    }

    class InnerTask2 extends AsyncTask<String, Void, BasicMessage<VideoPlayEntity>>{

        @Override
        protected BasicMessage<VideoPlayEntity> doInBackground(String... params) {
            String id = params[0];
            return VideoPlayAPi.getPlayInfo(id);
        }

        @Override
        protected void onPostExecute(BasicMessage<VideoPlayEntity> msg) {
            if (mTasksView != null){
                if (msg.getCode() == BasicMessage.CODE_ERROR){
                    mTasksView.onFail2();
                    return;
                }
                mTasksView.onSuccess2(msg.getObject());
            }
        }
    }


    class InnerTask3 extends AsyncTask<String, Void, BasicMessage<Mp4StreamBean>>{
        private  String requestUrl;
        @Override
        protected BasicMessage<Mp4StreamBean> doInBackground(String... params) {
            requestUrl = params[0];
            return Mp4StreamApi.getMp4Info(BuildOpt.build(requestUrl));
        }

        @Override
        protected void onPostExecute(BasicMessage<Mp4StreamBean> msg) {
            if (mTasksView != null){
                if (msg.getCode() == BasicMessage.CODE_ERROR){
                    mTasksView.onFail3();
                    return;
                }
                mTasksView.onSuccess3(msg.getObject(), requestUrl);
            }
        }
    }
}

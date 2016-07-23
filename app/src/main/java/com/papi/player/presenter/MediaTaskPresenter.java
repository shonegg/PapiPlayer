package com.papi.player.presenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.papi.player.api.EpisodeApi;
import com.papi.player.api.ProfileApi;
import com.papi.player.bean.MediaEpisodeEntity;
import com.papi.player.bean.ProfileBean;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.AsyncTask;
import com.papi.player.util.SimpleImageLoader;

import net.qiujuer.genius.blur.StackBlur;


/**
 * Author   Shone
 * Date     15/06/16.
 * Github   https://github.com/shonegg
 */
public class MediaTaskPresenter implements MediaTasksContract.Presenter {

    private final MediaTasksContract.View mTasksView;

    public static void bindPresenter(@NonNull MediaTasksContract.View tasksView) {
        new MediaTaskPresenter(tasksView);
    }

    public MediaTaskPresenter(@NonNull MediaTasksContract.View tasksView) {
        mTasksView = tasksView;
        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void startTask1(String _id) {
        new ViewInfoGetTask().execute(_id);
    }

    @Override
    public void startTask2(String _id) {
        new PartsGetTask().execute(_id);
    }


    @Override
    public void startTask5(String url) {
        new BlurBitmapTask().execute(url);
    }

    public class ViewInfoGetTask extends AsyncTask<String, Void, BasicMessage<ProfileBean>> {

        @Override
        protected BasicMessage<ProfileBean> doInBackground(String... params) {
            String id = params[0];//
            return ProfileApi.getProfileInfo(id);
        }

        @Override
        protected void onPostExecute(BasicMessage<ProfileBean> msg) {
            if (msg != null && msg.getCode() == BasicMessage.CODE_SUCCEED) {
                mTasksView.finishGetTask(msg.getObject());
            }
        }

    }

    public class PartsGetTask extends AsyncTask<String, Void, BasicMessage<MediaEpisodeEntity>> {

        @Override
        protected BasicMessage<MediaEpisodeEntity> doInBackground(String... params) {
            String _id = params[0];
            return EpisodeApi.getEpisodeInfo(_id);
        }

        @Override
        protected void onPostExecute(BasicMessage<MediaEpisodeEntity> msg) {
            if (msg.getCode() == BasicMessage.CODE_SUCCEED) {
                mTasksView.finishPartsGetTask(msg.getObject());
            }

        }
    }

    public class BlurBitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bmp = SimpleImageLoader.loadImageSync(url);
            return StackBlur.blurNatively(bmp, 20, true);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mTasksView.finishBlurBitmp(bitmap);
        }
    }

}
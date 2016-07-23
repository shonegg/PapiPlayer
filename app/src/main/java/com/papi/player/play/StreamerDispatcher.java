package com.papi.player.play;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.papi.player.AppContext;
import com.papi.player.api.MediaPlayApi;
import com.papi.player.api.Mp4StreamApi;
import com.papi.player.bean.MediaPlayEntity;
import com.papi.player.bean.Mp4StreamBean;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.AsyncTask;
import com.papi.player.util.Toastor;
import com.papi.player.util.log.ILog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class StreamerDispatcher {

    protected Play mPlayInfo;
    private PlayParam mParam;
    protected String mSelected;
    protected List<Play> mSupportEntity;
    private Handler mHandler;

    public synchronized void requestMedia(PlayParam param, Handler handler) throws Exception {
        this.mHandler = handler;
        if (param == null) {
            throw new Exception("param must not be null !");
        }
        this.mParam = param;
        new InnerTask().execute(param.subjectId);
    }


    private class InnerTask extends AsyncTask<String, Void, BasicMessage<MediaPlayEntity>> {

        @Override
        protected BasicMessage<MediaPlayEntity> doInBackground(String... params) {
            return MediaPlayApi.retrofit(params[0]);
        }

        @Override
        protected void onPostExecute(BasicMessage<MediaPlayEntity> entity) {
            try {
                first:
                {
                    if (entity == null || entity.getObject() == null || !entity.getObject().isOK()) {
                        break first;
                    }
                    MediaPlayEntity bean = entity.getObject();
                    if (bean == null) {
                        break first;
                    }
                    if (!TextUtils.equals(mParam.subjectId, bean.getEpisode())) {
                        break first;
                    }

                    mSelected = bean.getSelected();
                    mSupportEntity = bean.getMp4List();
                    int pos = selectPlayInfo();
//                    mPlayInfo.getInfohash();
//                    mParam.mediaId;
//                    mParam.mediaName;
//                    mPlayInfo.getFilesize();
//                    mPlayInfo.getName();

                    startPlay();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class InnerTask1 extends AsyncTask<String, Void, BasicMessage<Mp4StreamBean>> {

        private String requestUrl;

        @Override
        protected BasicMessage<Mp4StreamBean> doInBackground(String... params) {
            requestUrl = params[0];
            return Mp4StreamApi.getMp4Info(params[0]);
        }

        @Override
        protected void onPostExecute(BasicMessage<Mp4StreamBean> bean) {

            Message msg = mHandler.obtainMessage();
            if (bean == null) {
                msg.what = 1001;
            } else {
                msg.what = 1000;
                Mp4StreamBean mp4StreamBean = bean.getObject();
                if (mp4StreamBean != null) {
                    if (mp4StreamBean.playListInfoList.size() > 0) {
                        Mp4StreamBean.PlayListInfo playInfo = mp4StreamBean.playListInfoList.get(0);
                        String videoUrl  = playInfo.urls[0];
                        boolean ff = CompareUtils.compare(videoUrl, requestUrl);
                        if (ff){
                            msg.obj = mp4StreamBean.playListInfoList.get(0);
                        } else {
                            msg.what = 1001;
                        }
                    }
                } else {
                    msg.what = 1001;
                }
            }
            mHandler.sendMessage(msg);
        }
    }

    private void startPlay() {
        if (mPlayInfo == null) {
            return;
        }
        String http = mPlayInfo.getHttp();
        if (TextUtils.isEmpty(http)) {
            return;
        }
        new InnerTask1().execute(http);
    }

    protected int selectPlayInfo() {
        int v0;
        if (this.mSupportEntity == null || this.mSupportEntity.size() < 1) {
            this.mPlayInfo = null;
            v0 = -1;
        } else if (this.mSelected == null) {
            v0 = this.selectResolution(false);
        } else {
            v0 = this.selectResolution(true);
        }

        return v0;
    }

    protected List<Resolution> mResolutions;

    private int selectResolution(boolean isCodeExist) {
        ArrayList<Resolution> mList = new ArrayList();
        int flag = 0;
        int position = 0;
        int i = 0;
        Iterator<Play> iterator = this.mSupportEntity.iterator();
        while (iterator.hasNext()) {
            Play whatPlay = iterator.next();
            Resolution resolution = new Resolution();
            resolution.code = whatPlay.getCode();
            resolution.name = whatPlay.getName();
            if ((isCodeExist) && (TextUtils.equals(this.mSelected, whatPlay.getCode()))) {
                this.mPlayInfo = whatPlay;
                flag = 1;
                position = i;
            }

            mList.add(resolution);
            ++i;
        }

        this.mResolutions = mList;
        if (flag == 0) {
            this.mPlayInfo = this.mSupportEntity.get(mSupportEntity.size() - 1);
            this.mSelected = this.mPlayInfo.getCode();
        }


        return position;
    }
}

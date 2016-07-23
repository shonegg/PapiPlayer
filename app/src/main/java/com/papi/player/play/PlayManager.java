package com.papi.player.play;

import android.os.Handler;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class PlayManager {


    private String mPlayUrl;
    private VideoParam mPlayData;

    private StreamerDispatcher dispatcher;
    private Handler mHandler;

    public PlayManager(Handler handler){
        mHandler = handler;
        dispatcher = new StreamerDispatcher();
    }

    public void playMedia(VideoParam param) throws Exception {
        if(param == null) {
            throw new Exception("param is illegal");
        }
        if(param.isLegal()) {
            this.mPlayUrl = null;
            this.mPlayData = param;
//          this.addHistoryForMedia(param);
            dispatcher.requestMedia(new PlayParam(param), mHandler);
        }
    }
}

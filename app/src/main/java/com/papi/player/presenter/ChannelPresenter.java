package com.papi.player.presenter;

import android.support.annotation.NonNull;
import com.papi.player.api.ChannelAllApi;
import com.papi.player.bean.ChannelsEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.AsyncTask;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelPresenter implements ChannelTaskContract.Presenter {

    private ChannelTaskContract.View mTasksView;

    public static void bindPresenter(ChannelTaskContract.View tasksView) {
        new ChannelPresenter(tasksView);
    }

    public ChannelPresenter(@NonNull ChannelTaskContract.View tasksView) {
        mTasksView = tasksView;
        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mTasksView != null){
            mTasksView.startLoading();
        }
        new InnerTask().execute();
    }

    private class InnerTask extends AsyncTask<Void, Void, BasicMessage<ChannelsEntity>> {


        @Override
        protected BasicMessage<ChannelsEntity> doInBackground(Void... params) {
            return ChannelAllApi.getChannelsEntity();
        }

        @Override
        protected void onPostExecute(BasicMessage<ChannelsEntity> msg) {
            super.onPostExecute(msg);
            if (mTasksView != null) {
                if (msg.getCode() == BasicMessage.CODE_SUCCEED) {
                    mTasksView.onSuccess(msg.getObject().channelBeanList);
                } else {
                    mTasksView.onFail();
                }
            }
        }
    }
}

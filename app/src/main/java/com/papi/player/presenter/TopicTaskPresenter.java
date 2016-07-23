package com.papi.player.presenter;

import android.support.annotation.NonNull;

import com.papi.player.api.TopicApi;
import com.papi.player.bean.TopicBean;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.AsyncTask;

/**
 * Author   Shone
 * Date     23/06/16.
 * Github   https://github.com/shonegg
 */
public class TopicTaskPresenter implements TopicTasksContract.Presenter {

    private TopicTasksContract.View mTasksView;

    public static void bindPresenter(TopicTasksContract.View tasksView) {
        new TopicTaskPresenter(tasksView);
    }

    public TopicTaskPresenter(@NonNull TopicTasksContract.View tasksView) {
        mTasksView = tasksView;
        mTasksView.setPresenter(this);
    }

    @Override
    public void startTask(String id) {
        new TopicGetTask().execute(id);
    }



    @Override
    public void start() {
    }


    class TopicGetTask extends AsyncTask<String, Void, BasicMessage<TopicBean>> {

        @Override
        protected BasicMessage<TopicBean> doInBackground(String... params) {
            String id = params[0];//
            return TopicApi.getTopicInfo(id);
        }

        @Override
        protected void onPostExecute(BasicMessage<TopicBean> msg) {
            if (msg != null && msg.getCode() == BasicMessage.CODE_SUCCEED) {
                mTasksView.finishGetTopic(msg.getObject());
            }
        }
    }
}

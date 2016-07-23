package com.papi.player.presenter;

import com.papi.player.bean.Mp4StreamBean;
import com.papi.player.bean.TopicBean;
import com.papi.player.bean.VideoPlayEntity;
import com.papi.player.presenter.base.BasePresenter;
import com.papi.player.presenter.base.BaseView;

/**
 * Author   Shone
 * Date     23/06/16.
 * Github   https://github.com/shonegg
 */
public interface TopicTasksContract {

    interface View extends BaseView<Presenter> {
        void finishGetTopic(TopicBean bean);

//        void finishGetDetailTask(VideoPlayEntity object);
//
//        void finishGetStream (Mp4StreamBean object);
    }

    interface Presenter extends BasePresenter {
        void startTask(String id);

//        void startTask1(String playId);
//
//        void startTask2(String httpUrl);
    }
}

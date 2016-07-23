package com.papi.player.presenter;

import com.papi.player.bean.Mp4StreamBean;
import com.papi.player.bean.VideoEntity;
import com.papi.player.bean.VideoPlayEntity;
import com.papi.player.presenter.base.BasePresenter;
import com.papi.player.presenter.base.BaseView;

/**
 * Author   Shone
 * Date     01/07/16.
 * Github   https://github.com/shonegg
 */
public interface VideoViewContract {

    interface View extends BaseView<Presenter> {
        void onSuccess1(VideoEntity entity);
        void onFail1();
        void onSuccess2(VideoPlayEntity entity);
        void onFail2();
        void onSuccess3(Mp4StreamBean bean, String requestUrl);
        void onFail3();
    }

    interface Presenter extends BasePresenter {
        void start1(String taskId);
        void start2(String taskId);
        void start3(String url);

    }
}

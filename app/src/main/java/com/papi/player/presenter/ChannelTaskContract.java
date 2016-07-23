package com.papi.player.presenter;

import com.papi.player.bean.ChannelsEntity;
import com.papi.player.presenter.base.BasePresenter;
import com.papi.player.presenter.base.BaseView;

import java.util.List;

import com.papi.player.bean.ChannelsEntity.ChannelBean;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public interface ChannelTaskContract {

    interface View extends BaseView<Presenter> {
        void startLoading();
        void onSuccess(List<ChannelBean> bean);
        void onFail();
    }

    interface Presenter extends BasePresenter {
    }
}

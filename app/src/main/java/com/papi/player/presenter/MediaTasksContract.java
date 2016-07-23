package com.papi.player.presenter;

import android.graphics.Bitmap;
import com.papi.player.bean.MediaEpisodeEntity;
import com.papi.player.bean.ProfileBean;
import com.papi.player.presenter.base.BasePresenter;
import com.papi.player.presenter.base.BaseView;

/**
 * author   Shone
 * date     15/06/16.
 * github   https://github.com/shonegg
 */
public interface MediaTasksContract {

    interface View extends BaseView<Presenter> {
        void finishGetTask(ProfileBean t);
        void finishPartsGetTask(MediaEpisodeEntity t);
        void finishBlurBitmp(Bitmap bmp);
    }

    interface Presenter extends BasePresenter {

        void startTask1(String id);
        void startTask2(String id);

        void startTask5(String url);
    }
}




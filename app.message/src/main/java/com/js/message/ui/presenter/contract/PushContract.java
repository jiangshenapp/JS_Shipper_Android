package com.js.message.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.message.model.bean.PushBean;

import java.util.List;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/07
 * desc   :
 * version: 3.0.0
 */
public interface PushContract {

    interface View extends IBaseView {
        void finishRefreshAndLoadMore();
        void onPushMessage(List<PushBean> mPushBeans);
        void onReadPushLog(boolean isOk);
        void onReadAllPushLog(boolean isOk);
    }

    interface Presenter extends IPresenter<View> {
        void getPushMessage(int pushSide);
        void readPushLog(long id, int pushSide);
        void readAllPushLog(int pushSide);
    }
}

package com.js.driver.ui.message.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.driver.model.bean.MessageBean;
import com.js.driver.model.response.ListResponse;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   :
 * version: 3.0.0
 */
public interface MessageContract {

    interface View extends IBaseView {
        void finishRefreshAndLoadMore();
        void onMessage(ListResponse<MessageBean> mMessageBeans);
        void onReadMessage(boolean isOk);
        void onReadAllMessage(boolean isOk);
    }

    interface Presenter extends IPresenter<View> {
        void getMessage(int type, int current, int size);
        void readMessage(long id, int pushSide);
        void readAllMessage(int pushSide);
    }
}

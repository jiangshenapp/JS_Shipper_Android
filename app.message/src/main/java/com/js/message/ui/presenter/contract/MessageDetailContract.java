package com.js.message.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.message.model.bean.MessageBean;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   :
 * version: 3.0.0
 */
public interface MessageDetailContract {

    interface View extends IBaseView {
        void onMessageDetail(MessageBean messageBean);
    }

    interface Presenter extends IPresenter<View> {
        void getMessageDetail(long id);
    }
}

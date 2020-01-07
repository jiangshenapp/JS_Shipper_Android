package com.js.shipper.ui.message.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.model.response.ListResponse;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/07
 * desc   :
 * version: 3.0.0
 */
public interface PushContract {

    interface View extends IBaseView {
        void onMessage(ListResponse<MessageBean> mMessageBeans);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View> {
        void getMessage(int type, int current, int size);
    }
}

package com.js.shipper.ui.message.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.bean.MessageBean;
import com.js.shipper.model.request.CollectLine;
import com.js.shipper.ui.park.presenter.contract.CarSourceDetailContract;

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

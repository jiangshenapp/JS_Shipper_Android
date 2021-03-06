package com.js.shipper.ui.wallet.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.PayInfo;
import com.js.shipper.model.bean.PayRouter;

import java.util.List;

/**
 * Created by huyg on 2019/4/24.
 */
public interface RechargeContract {

    interface View extends IBaseView {
        void onPayOrder(PayInfo payInfo);

        void onPayRouter(List<PayRouter> payRouters);
    }

    interface Presenter extends IPresenter<View> {
        void payOrder(int tradeType, int channelType, double money, int routeId,String orderNo);

        void getPayRouter();
    }
}

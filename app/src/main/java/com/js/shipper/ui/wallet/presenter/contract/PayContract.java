package com.js.shipper.ui.wallet.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.PayInfo;
import com.js.shipper.model.bean.PayRouter;

import java.util.List;

/**
 * Created by huyg on 2019-06-13.
 */
public interface PayContract {

    interface View extends IBaseView{
        void onPayOrder(PayInfo payInfo);

        void onPayRouter(List<PayRouter> payRouters);

        void onPayAccount(Boolean isOk);
    }

    interface Presenter extends IPresenter<View>{

        void payAccount(String orderNo);

        void payOrder(int tradeType, int channelType, double money, int routeId,String orderNo);

        void getPayRouter();
    }
}

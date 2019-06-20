package com.js.shipper.ui.order.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.OrderBean;

/**
 * Created by huyg on 2019/4/29.
 */
public interface OrderDetailContract {

    interface View extends IBaseView {
        void onOrderDetail(OrderBean orderBean);

        void onCancelOrder(boolean isOk);


        void finishRefresh();

        void onConfirmOrder(boolean isOk);

    }

    interface Presenter extends IPresenter<View> {
        void getOrderDetail(long id);

        void cancelOrder(long id);


        void confirmOrder(long id);

    }
}

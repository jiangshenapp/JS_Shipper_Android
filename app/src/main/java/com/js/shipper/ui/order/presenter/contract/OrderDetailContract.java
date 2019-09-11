package com.js.shipper.ui.order.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.request.OrderComment;

/**
 * Created by huyg on 2019/4/29.
 */
public interface OrderDetailContract {

    interface View extends IBaseView {
        void onOrderDetail(OrderBean orderBean);

        void onCancelOrder(boolean isOk);

        void finishRefresh();

        void onConfirmOrder(boolean isOk);

        void onReceiptOrder(boolean isOk);

        void onCommentOrder(boolean isOk);
    }

    interface Presenter extends IPresenter<View> {
        void getOrderDetail(long id);

        void cancelOrder(long id);

        void confirmOrder(long id);

        void receiptOrder(long id);

        void commentOrder(OrderComment orderComment, long id);
    }
}

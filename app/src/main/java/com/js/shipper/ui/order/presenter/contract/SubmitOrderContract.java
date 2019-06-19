package com.js.shipper.ui.order.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.request.AddOrder;

/**
 * Created by huyg on 2019-06-18.
 */
public interface SubmitOrderContract {

    interface View extends IBaseView {
        void onSubmitOrder(Boolean isOk);
    }

    interface Presenter extends IPresenter<View> {
        void submitOrder(AddOrder addOrder);
    }
}

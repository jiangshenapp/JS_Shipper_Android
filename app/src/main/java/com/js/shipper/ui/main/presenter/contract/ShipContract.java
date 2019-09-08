package com.js.shipper.ui.main.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.request.AddStepOne;

/**
 * Created by huyg on 2019/4/30.
 */
public interface ShipContract {

    interface View extends IBaseView {
        void onStepOne(long orderId);
    }

    interface Presenter extends IPresenter<View> {
        void addStepOne(AddStepOne addStepOne);
    }

}

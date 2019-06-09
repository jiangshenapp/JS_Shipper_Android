package com.js.shipper.ui.main.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.request.AddStepOne;
import com.js.shipper.model.request.AddStepTwo;

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

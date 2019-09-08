package com.js.shipper.ui.order.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.request.AddStepTwo;

/**
 * Created by huyg on 2019/5/6.
 */
public interface OrderSubmitContract {


    interface View extends IBaseView{
        void onSubmit(boolean data);
    }

    interface Presenter extends IPresenter<View>{
        void submit(AddStepTwo addStepTwo);
    }
}

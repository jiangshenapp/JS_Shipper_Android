package com.js.shipper.ui.order.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.FeeBean;
import com.js.shipper.model.request.AddStepTwo;

import retrofit2.http.Query;

/**
 * Created by huyg on 2019/5/6.
 */
public interface OrderSubmitContract {


    interface View extends IBaseView{
        void onSubmit(boolean data);
        void onOrderFee(FeeBean feeBean);
    }

    interface Presenter extends IPresenter<View>{
        void submit(AddStepTwo addStepTwo);
        void getOrderFee(String startAddressCode,
                         String arriveAddressCode,
                         Number goodsWeight,
                         Number goodsVolume);
    }
}

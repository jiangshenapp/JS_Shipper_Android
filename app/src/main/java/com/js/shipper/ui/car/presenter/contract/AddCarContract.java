package com.js.shipper.ui.car.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.CarBean;

import java.util.List;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/12
 * desc   :
 * version: 3.0.0
 */
public interface AddCarContract {

    interface View extends IBaseView {
        void onQueryCarList(List<CarBean> carBeans);

        void onAddCar(boolean isOk);
    }

    interface Presenter extends IPresenter<View> {
        void queryCarList(String input);

        void addCar(long carId, String remark, long type);
    }
}

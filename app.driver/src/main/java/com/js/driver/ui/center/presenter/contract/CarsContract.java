package com.js.driver.ui.center.presenter.contract;

import com.js.driver.model.bean.CarBean;
import com.js.driver.model.response.ListResponse;
import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019/4/29.
 */
public interface CarsContract {

    interface View extends IBaseView {
        void onCarList(ListResponse<CarBean> response);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View> {
        void getCarList();
    }
}

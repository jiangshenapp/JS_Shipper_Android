package com.js.driver.ui.center.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.driver.model.bean.SelectCity;

import java.util.List;

/**
 * Created by huyg on 2019-06-06.
 */
public interface SelectCityContract {

    interface View extends IBaseView {
        void onCityList(List<SelectCity> selectCities);
    }

    interface Presenter extends IPresenter<View> {
        void getCityList();
    }
}

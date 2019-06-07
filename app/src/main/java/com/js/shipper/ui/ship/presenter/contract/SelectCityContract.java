package com.js.shipper.ui.ship.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.SelectCity;

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

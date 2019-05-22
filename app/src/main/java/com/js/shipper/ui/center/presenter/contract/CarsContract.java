package com.js.shipper.ui.center.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/29.
 */
public interface CarsContract {

    interface View extends IBaseView{

    }

    interface Presenter extends IPresenter<View>{

    }
}

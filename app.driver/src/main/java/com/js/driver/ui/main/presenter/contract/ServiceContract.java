package com.js.driver.ui.main.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019/4/1.
 */
public interface ServiceContract {

    interface View extends IBaseView {

    }

    interface Presenter extends IPresenter<View> {

    }

}
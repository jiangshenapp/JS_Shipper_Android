package com.js.driver.ui.center.presenter.contract;

import com.js.driver.model.bean.RouteBean;
import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/06/12
 * desc   :
 * version: 3.0.0
 */
public interface RoutesDetailContract {

    interface View extends IBaseView {
        void onRouteDetail(RouteBean routeBean);
        void onApplyClassicLine();
        void onEnableLine();
    }

    interface Presenter extends IPresenter<View> {
        void getRouteDetail(long id);
        void applyClassicLine(long id);
        void enableLine(long lineId, long enable);
    }
}

package com.js.shipper.ui.main.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.request.ParkList;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/30.
 */
public interface DeliveryContract {

    interface View extends IBaseView {
        void onParkList(ListResponse<ParkBean> listResponse);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View> {
        void getParkList(int current ,int size,ParkList parkList);
    }

}

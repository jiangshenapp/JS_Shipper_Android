package com.js.shipper.ui.park.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/30.
 */
public interface CollectDeliveryContract {

    interface View extends IBaseView {
        void onCollectBranchs(ListResponse<ParkBean> listResponse);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View> {
        void getCollectBranchs(int current, int size);
    }

}

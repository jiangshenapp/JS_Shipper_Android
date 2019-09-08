package com.js.shipper.ui.park.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.ParkBean;
import com.js.shipper.model.request.CollectPark;

/**
 * Created by huyg on 2019-06-16.
 */
public interface BranchDetailContract {

    interface View extends IBaseView {
        void onBranchDetail(ParkBean parkBean);

        void onAddCollect(boolean isOk);

        void onRemoveCollect(boolean isOk);
    }

    interface Presenter extends IPresenter<View> {
        void getBranchDetail(long id);

        void addCollect(CollectPark collectPark);

        void removeCollect(CollectPark collectPark);
    }
}

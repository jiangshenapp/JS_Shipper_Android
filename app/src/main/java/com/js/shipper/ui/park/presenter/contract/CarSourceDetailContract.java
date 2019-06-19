package com.js.shipper.ui.park.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.CollectLine;

/**
 * Created by huyg on 2019-06-16.
 */
public interface CarSourceDetailContract {

    interface View extends IBaseView {
        void onLineDetail(LineBean lineBean);

        void onAddCollect(boolean isOk);

        void onRemoveCollect(boolean isOk);
    }

    interface Presenter extends IPresenter<View> {
        void getLineDetail(long id);

        void addCollect(CollectLine collectLine);

        void removeCollect(CollectLine collectLine);
    }
}

package com.js.shipper.ui.order.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.request.OrderEdit;

/**
 * Created by huyg on 2019-06-17.
 */
public interface OrderEditContract {

    interface View extends IBaseView{
        void onEditOrder(boolean isOk);
    }

    interface Presenter extends IPresenter<View>{
        void editOrder(OrderEdit orderEdit,long id);
    }
}

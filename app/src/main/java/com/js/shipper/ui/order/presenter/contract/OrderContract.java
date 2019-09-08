package com.js.shipper.ui.order.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/29.
 */
public interface OrderContract {

    interface View extends IBaseView{
        void onOrderList(ListResponse<OrderBean> orders);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View>{
        void getOrderList(int state,int current,int size);
    }
}

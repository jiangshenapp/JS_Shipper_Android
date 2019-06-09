package com.js.shipper.ui.wallet.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.BillBean;

import java.util.List;

/**
 * Created by huyg on 2019/4/24.
 */
public interface BillContract {

    interface View extends IBaseView {
        void onBillList(List<BillBean> billBeans);

        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View> {
        void getBillList(int status);
    }
}

package com.js.shipper.ui.main.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.LineAppFind;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/30.
 */
public interface CarSourceContract {

    interface View extends IBaseView{
        void onCarSource(ListResponse<LineBean> mLineBeans);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View>{
        void getCarSource(int current, LineAppFind lineAppFind, int size);
    }
}

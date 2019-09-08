package com.js.shipper.ui.main.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.LineAppFind;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/30.
 */
public interface BoutiqueContract {

    interface View extends IBaseView{
        void onClassicLine(ListResponse<LineBean> response);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View>{
        void getClassicLine(int current, LineAppFind lineAppFind, int size);
    }
}

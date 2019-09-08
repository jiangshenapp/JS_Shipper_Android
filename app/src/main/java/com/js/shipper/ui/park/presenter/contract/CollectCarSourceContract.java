package com.js.shipper.ui.park.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/30.
 */
public interface CollectCarSourceContract {

    interface View extends IBaseView{
        void onCollectLines(ListResponse<LineBean> mLineBeans);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View>{
        void getCollectLines(int current, int size);
    }
}

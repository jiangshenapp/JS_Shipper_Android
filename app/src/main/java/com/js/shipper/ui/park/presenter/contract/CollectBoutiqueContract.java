package com.js.shipper.ui.park.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.response.ListResponse;

/**
 * Created by huyg on 2019/4/30.
 */
public interface CollectBoutiqueContract {

    interface View extends IBaseView{
        void onCollectClassics(ListResponse<LineBean> response);
        void finishRefreshAndLoadMore();
    }

    interface Presenter extends IPresenter<View>{
        void getCollectClassics(int current,int size);
    }
}

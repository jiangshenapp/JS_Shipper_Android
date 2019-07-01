package com.js.shipper.ui.order.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.response.ListResponse;

import java.util.List;

/**
 * Created by huyg on 2019/4/29.
 */
public interface TypeInputContract {

    interface View extends IBaseView {
        void onDictByType(String type, List<DictBean> dictBeans);
    }

    interface Presenter extends IPresenter<View> {
        void getDictByType(String type);
    }
}

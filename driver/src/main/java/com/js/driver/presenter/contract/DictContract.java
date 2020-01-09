package com.js.driver.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.driver.model.bean.DictBean;

import java.util.List;

/**
 * Created by huyg on 2019-06-09.
 */
public interface DictContract {


    interface View extends IBaseView {

        void onDictByType(String type, List<DictBean> dictBeans);
        void onFirstDictByType(String type, DictBean dictBean);
    }

    interface Presenter extends IPresenter<View> {

        void getDictByType(String type);
        void getFirstDictByType(String type);
    }
}

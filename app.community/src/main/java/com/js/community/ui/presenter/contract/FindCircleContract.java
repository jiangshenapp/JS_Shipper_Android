package com.js.community.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.community.model.bean.CircleBean;

import java.util.List;

/**
 * Created by huyg on 2019-09-09.
 */
public interface FindCircleContract {


    interface View extends IBaseView{
        void onAllCircle(List<CircleBean> circleBeans);
        void finishRefresh();
        void onApplayCircle(Boolean b);
    }

    interface Presenter extends IPresenter<View>{
        void getAllCircle(String city,int showSide);
        void applyCircle(long id);
    }
}

package com.js.driver.ui.main.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.community.model.bean.CircleBean;

import java.util.List;

/**
 * Created by huyg on 2019/4/1.
 */
public interface CommunityContract {

    interface View extends IBaseView{
        void onCircles(List<CircleBean> circleBeans);
        void finishRefresh();
    }

    interface Presenter extends IPresenter<View>{
        void getCircles();
    }
}

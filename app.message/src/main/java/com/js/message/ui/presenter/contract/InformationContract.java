package com.js.message.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

import retrofit2.http.Query;

/**
 * Created by huyg on 2019/4/1.
 */
public interface InformationContract {

    interface View extends IBaseView {
        void onGetUnreadMessageCount(String count);
        void onGetUnreadPushLogCount(String count);
    }

    interface Presenter extends IPresenter<View> {
        void getUnreadMessageCount(int pushSide);
        void getUnreadPushLogCount(int pushSide);
    }
}
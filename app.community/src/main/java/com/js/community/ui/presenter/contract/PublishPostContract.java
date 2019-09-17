package com.js.community.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019-09-11.
 */
public interface PublishPostContract {


    interface View extends IBaseView {
        void onAddPost();
    }

    interface Presenter extends IPresenter<View> {
        void addPost(long circleId, String content, String image, String subject);
    }
}

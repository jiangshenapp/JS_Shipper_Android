package com.js.community.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.community.model.bean.PostBean;

import java.util.List;

/**
 * Created by huyg on 2019-09-09.
 */
public interface CircleIndexContract {

    interface View extends IBaseView {
        void onPosts(List<PostBean> postBeans);
    }

    interface Presenter extends IPresenter<View> {
        void getPosts(long circleId, String subject);
    }
}

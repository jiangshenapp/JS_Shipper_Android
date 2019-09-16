package com.js.community.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.community.model.bean.PostBean;

import java.util.List;

/**
 * Created by huyg on 2019-09-16.
 */
public interface PostContract {


    interface View extends IBaseView {
        void onPosts(List<PostBean> postBeans);
        void finishRefresh();
    }

    interface Presenter extends IPresenter<View> {
        void getPosts(long circleId, String subject, boolean commentFlag, boolean likeFlag, boolean myFlag);
    }
}

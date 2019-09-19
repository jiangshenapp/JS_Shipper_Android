package com.js.community.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019-09-19.
 */
public interface CommentContract {

    interface View extends IBaseView{
        void onComment();
    }

    interface Presenter extends IPresenter<View>{
        void comment(long postId,String comment);
    }
}

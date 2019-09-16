package com.js.community.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.community.model.bean.Comment;

import java.util.List;

/**
 * Created by huyg on 2019-09-10.
 */
public interface PostDetailContract {


    interface View extends IBaseView {
        void onCommentList(List<Comment> comments);

        void onLikePost();

    }

    interface Presenter extends IPresenter<View> {
        void getCommentList(long postId);

        void likePost(long postId);
    }
}

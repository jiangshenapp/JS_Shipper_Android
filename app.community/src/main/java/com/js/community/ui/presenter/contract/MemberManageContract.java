package com.js.community.ui.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;
import com.js.community.model.bean.Member;

import java.util.List;

/**
 * Created by huyg on 2019-09-11.
 */
public interface MemberManageContract {

    interface View extends IBaseView {
        void onMembers(List<Member> members);
        void finishRefresh();
    }

    interface Presenter extends IPresenter<View> {
        void getMembers(long circleId);
    }
}

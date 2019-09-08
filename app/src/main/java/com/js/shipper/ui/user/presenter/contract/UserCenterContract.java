package com.js.shipper.ui.user.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019/4/24.
 */
public interface UserCenterContract {

    interface View extends IBaseView{
        void onChangeAvatar();
        void onChangeNickname();
    }

    interface Presenter extends IPresenter<View>{
        void changeAvatar(String avatar);
        void changeNickname(String nickname);
    }
}

package com.js.shipper.ui.wallet.presenter.contract;

import com.js.frame.mvp.IBaseView;
import com.js.frame.mvp.IPresenter;
import com.js.shipper.model.bean.AccountInfo;

/**
 * Created by huyg on 2019/4/24.
 */
public interface WalletContract {

    interface View extends IBaseView {
        void onAccountInfo(AccountInfo accountInfo);
    }

    interface Presenter extends IPresenter<View> {
        void getAccountInfo();
    }

}

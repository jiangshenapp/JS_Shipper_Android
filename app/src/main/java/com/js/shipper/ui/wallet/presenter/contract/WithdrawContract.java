package com.js.shipper.ui.wallet.presenter.contract;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

/**
 * Created by huyg on 2019/4/24.
 */
public interface WithdrawContract {

    interface View extends IBaseView {
        void onBalanceWithdraw();
    }

    interface Presenter extends IPresenter<View> {
        void balanceWithdraw(int withdrawType, int withdrawChannel,
                             String bankCard, String khh, String zh,
                             String zfbzh, String zfbmc);
    }
}

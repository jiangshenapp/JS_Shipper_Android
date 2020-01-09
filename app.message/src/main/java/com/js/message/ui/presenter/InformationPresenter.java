package com.js.message.ui.presenter;

import com.base.frame.mvp.RxPresenter;
import com.js.message.ui.presenter.contract.InformationContract;
import javax.inject.Inject;

/**
 * Created by huyg on 2019/4/1.
 */
public class InformationPresenter extends RxPresenter<InformationContract.View> implements InformationContract.Presenter {

    @Inject
    public InformationPresenter(){

    }
}

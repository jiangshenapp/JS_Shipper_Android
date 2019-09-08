package com.js.shipper.ui.ship.presenter;

import com.base.frame.mvp.RxPresenter;
import com.js.shipper.ui.ship.presenter.contract.SelectAddressContrat;

import javax.inject.Inject;

/**
 * Created by huyg on 2019/5/13.
 */
public class SelectAddressPresenter extends RxPresenter<SelectAddressContrat.View> implements SelectAddressContrat.Presenter{


    @Inject
    public SelectAddressPresenter(){

    }


}

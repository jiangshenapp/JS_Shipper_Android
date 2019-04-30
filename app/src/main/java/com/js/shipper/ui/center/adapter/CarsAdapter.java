package com.js.shipper.ui.center.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.model.bean.CarBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/29.
 */
public class CarsAdapter extends BaseQuickAdapter<CarBean, BaseViewHolder> {

    public CarsAdapter(int layoutResId, @Nullable List<CarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBean item) {

    }
}

package com.js.driver.ui.center.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.driver.R;
import com.js.driver.model.bean.DriverBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/29.
 */
public class DriversAdapter extends BaseQuickAdapter<DriverBean, BaseViewHolder> {

    public DriversAdapter(int layoutResId, @Nullable List<DriverBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DriverBean item) {
        helper.addOnClickListener(R.id.item_driver_delete);
    }
}

package com.js.driver.ui.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.driver.model.bean.OrderBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/24.
 */
public class FindOrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {

    public FindOrderAdapter(int layoutResId, @Nullable List<OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {

    }
}

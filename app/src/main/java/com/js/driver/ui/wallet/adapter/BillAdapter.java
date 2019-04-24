package com.js.driver.ui.wallet.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.driver.model.bean.BillBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/24.
 */
public class BillAdapter extends BaseQuickAdapter<BillBean, BaseViewHolder> {

    public BillAdapter(int layoutResId, @Nullable List<BillBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean item) {

    }
}

package com.js.shipper.ui.ship.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.CityBean;

import java.util.List;

/**
 * Created by huyg on 2019-06-06.
 */
public class CityAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {

    public CityAdapter(int layoutResId, @Nullable List<CityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean item) {
        helper.setText(R.id.tvCity, item.getCity());
    }
}

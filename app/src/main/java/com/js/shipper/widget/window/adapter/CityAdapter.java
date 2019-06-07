package com.js.shipper.widget.window.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.AreaBean;

import java.util.List;

/**
 * Created by huyg on 2019-06-05.
 */
public class CityAdapter extends BaseQuickAdapter<AreaBean, BaseViewHolder> {

    public CityAdapter(int layoutResId, @Nullable List<AreaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaBean item) {
        helper.setText(R.id.item_city, item.getName());
        if (item.isChecked()) {
            helper.setBackgroundRes(R.id.item_city, R.drawable.shape_city_checked);
        } else {
            helper.setBackgroundRes(R.id.item_city, R.drawable.shape_city_normal);

        }
    }
}

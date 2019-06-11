package com.js.shipper.widget.window.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.DictBean;

import java.util.List;

/**
 * Created by huyg on 2019-06-09.
 */
public class DictAdapter extends BaseQuickAdapter<DictBean, BaseViewHolder> {

    public DictAdapter(int layoutResId, @Nullable List<DictBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DictBean item) {
        helper.setText(R.id.item_dict, item.getLabel());
        if (item.isChecked()) {
            helper.setBackgroundRes(R.id.item_dict, R.drawable.shape_city_checked);
        } else {
            helper.setBackgroundRes(R.id.item_dict, R.drawable.shape_city_normal);
        }
    }
}

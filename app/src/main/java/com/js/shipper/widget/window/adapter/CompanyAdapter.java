package com.js.shipper.widget.window.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;

import java.util.List;

/**
 * Created by huyg on 2019-06-20.
 */
public class CompanyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CompanyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_content,item);
    }
}

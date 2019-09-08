package com.js.shipper.widget.window.adapter;

import androidx.annotation.Nullable;

import com.baidu.mapapi.search.core.PoiInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;

import java.util.List;

/**
 * Created by huyg on 2019-06-17.
 */
public class SearchAddressApdater extends BaseQuickAdapter<PoiInfo, BaseViewHolder> {

    public SearchAddressApdater(int layoutResId, @Nullable List<PoiInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiInfo item) {
        helper.setText(R.id.item_address_name,item.name)
                .setText(R.id.item_address,item.address);
    }
}

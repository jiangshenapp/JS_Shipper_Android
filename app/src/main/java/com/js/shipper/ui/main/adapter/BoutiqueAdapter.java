package com.js.shipper.ui.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.LineBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/30.
 */
public class BoutiqueAdapter extends BaseQuickAdapter<LineBean, BaseViewHolder> {

    public BoutiqueAdapter(int layoutResId, @Nullable List<LineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LineBean item) {
        helper.setText(R.id.item_send_address,item.getStartAddressCodeName())
                .setText(R.id.item_arrive_address,item.getArriveAddressCodeName());
//                .setText(R.id.item_send_date,item.ge)
    }
}

package com.js.shipper.ui.main.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.ParkBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/30.
 */
public class DeliveryAdapter extends BaseQuickAdapter<ParkBean, BaseViewHolder> {

    public DeliveryAdapter(int layoutResId, @Nullable List<ParkBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ParkBean item) {
        helper.setText(R.id.item_delivery_branch, item.getCompanyName())
                .setText(R.id.item_address, item.getDetailAddress())
                .setText(R.id.item_remark, item.getRemark());
        helper.addOnClickListener(R.id.remark_status_layout);
        TextView remark = helper.getView(R.id.item_remark);
        if (item.isRemark()){
            remark.setVisibility(View.VISIBLE);
            helper.setImageResource(R.id.remark_status,R.mipmap.ic_delivery_arrow_up);
        }else {
            remark.setVisibility(View.GONE);
            helper.setImageResource(R.id.remark_status,R.mipmap.ic_delivery_arrow_down);
        }
    }
}

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
        switch (item.getCompanyType()) {
            case 1:
                helper.setText(R.id.item_delivery_branch, item.getCompanyName()+"[服务中心]");
                break;
            case 2:
                helper.setText(R.id.item_delivery_branch, item.getCompanyName()+"[车代点]");
                break;
            case 3:
                helper.setText(R.id.item_delivery_branch, item.getCompanyName()+"[网点]");
                break;
        }

        helper.setText(R.id.item_address, item.getDetailAddress())
                .setText(R.id.item_remark, item.getRemark());
        helper.addOnClickListener(R.id.remark_status_layout);
        helper.addOnClickListener(R.id.navigation);
        helper.addOnClickListener(R.id.item_phone);
        helper.addOnClickListener(R.id.item_chat);
        TextView remark = helper.getView(R.id.item_remark);
        if (item.isRemark()) {
            remark.setVisibility(View.VISIBLE);
            helper.setImageResource(R.id.remark_status, R.mipmap.ic_delivery_arrow_up);
        } else {
            remark.setVisibility(View.GONE);
            helper.setImageResource(R.id.remark_status, R.mipmap.ic_delivery_arrow_down);
        }
    }
}

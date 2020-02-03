package com.js.shipper.ui.order.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.OrderBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {


    public OrderAdapter(int layoutResId, @Nullable List<OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        String info = "";
        if (!TextUtils.isEmpty(item.getCarModelName())) {
            info += item.getCarModelName();
        }
        if (!TextUtils.isEmpty(item.getCarLengthName())) {
            info += item.getCarLengthName();
        }
        if (item.getGoodsVolume()!=0) {
            info += "/"+item.getGoodsVolume()+"方";
        }
        if (item.getGoodsWeight()!=0) {
            info += "/"+item.getGoodsWeight()+"千克";
        }
        helper.setText(R.id.waybill_order_no, "订单编号：" + item.getOrderNo())
                .setText(R.id.waybill_state, item.getStateNameConsignor())
                .setText(R.id.send_address, item.getSendAddress())
                .setText(R.id.end_address, item.getReceiveAddress())
                .setText(R.id.waybill_info, info);
        if (item.getFee() == 0) {
            helper.setVisible(R.id.item_waybill_money, false);
        } else {
            helper.setText(R.id.item_waybill_money, "￥" + item.getFee());
            helper.setVisible(R.id.item_waybill_money, true);
        }
    }
}

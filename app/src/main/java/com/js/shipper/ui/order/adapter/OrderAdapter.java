package com.js.shipper.ui.order.adapter;

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
        helper.setText(R.id.waybill_order_no, "订单编号：" + item.getOrderNo())
                .setText(R.id.waybill_state, item.getStateName())
                .setText(R.id.send_address, item.getSendAddress())
                .setText(R.id.end_address, item.getReceiveAddress())
                .setText(R.id.waybill_info, item.getGoodsType() + " "
                        + item.getGoodsVolume() + "方/"
                        + item.getGoodsWeight()+"吨");
        if (item.getFee() == 0) {
            helper.setVisible(R.id.item_waybill_money, false);
        } else {
            helper.setText(R.id.item_waybill_money, "￥" + item.getFee());
            helper.setVisible(R.id.item_waybill_money, true);
        }
    }
}

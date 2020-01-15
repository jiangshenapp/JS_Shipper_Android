package com.js.shipper.ui.car.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.CarBean;

import java.util.List;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/15
 * desc   :
 * version: 3.0.0
 */
public class AddCarAdapter extends BaseQuickAdapter<CarBean, BaseViewHolder> {

    public AddCarAdapter(int layoutResId, @Nullable List<CarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBean item) {
        helper.setText(R.id.item_car_name,item.getCphm())
                .setText(R.id.item_driver_info,item.getNickName()+" "+item.getMobile())
                .setText(R.id.item_car_info,"车型："+item.getCarModelName()+"  车长："+item.getCarLengthName());

        if (item.getCooperated() > 0) {
            helper.getView(R.id.item_cooperation).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_cooperation).setVisibility(View.GONE);
        }
        helper.getView(R.id.item_remark).setVisibility(View.GONE);
        helper.getView(R.id.item_phone).setVisibility(View.GONE);

        if (item.getAdded() > 0) { //已添加
            helper.getView(R.id.item_submit).setBackgroundResource(R.drawable.shape_border_search);
            helper.setText(R.id.item_submit,"已添加");
        } else {
            helper.getView(R.id.item_submit).setBackgroundResource(R.drawable.shape_wallet_info_control);
            helper.setText(R.id.item_submit,"添加");
            helper.addOnClickListener(R.id.item_submit);
        }
    }
}

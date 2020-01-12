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
 * time   : 2020/01/12
 * desc   :
 * version: 3.0.0
 */
public class CarAdapter extends BaseQuickAdapter<CarBean, BaseViewHolder> {

    public CarAdapter(int layoutResId, @Nullable List<CarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBean item) {
        String carName = item.getCphm();
        if (item.getType() == 1) { //1自有车辆，2外调车辆
            carName += "[自有车辆]";
        } else {
            carName += "[外调车辆]";
        }
        String remark = "";
        if (!TextUtils.isEmpty(item.getRemark())) {
            remark += "备注："+item.getRemark();
        }
        helper.setText(R.id.item_car_name,carName)
                .setText(R.id.item_driver_info,item.getNickName()+" "+item.getMobile())
                .setText(R.id.item_car_info,"车型："+item.getCarModelName()+"  车长："+item.getCarLengthName())
                .setText(R.id.item_remark,remark);

        if (item.getCooperated() > 0) {
            helper.getView(R.id.item_cooperation).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_cooperation).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.phone);
        helper.addOnClickListener(R.id.submit);
    }
}

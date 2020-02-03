package com.js.driver.ui.center.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.driver.R;
import com.js.driver.model.bean.CarBean;
import com.base.http.global.Const;
import com.js.driver.util.glide.CommonGlideImageLoader;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/29.
 */
public class CarsAdapter extends BaseQuickAdapter<CarBean, BaseViewHolder> {

    public CarsAdapter(int layoutResId, @Nullable List<CarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBean item) {
        helper.setText(R.id.item_car_number,item.getCphm())
                .setText(R.id.item_car_state,item.getStateName())
                .setText(R.id.item_car_type,item.getCarModelName()+item.getCarLengthName()+"米/"
                        +item.getCapacityVolume()+"方/"
                        +item.getCapacityTonnage()+"千克");

        helper.setTextColor(R.id.item_car_state,Color.parseColor(com.js.driver.global.Const.CarStateColor[item.getState()]));
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + item.getImage2()
                , helper.getView(R.id.item_car_img));
    }
}

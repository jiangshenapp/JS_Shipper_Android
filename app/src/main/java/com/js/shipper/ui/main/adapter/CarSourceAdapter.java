package com.js.shipper.ui.main.adapter;

import android.text.TextUtils;
import android.view.TextureView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.util.TimeUtils;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/30.
 */
public class CarSourceAdapter extends BaseQuickAdapter<LineBean, BaseViewHolder> {

    private DecimalFormat df = new DecimalFormat("#####0.0");

    public CarSourceAdapter(int layoutResId, @Nullable List<LineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LineBean item) {

        String info = "";
        if (!TextUtils.isEmpty(item.getDriverName())) {
            info += item.getDriverName();
        }
        if (!TextUtils.isEmpty(item.getCarLengthName())) {
            info += " " + item.getCarLengthName();
        }
        if (!TextUtils.isEmpty(item.getCarModelName())) {
            info += "/" + item.getCarModelName();
        }
        helper.setText(R.id.item_send_address, item.getStartAddressCodeName())
                .setText(R.id.item_arrive_address, item.getArriveAddressCodeName())
                .setText(R.id.item_driver_info, info);
//        double distance = DistanceUtil.getDistance(mGson.fromJson(mSendShip.getPosition(), LatLng.class), mGson.fromJson(mEndShip.getPosition(), LatLng.class));
//        helper.setText(R.id.item_distance,"总里程" + (distance > 1000 ? df.format(distance / 1000) + " Km" : ((int) distance) + "米");)
//                .setText(R.id.item_time, TimeUtils.format(item.getti))

    }
}

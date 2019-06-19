package com.js.shipper.util;

import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.js.shipper.App;
import com.js.shipper.R;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Created by huyg on 2019-05-30.
 */
public class AppUtils {

    private static DecimalFormat df = new DecimalFormat("#####0.0");


    public static String getDistance(LatLng latLng) {
        double distance = 0;
        if (App.getInstance() != null) {
            distance = DistanceUtil.getDistance(latLng, new LatLng(App.getInstance().mLocation.getLatitude(), App.getInstance().mLocation.getLongitude()));
        }
        return distance > 1000 ? df.format(distance / 1000) + " Km" : distance + "m";
    }


    public static boolean isMoney(String money){
        String regex = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
        return money.matches(regex);
    }



    public static View getEmptyView(){
        return LayoutInflater.from(App.getInstance()).inflate(R.layout.layout_data_empty,null);
    }



}

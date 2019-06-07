package com.js.shipper.model.event;

import com.js.shipper.model.bean.AreaBean;

/**
 * Created by huyg on 2019-06-06.
 */
public class CitySelectEvent {
    public int type;
    public AreaBean areaBean;

    public CitySelectEvent(int type, AreaBean areaBean) {
        this.type = type;
        this.areaBean = areaBean;
    }
}

package com.js.shipper.model.event;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * Created by huyg on 2019-06-17.
 */
public class PoiAddrInfoEvent {
    public PoiInfo poiInfo;

    public PoiAddrInfoEvent(PoiInfo poiInfo) {
        this.poiInfo = poiInfo;
    }
}

package com.js.shipper.api;

import com.base.frame.bean.HttpResponse;
import com.js.shipper.model.bean.ConsigneeBean;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by huyg on 2019-05-21.
 * 常用收货人
 */
public interface ConsigneeApi {


    /**
     * 获取指定会员常用收货人
     * @param current
     * @param size
     * @return
     */
    @GET("app/consignee/list")
    Observable<HttpResponse<ListResponse<ConsigneeBean>>> getConsignees(@Query("current") int current,
                                                                        @Query("size") int size);


}

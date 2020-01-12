package com.js.shipper.api;

import com.base.frame.bean.HttpResponse;
import com.js.shipper.model.bean.BillBean;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.model.request.CollectLine;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/12
 * desc   : 我的运力接口
 * version: 3.0.0
 */
public interface CarApi {

    /**
     * 我的运力列表
     *
     * @param type 类型，0全部，1自由车辆，2外调车辆
     * @return the my car list
     */
    @GET("app/consignorcar/list")
    Observable<HttpResponse<List<CarBean>>> getMyCarList(@Query("type") long type);


    /**
     * 查询运力列表
     *
     * @param input 车牌号、会员手机号、司机姓名
     * @return the my car list
     */
    @GET("app/consignorcar/carList")
    Observable<HttpResponse<List<CarBean>>> queryCarList(@Query("input") long input);


    /**
     * 添加运力
     *
     * @param carId  车辆ID
     * @param remark 备注
     * @param type   类型，1自由车辆，2外调车辆
     * @return the observable
     */
    @GET("app/consignorcar/addCar")
    Observable<HttpResponse<Boolean>> addCar(@Query("carId") long carId,
                                             @Query("remark") String remark,
                                             @Query("type") long type);


    /**
     * 删除运力
     *
     * @param id 车辆ID
     * @return observable
     */
    @POST("app/consignorcar/removeCar")
    Observable<HttpResponse<Boolean>> removeCar(@Query("id") long id);
}

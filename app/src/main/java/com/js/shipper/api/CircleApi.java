package com.js.shipper.api;

import com.base.http.HttpResponse;
import com.js.community.model.bean.CircleBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-09-09.
 */
public interface CircleApi {


    /**
     * 我的圈子
     *
     * @return
     */
    @POST("app/circle/list")
    Observable<HttpResponse<List<CircleBean>>> getCircles();


}

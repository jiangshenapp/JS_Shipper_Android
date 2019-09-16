package com.js.driver.api;

import com.base.http.HttpResponse;
import com.js.driver.model.bean.BannerBean;
import com.js.driver.model.bean.ServiceBean;
import com.js.driver.model.request.BannerRequest;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/15
 * desc   : 服务配置、广告
 * version: 3.0.0
 */
public interface ServiceApi {

    /**
     * 获取系统服务列表
     */
    @POST("app/sys/getSysServiceList")
    Observable<HttpResponse<List<ServiceBean>>> getSysServiceList();

    /**
     * 根据类型获取banner
     * type 类型 1服务页广告、2发货页广告、3启动页广告
     */
    @POST("app/banner/list")
    Observable<HttpResponse<List<BannerBean>>> getBannerList(@Body BannerRequest bannerRequest);
}

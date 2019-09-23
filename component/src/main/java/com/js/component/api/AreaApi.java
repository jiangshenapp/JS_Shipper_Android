package com.js.component.api;

import com.base.frame.bean.HttpResponse;
import com.js.component.city.bean.SelectCity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-06-06.
 */
public interface AreaApi {


    @POST("app/area/getCityTree")
    Observable<HttpResponse<List<SelectCity>>> getCityList();
}

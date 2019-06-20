package com.js.shipper.api;

import com.js.http.HttpResponse;
import com.js.shipper.model.bean.LineBean;
import com.js.shipper.model.request.LineAppFind;
import com.js.shipper.model.request.LineClassic;
import com.js.shipper.model.response.ListResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by huyg on 2019/5/18.
 */
public interface LineApi {


    /**
     * 车源
     * @param current
     * @param data
     * @param size
     * @return
     */
    @POST("app/line/find")
    Observable<HttpResponse<ListResponse<LineBean>>> getCarLine(@Query("current") int current,
                                                                @Body LineAppFind data,
                                                                @Query("size") int size);


    /**
     * 精品线路
     * @param current
     * @param data
     * @param size
     * @return
     */
    @POST("app/line/classic")
    Observable<HttpResponse<ListResponse<LineBean>>> getClassicLine(@Query("current") int current,
                                                                    @Body LineAppFind data,
                                                                    @Query("size") int size);




    /**
     * 线路详情
     */
    @POST("app/line/get/{id}")
    Observable<HttpResponse<LineBean>> getLineDetail(@Path("id") long id);

}

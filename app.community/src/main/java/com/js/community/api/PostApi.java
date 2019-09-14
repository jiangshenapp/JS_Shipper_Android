package com.js.community.api;

import android.system.Os;

import com.base.http.BaseHttpResponse;
import com.base.http.HttpResponse;
import com.js.community.model.bean.Comment;
import com.js.community.model.bean.PostBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2019-09-10.
 */
public interface PostApi {


    /**
     * 发帖
     * @param circleId 圈子id
     * @param content 内容
     * @param image 图片
     * @param subject 话题
     * @return
     */
    @FormUrlEncoded
    @POST("app/post/addPost")
    Observable<BaseHttpResponse> addPost(@Field("circleId") long circleId,
                                         @Field("content") String content,
                                         @Field("image") String image,
                                         @Field("subject") String subject);


    /**
     * 评论
     * @param comment
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("app/post/comment")
    Observable<BaseHttpResponse> commentPost(@Field("comment") String comment,
                                             @Field("postId") long postId);


    /**
     * 评论列表
     * @param postId 帖子id
     * @return
     */
    @FormUrlEncoded
    @POST("app/post/commentList")
    Observable<HttpResponse<List<Comment>>> getComments(@Field("postId") String postId);


    /**
     * 点赞
     * @param postId 帖子id
     * @return
     */
    @FormUrlEncoded
    @POST("app/post/like")
    Observable<BaseHttpResponse> likePost(@Field("postId") long postId);


    /**
     * 帖子列表
     * @param circleId 圈子id
     * @param subject 话题
     * @return
     */
    @FormUrlEncoded
    @POST("app/post/list")
    Observable<HttpResponse<List<PostBean>>> getPostList(@Field("circleId") long circleId,
                                                         @Field("subject") String subject);

}

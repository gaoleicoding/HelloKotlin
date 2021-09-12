package com.gl.kotlin.retrofit

import com.gl.kotlin.model.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiWanAndroid {

    @GET("/banner/json")
    suspend fun banners(): BaseResponse<List<Banner>>

    @GET("/hotkey/json")
    suspend fun hotKeys(): BaseResponse<List<HotKey>>

    @POST("/article/query/{pageNum}/json")
    suspend fun searchArticles(
        @Path("pageNum") pageNum: Int,
        @Query("k") key: String
    ): BaseResponse<PageInfo<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): BaseResponse<PageInfo<Article>>

}
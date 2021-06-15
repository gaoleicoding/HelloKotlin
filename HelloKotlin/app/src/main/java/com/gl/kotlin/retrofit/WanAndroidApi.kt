package tech.kicky.common

import com.gl.kotlin.model.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Wan Android API
 * author: yidong
 * 2021/1/2
 */
interface WanAndroidApi {

    @GET("/banner/json")
    suspend fun banners(): BasicResponse<List<Banner>>

    @GET("/hotkey/json")
    suspend fun hotKeys(): BasicResponse<List<HotKey>>

    @POST("/article/query/{pageNum}/json")
    suspend fun searchArticles(
        @Path("pageNum") pageNum: Int,
        @Query("k") key: String
    ): BasicResponse<PageInfo<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): BasicResponse<PageInfo<Article>>

}
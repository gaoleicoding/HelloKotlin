package com.gl.kotlin.retrofit

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Download Video API
 * author: yidong
 * 2021/1/2
 */
interface DownloadApi {
    @GET
    suspend fun downloadVideo(@Url url: String): ResponseBody
}
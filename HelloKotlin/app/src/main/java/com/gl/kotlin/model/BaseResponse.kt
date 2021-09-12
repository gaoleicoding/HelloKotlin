package com.gl.kotlin.model

/**
 * WanAndroid Root
 * author: yidong
 * 2021/1/2
 */
data class BaseResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)



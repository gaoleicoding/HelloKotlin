package com.gl.kotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtil {

    val TAG: String = "RetrofitManager"
    val defaultBaseUrl: String = "https://api.github.com/"
    val wanAndroidBaseUrl: String = "https://www.wanandroid.com"

    val retrofitMap = mutableMapOf<String, Retrofit>()
    val providerMap = mutableMapOf<Any, Any?>()


    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = RetrofitUtil()
    }

    fun <T> get(service: Class<T>): T {

        val retrofit = getRetrofit(defaultBaseUrl)
        val entity = providerMap.get(service)
        if (entity != null) {
            return entity as T
        }
        val provider = retrofit.create(service)
        providerMap.put(service, retrofit)
        return provider
    }

    fun <T> get(baseUrl: String, service: Class<T>): T {

        val retrofit = getRetrofit(baseUrl)
        val entity = providerMap.get(service)
        if (entity != null) {
            return entity as T
        }
        val provider = retrofit.create(service)
        providerMap.put(service, retrofit)
        return provider
    }

    fun getRetrofit(baseUrl: String): Retrofit {

        val entity = retrofitMap.get(baseUrl)
        if (entity != null) return entity
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitMap.put(baseUrl, retrofit)
        return retrofit
    }

}
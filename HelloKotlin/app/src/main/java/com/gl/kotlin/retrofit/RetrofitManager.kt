package com.gl.kotlin.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    val baseUrl: String = "https://www.wanandroid.com"
    val githubBaseUrl: String = "https://api.github.com/"

    private const val TIME_OUT = 60L  //超时时间
    private var mRetrofit: Retrofit? = null
    private var mOkHttpClientBuilder: OkHttpClient.Builder? = null
    private var mRetrofitServices = hashMapOf<String, Any>()

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        initOkHttpClient()
        mRetrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClientBuilder!!.build())
            .build()
    }

    private fun initOkHttpClient() {
        mOkHttpClientBuilder = OkHttpClient.Builder()
        mOkHttpClientBuilder?.run {

            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
//            addInterceptor(HttpHeaderInterceptor())
            // addInterceptor(MockApiInterceptor())
//            val loggingInterceptor = LoggingInterceptor()
//            if (BuildConfig.DEBUG) {
//                loggingInterceptor.level = LoggingInterceptor.Level.BASIC
//            }
//            addInterceptor(loggingInterceptor)
            //错误重连
            retryOnConnectionFailure(true)
        }
    }

    open fun <T> create(clazz: Class<T>): T {
        var key = clazz.canonicalName
        var mRetrofitService = mRetrofitServices[key]
        if (mRetrofitService == null) {
            mRetrofitService = mRetrofit!!.create(clazz)
            mRetrofitServices[key!!] = mRetrofitService!!
        }
        return mRetrofitService as T
    }


}
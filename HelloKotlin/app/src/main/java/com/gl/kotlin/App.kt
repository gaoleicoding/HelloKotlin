package com.gl.kotlin

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        connectivityManager = applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        context = this


    }

    init {
        //初始化一些数据
    }

    //伴生对象（Companion object），在Kotlin中是没有static关键字的，也就是意味着没有了静态方法和静态成员。
    // 那么在kotlin中如果要想表示这种概念，取而代之的是包级别函数（package-level function）和我们这里提到的伴生对象
    companion object {
        lateinit var connectivityManager: ConnectivityManager
        lateinit var context: Context

    }


}

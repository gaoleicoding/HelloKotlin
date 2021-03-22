package com.gl.kotlin.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gl.kotlin.entity.Banner
import com.gl.kotlin.entity.HotKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import tech.kicky.common.RetrofitManager

/**
 * main view model
 * author: yidong
 * 2021/1/2
 */
class MainViewModel : ViewModel() {

    val banners = MutableLiveData<List<Banner>>()
    val hotKeys = MutableLiveData<List<HotKey>>()
    val toastMsg = MutableLiveData<String>()


    fun viewModelCoroutine() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = RetrofitManager.wanAndroidApi.banners()
            banners.postValue(result.data)
        }
    }

    fun viewModelSequenceRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            val start = System.currentTimeMillis()
            // 先请求Banners
            val result = RetrofitManager.wanAndroidApi.banners()
            banners.postValue(result.data)
            // 再请求热键，只要是顺序执行即可且上一次的请求结果已拿到即可满足我们的使用场景。
            val keys = RetrofitManager.wanAndroidApi.hotKeys()
            hotKeys.postValue(keys.data)
            Log.d("Coroutine Sample", (System.currentTimeMillis() - start).toString())
        }
    }

    fun viewModelAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            val start = System.currentTimeMillis()
            val result = async { RetrofitManager.wanAndroidApi.banners() }
            val keys = async { RetrofitManager.wanAndroidApi.hotKeys() }
            Log.d(
                "Coroutine Sample",
                (result.await().data.size + keys.await().data.size).toString()
            )
            Log.d("Coroutine Sample", (System.currentTimeMillis() - start).toString())
        }
    }
}
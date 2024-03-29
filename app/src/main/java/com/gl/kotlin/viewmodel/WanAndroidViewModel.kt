package com.gl.kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gl.kotlin.base.BaseViewModel
import com.gl.kotlin.model.Article
import com.gl.kotlin.model.Banner
import com.gl.kotlin.model.HotKey
import com.gl.kotlin.retrofit.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.gl.kotlin.retrofit.ApiWanAndroid

class WanAndroidViewModel : BaseViewModel() {
    val TAG: String = javaClass.simpleName
    val banners = MutableLiveData<List<Banner>?>()
    val hotKeys = MutableLiveData<List<HotKey>?>()
    val searchArticles = MutableLiveData<List<Article>?>()
    val homeArticles = MutableLiveData<List<Article>?>()
    val toastMsg = MutableLiveData<String>()

    var apiWanAndroid: ApiWanAndroid = RetrofitManager.create(ApiWanAndroid::class.java)

    fun getBanners() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = provider.banners()
//            banners.postValue(result.data)
//        }
        launch(
            block = {
                val result = apiWanAndroid.banners()
                banners.postValue(result.data)
            }
        )
    }

    fun getBannersAndKeys() {
        viewModelScope.launch(Dispatchers.IO) {
            val start = System.currentTimeMillis()
            // 先请求Banners
            val result = async { apiWanAndroid.banners() }
            banners.postValue(result.await().data)
            // 再请求热键，只要是顺序执行即可且上一次的请求结果已拿到即可满足我们的使用场景。
            val keys = apiWanAndroid.hotKeys()
            hotKeys.postValue(keys.data)
            Log.d(TAG, "getBannersAndKeys-time: " + (System.currentTimeMillis() - start).toString())
        }
    }

    fun getBannersAndKeysAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            val start = System.currentTimeMillis()
            val result = async {
                apiWanAndroid.banners()
            }
            val keys = async {
                apiWanAndroid.hotKeys()
            }
            Log.d(TAG, (result.await().data.size + keys.await().data.size).toString())
            Log.d(TAG, ("getBannersAndKeysAsync-time: " + (System.currentTimeMillis() - start)))
        }
    }

    fun getHomeArticleList(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = async { apiWanAndroid.getHomeArticleList(page) }
            homeArticles.postValue(result.await().data.datas)
        }
    }

    fun getArticles(key: String) {
        viewModelScope.launch {
            flow {
                Log.d("Flow", "Emit on ${Thread.currentThread().name}")
                val result = apiWanAndroid.searchArticles(0, key)
                emit(result.data.datas)
            }.flowOn(Dispatchers.IO)
                .onStart {
                    _loading.value = true
                    Log.d("Flow", "onStart on ${Thread.currentThread().name}")
                }.onCompletion {
                    _loading.value = false
                    Log.d("Flow", "onComplete on ${Thread.currentThread().name}")
                }.catch { ex ->
                    ex.printStackTrace()
                    toastMsg.setValue(ex.message)
                }.collect {
                    Log.d("Flow", "Collect on ${Thread.currentThread().name}")
                    searchArticles.setValue(it)
                }
        }
    }

}
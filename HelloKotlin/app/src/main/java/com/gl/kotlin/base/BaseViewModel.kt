package com.gl.kotlin.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gl.kotlin.App
import com.gl.kotlin.R
import com.gl.kotlin.retrofit.ApiException
import com.gl.kotlin.util.Utils
import com.gl.kotlin.util.Utils.toast
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.net.ssl.SSLHandshakeException
import org.json.JSONException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

/*所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动*/
open class BaseViewModel : ViewModel() {

    val _loading = MutableLiveData<Boolean>()
    /*
    * 启动协程 
    */
    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                block.invoke(this)
                _loading.postValue(false)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        handleError(e, false)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /*
    * 启动协程 
    */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async {
            block.invoke(this)
        }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun handleError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效，清除用户信息、清除cookie/token
//                        UserInfoStore.clearUserInfo()
//                        RetrofitClient.clearCookie()
//                        loginStatusInvalid.value = true
                    }
                    // 其他api错误
                    -1 -> if (showErrorToast) Utils.showToast(e.message, true)
                    // 其他错误
                    else -> if (showErrorToast) Utils.showToast(e.message, true)
                }
            }
            // 网络请求失败
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            is SSLHandshakeException ->
                if (showErrorToast)
                    App.context.toast(App.context.resources.getString(R.string.network_request_failed))

            // 数据解析错误
            is JSONException->
                App.context.toast(App.context.resources.getString(R.string.api_data_parse_error))
            // 其他错误
            else ->
                if (showErrorToast) Utils.showToast(e.message ?: return,true)
        }
    }

}
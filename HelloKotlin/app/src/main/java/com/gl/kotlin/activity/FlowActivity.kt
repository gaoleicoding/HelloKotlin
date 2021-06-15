package com.gl.kotlin.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gl.kotlin.databinding.ActivityFlowBinding
import com.gl.kotlin.viewmodel.WanAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {
    val TAG: String = javaClass.simpleName

    private val mBinding: ActivityFlowBinding by lazy {
        ActivityFlowBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        GlobalScope.launch(Dispatchers.Main) {
            count().flowOn(Dispatchers.Unconfined) // 指定数据流产生运行线程
                .map {
                    Log.d("Coroutine", "map on ${Thread.currentThread().name}")
                    if (it > 15) {
                        throw NumberFormatException()
                    }
                    "I am $it"
                }.flowOn(Dispatchers.IO)           // 指定map中间action运行线程
                .catch { ex ->
                    Log.d("Coroutine", "catch on ${Thread.currentThread().name}")
                    emit("error")
                }.collect {
                    Log.d("Coroutine", "collect on ${Thread.currentThread().name}")
                    mBinding.textView.text = it
                }
        }
    }

    private fun count(): Flow<Int> = flow {
        var x = 0
        while (true) {
            if (x > 20) {
                break
            }
            delay(500)
            Log.d("Coroutine", "emit on ${Thread.currentThread().name}")
            emit(x)
            x = x.plus(1)
        }
    }

    suspend fun debouceDemo() {
        val result = flow {
            emit("h")
            emit("i")
            emit("d")
            delay(90)
            emit("dh")
            emit("dhl")
        }.debounce(200).toList()
        println(result) // 最后输出：dhl
    }



}

package com.gl.kotlin.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gl.kotlin.databinding.ActivityFlowBinding
import com.gl.kotlin.viewmodel.WanAndroidViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

class FlowActivity : AppCompatActivity() {
    val TAG: String = javaClass.simpleName

    private val mBinding: ActivityFlowBinding by lazy {
        ActivityFlowBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        flowDemo()
        etDebounce()
    }

    fun flowDemo() {
        GlobalScope.launch(Dispatchers.Main) {
            count().flowOn(Dispatchers.Unconfined) // 指定数据流产生运行线程
                .map {
//                    Log.d("Coroutine", "map on ${Thread.currentThread().name}")
                    if (it > 10) {
                        throw NumberFormatException()
                    }
                    "I am $it"
                }.flowOn(Dispatchers.IO)           // 指定map中间action运行线程
                .catch { ex ->
//                    Log.d("Coroutine", "catch on ${Thread.currentThread().name}")
                    emit("error")
                }.collect {
//                    Log.d("Coroutine", "collect on ${Thread.currentThread().name}")
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
//            Log.d("Coroutine", "emit on ${Thread.currentThread().name}")
            emit(x)
            x = x.plus(1)
        }
    }

    fun etDebounce() {
        // 定义一个全局的 StateFlow
        val _etState = MutableStateFlow("")
        mBinding.et.addTextChangedListener { text ->
            // 往流里写数据
            _etState.value = (text ?: "").toString()
        }
        lifecycleScope.launch {
            _etState
                .debounce(2000) // 限流，2000毫秒
                .filter {
                    // 空文本过滤掉
                    it.isNotBlank()
                }.collect {
                    // 订阅数据
                    Log.d("etDebounce", "etDebounce----------------->>> $it")

                }
        }
    }

    // debounce（防抖）：在一定时间内（例5s内），不管调动多少次方法，也只执行一次方法。
    fun debouceDemo(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            flow<Int> {
                for (i in 0..100) {
                    // 模拟生成数据
                    emit(i)
//                    delay(100)
                }
            }.debounce(500) // 这里是有效的 限流
                .collect {
                    Log.d("debouceDemo", "debouceDemo----------------->>> $it")
                }
        }
    }

    private val state = MutableStateFlow(1)

    fun simpleStateFlow(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            coroutineScope {
                launch {
                    delay(1000)
                    state.collect {
                        println("before state value $it")
                    }
                }
                launch {
                    for (i in 1..100) {
                        state.emit(i)
                        delay(100)
                    }
                }

                launch {
                    state.collect {
                        println("state value $it")
                    }
                }
            }
        }
    }

    //SharedFlow 和 StateFlow 相比，他有缓冲区区，并可以定义缓冲区的溢出规则，
    // 已经可以定义给一个新的接收器发送多少数据的缓存值。
    fun simpleSharedFlow(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val sharedFlow = MutableSharedFlow<Int>(
                replay = 5,
                extraBufferCapacity = 3,
            )

            coroutineScope {
                launch {
                    sharedFlow.collect {
                        println("collect1 received shared flow $it")
                    }
                }
                launch {
                    (1..10).forEach {
                        sharedFlow.emit(it)
                        delay(100)
                    }
                }
                // wait a minute
                delay(1000)
                launch {
                    sharedFlow.collect {
                        println("collect2 received shared flow $it")
                    }
                }
            }
        }
    }

}

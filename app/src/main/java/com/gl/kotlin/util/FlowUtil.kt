package com.gl.kotlin.util

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

object FlowUtil {

    /**
     *它返回包含三个数字的 List
     */
    fun flowTranversal() {
        runBlocking {
            sequence {
                for (i in 1..3) {
                    Thread.sleep(100) // pretend we are computing it
                    yield(i) // yield next value
                    println("i:$i")
                }
            }
        }
    }

    /**
     *  使用 transform 运算符，我们可以发出任意次数的任意值
     */
    fun flowTransform() {
        runBlocking {
            //sampleStart
            (1..3).asFlow() // a flow of requests
                .transform { request ->
                    emit("Making request $request")
                    emit(FlowUtil.performRequest(request))
                }
                .collect { response -> println(response) }
        }
    }

    suspend fun performRequest(request: Int): String {
        delay(1000) // imitate long-running asynchronous work
        return "response $request"
    }

    fun flowSeuquence() {
        GlobalScope.launch(Dispatchers.Main) {
            count().flowOn(Dispatchers.Unconfined) // 指定数据流产生运行线程
                .map {
                    if (it > 10) {
                        throw NumberFormatException()
                    }
                    "I am $it"
                }.flowOn(Dispatchers.IO)           // 指定map中间action运行线程
                .catch { ex ->
                    emit("error")
                }.collect {
                    println("value: $it")
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
            emit(x)
            x = x.plus(1)
        }
    }

}
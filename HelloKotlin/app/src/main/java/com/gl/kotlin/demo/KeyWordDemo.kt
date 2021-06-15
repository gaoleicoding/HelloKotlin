package com.gl.kotlin.demo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class KeyWordDemo {


    suspend fun filterDemo() {
        val result = flow {
            emit("h")
            emit("i")
            emit("d")
            delay(90)
            emit("dh")
            emit("dhl")
        }.filter { result ->
            if (!result.equals("dhl")) {
                return@filter false
            } else {
                return@filter true
            }
        }.toList()
        println(result) // 最后输出：dhl
    }

    suspend fun flatMapLatestDemo() {
        flow {
            emit("dh")
            emit("dhl")
        }.flatMapLatest { value ->

            flow<String> {
                delay(100)
                println("collected $value") // 最后输出 collected dhl
            }

        }.collect()
    }

    suspend fun distinctUntilChangedDemo() {
        val result = flow {
            emit("d")
            emit("d")
            emit("d")
            emit("d")
            emit("dhl")
            emit("dhl")
            emit("dhl")
            emit("dhl")
        }.distinctUntilChanged().toList()
        println(result) // 输出 [d, dhl]
    }

}
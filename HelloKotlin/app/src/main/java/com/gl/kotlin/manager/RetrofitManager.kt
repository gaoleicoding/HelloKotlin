package com.gl.kotlin.manager

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object RetrofitManager {


    // 与 Retrofit 结合使用
    fun startCoroutineWithRetrofit() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(CoroutineManager.TAG, "launch: ")
            try {
                val repos = CoroutineManager.api.listReposKt("JakeWharton")
                Log.d(CoroutineManager.TAG, "listReposKt: ${repos[0].name}")
            } catch (e: Exception) {
                Log.d(CoroutineManager.TAG, "catch: ${e.message}")

            }

        }
    }

    // 使用 async 并发
    fun startAsyncCoroutine() {
        GlobalScope.launch(Dispatchers.Main) {
            val one = async { CoroutineManager.api.listReposKt("JakeWharton") }
            val two = async { CoroutineManager.api.listReposKt("JakeWharton") }
            Log.d(CoroutineManager.TAG, "launch:${one.await()[0].name}== ${two.await()[0].name}")

        }
    }
}
package com.gl.kotlin.retrofit

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {

    val TAG: String = "RetrofitManager"
    val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api = retrofit.create(GitHubService::class.java)

    // 与 Retrofit 结合使用
    fun startCoroutineWithRetrofit() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "launch: ")
            try {
                val repos = api.listReposKt("JakeWharton")
                Log.d(TAG, "listReposKt: ${repos[0].name}")
            } catch (e: Exception) {
                Log.d(TAG, "catch: ${e.message}")
            }
        }
    }

    // 使用 async 并发
    fun startAsyncCoroutine() {
        GlobalScope.launch(Dispatchers.Main) {
            val one = async { api.listReposKt("JakeWharton") }
            val two = async { api.listReposKt("gaoleicoding") }
            Log.d(TAG, "launch:${one.await()[0].name}== ${two.await()[0].name}")

        }
    }
}
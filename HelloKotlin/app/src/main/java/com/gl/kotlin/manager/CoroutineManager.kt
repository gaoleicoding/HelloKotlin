package com.gl.kotlin.manager

import android.util.Log
import com.gl.kotlin.retrofit.GitHubService
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoroutineManager {

    val TAG: String = "HelloCoroutine"

    val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api = retrofit.create(GitHubService::class.java)

    private suspend fun ioCode1() {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
            Log.d(TAG, "onCreate:ioCode1=${Thread.currentThread().name} ")
        }
    }

    private fun uiCode1() {
        Log.d(TAG, "onCreate:uiCode1=${Thread.currentThread().name} ")
    }


    // 基本使用
    fun startCoroutine() {
        GlobalScope.launch(Dispatchers.Main)
        {
            ioCode1()
            uiCode1()
        }
    }

    //   runBlocking 是会阻塞主线程的，直到 runBlocking 内部全部子任务执行完毕，才会继续执行下一步的操作，
    //   也就是说在runBlocking中不管开启多少个子协程它们都是使用runBlocking所使用的那一条线程来完成任务的
    fun startBlockingCoroutine() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }

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
            val two = async { api.listReposKt("JakeWharton") }
            Log.d(TAG, "launch:${one.await()[0].name}== ${two.await()[0].name}")

        }
    }

    fun switchCoroutineThread() = runBlocking {
        val deferred: Deferred<Int> = async(Dispatchers.Default) {
            loadData()
        }
        Log.d(TAG, "waiting...")
        Log.d(TAG, "deferred: " + deferred.await())
        Log.d(TAG, "finish...")
    }

    suspend fun loadData(): Int {
        Log.d(TAG, "loading...")
        withContext(Dispatchers.Main) {//<---回到主线程
            //update ui here
            Log.d(TAG, "updating main...")
        }
        delay(1000L)
        Log.d(TAG, "loaded!")
        return 42
    }

}
package com.gl.kotlin.util

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

object CoroutineUtil {

    val TAG: String = "CoroutineUtil"

    private suspend fun ioCode1() {
        withContext(Dispatchers.IO) {
            delay(1000)
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

    fun lifecycleCoroutine(activity: AppCompatActivity) {
        Log.d(TAG, "test: 方法开始")
        activity.lifecycleScope.launch {
            delay(1000)
            Log.d(TAG, "test: " + Thread.currentThread().name)
            Log.d(TAG, "test: 协程结束")
            Toast.makeText(activity, "协程结束", Toast.LENGTH_SHORT).show()
        }
        Log.d(TAG, "test: 方法结束")
    }

    //   runBlocking 是会阻塞主线程的，直到 runBlocking 内部全部子任务执行完毕，才会继续执行下一步的操作，
    //   也就是说在runBlocking中不管开启多少个子协程它们都是使用runBlocking所使用的那一条线程来完成任务的
    fun startBlockingCoroutine() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            Log.d(TAG, "Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                Log.d(TAG, "Task from nested launch")
            }

            delay(100L)
            Log.d(TAG, "Task from coroutine scope") // This line will be printed before the nested launch
        }

        Log.d(TAG, "Coroutine scope is over") // This line is not printed until the nested launch completes
    }

    //runBlocking作为普通方法和suspend方法互相调用的桥梁，实现程序在阻塞和非阻塞状态之间切换

    fun switchCoroutineThread() = runBlocking {
        val deferred = async(Dispatchers.IO) {
            //此处是一个耗时任务
            delay(2000L)
            "ok"
        }
        //此处继续执行其他任务
        //..........
        val result = deferred.await()  //此处获取耗时任务的结果，我们挂起当前协程，并等待结果
        Log.d(TAG, "threadName: "+Thread.currentThread().name)
        Log.d(TAG, "result: "+result)

    }

    fun setUpUI(){
        val data = requestData()
        doSomethingElse()
        processData(data)
    }

    fun requestData(): String {
        Thread.sleep(2000)
        return "Ui Data"
    }

    fun doSomethingElse(){
        println("doSomethingElse")
    }

    fun processData(data: String) {
        println("updateUI$data")
    }
}